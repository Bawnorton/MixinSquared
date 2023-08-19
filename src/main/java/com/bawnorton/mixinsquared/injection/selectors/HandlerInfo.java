/*
 * This file is part of Mixin, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.bawnorton.mixinsquared.injection.selectors;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.selectors.*;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.TargetNotSupportedException;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.Quantifier;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.ASM;

import java.util.Set;

/**
 * Modified version of {@link org.spongepowered.asm.mixin.injection.struct.MemberInfo} to allow for targetting
 * of other mixin handlers
 */
public final class HandlerInfo implements ITargetSelectorRemappable, ITargetSelectorConstructor {

    /**
     * Separator for elements in the path
     */
    private static final String ARROW = "->";
    /**
     * Member owner in internal form but without L;, can be null
     */
    private final String owner;

    /**
     * Member name, can be null to match any member
     */
    private final String name;

    /**
     * Member descriptor, can be null
     */
    private final String desc;

    /**
     * Required matches
     */
    private final Quantifier matches;

    /**
     * Force this member to report as a field
     */
    private final boolean forceField;

    /**
     * The actual String value passed into the {@link #parse} method 
     */
    private final String input;

    /**
     * The actual String value passed into the {@link #parse} method 
     */
    private final String tail;

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param matches Quantifier specifying the number of matches required
     */
    public HandlerInfo(String name, Quantifier matches) {
        this(name, null, null, matches, null, null);
    }

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param owner Member owner, can be null otherwise must be in internal form
     *      without L;
     * @param matches Quantifier specifying the number of matches required
     */
    public HandlerInfo(String name, String owner, Quantifier matches) {
        this(name, owner, null, matches, null, null);
    }

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param owner Member owner, can be null otherwise must be in internal form
     *      without L;
     * @param desc Member descriptor, can be null
     */
    public HandlerInfo(String name, String owner, String desc) {
        this(name, owner, desc, Quantifier.DEFAULT, null, null);
    }

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param owner Member owner, can be null otherwise must be in internal form
     *      without L;
     * @param desc Member descriptor, can be null
     * @param matches Quantifier specifying the number of matches required
     */
    public HandlerInfo(String name, String owner, String desc, Quantifier matches) {
        this(name, owner, desc, matches, null, null);
    }

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param owner Member owner, can be null otherwise must be in internal form
     *      without L;
     * @param desc Member descriptor, can be null
     * @param matches Quantifier specifying the number of matches required
     */
    public HandlerInfo(String name, String owner, String desc, Quantifier matches, String tail) {
        this(name, owner, desc, matches, tail, null);
    }

    /**
     * ctor
     *
     * @param name Member name, must not be null
     * @param owner Member owner, can be null otherwise must be in internal form
     *      without L;
     * @param desc Member descriptor, can be null
     * @param matches Quantifier specifying the number of matches required
     */
    public HandlerInfo(String name, String owner, String desc, Quantifier matches, String tail, String input) {
        if (owner != null && owner.contains(".")) {
            throw new IllegalArgumentException("Attempt to instance a HandlerInfo with an invalid owner format");
        }

        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.matches = matches;
        this.forceField = false;
        this.tail = tail;
        this.input = input;
    }

    /**
     * Initialise a HandlerInfo using the supplied insn which must be an instance
     * of MethodInsnNode or FieldInsnNode.
     *
     * @param insn instruction node to copy values from
     */
    public HandlerInfo(AbstractInsnNode insn) {
        this.matches = Quantifier.DEFAULT;
        this.forceField = false;
        this.input = null;
        this.tail = null;

        if (insn instanceof MethodInsnNode) {
            MethodInsnNode methodNode = (MethodInsnNode)insn;
            this.owner = methodNode.owner;
            this.name = methodNode.name;
            this.desc = methodNode.desc;
        } else if (insn instanceof FieldInsnNode) {
            FieldInsnNode fieldNode = (FieldInsnNode)insn;
            this.owner = fieldNode.owner;
            this.name = fieldNode.name;
            this.desc = fieldNode.desc;
        } else {
            throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
        }
    }

    /**
     * Initialise a HandlerInfo using the supplied mapping object
     *
     * @param mapping Mapping object to copy values from
     */
    public HandlerInfo(IMapping<?> mapping) {
        this.owner = mapping.getOwner();
        this.name = mapping.getSimpleName();
        this.desc = mapping.getDesc();
        this.matches = Quantifier.SINGLE;
        this.forceField = mapping.getType() == IMapping.Type.FIELD;
        this.tail = null;
        this.input = null;
    }

    /**
     * Initialise a remapped HandlerInfo using the supplied mapping object
     *
     * @param method mapping method object to copy values from
     */
    private HandlerInfo(HandlerInfo remapped, MappingMethod method, boolean setOwner) {
        this.owner = setOwner ? method.getOwner() : remapped.owner;
        this.name = method.getSimpleName();
        this.desc = method.getDesc();
        this.matches = remapped.matches;
        this.forceField = false;
        this.tail = null;
        this.input = null;
    }

    /**
     * Initialise a remapped HandlerInfo with a new name
     *
     * @param original Original HandlerInfo
     * @param owner new owner
     */
    private HandlerInfo(HandlerInfo original, String owner) {
        this.owner = owner;
        this.name = original.name;
        this.desc = original.desc;
        this.matches = original.matches;
        this.forceField = original.forceField;
        this.tail = original.tail;
        this.input = null;
    }

    @Override
    public ITargetSelector next() {
        return Strings.isNullOrEmpty(this.tail) ? null : HandlerInfo.parse(this.tail, null);
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public int getMinMatchCount() {
        return this.matches.getClampedMin();
    }

    @Override
    public int getMaxMatchCount() {
        return this.matches.getClampedMax();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String owner = this.owner != null ? "L" + this.owner + ";" : "";
        String name = this.name != null ? this.name : "";
        String quantifier = this.matches.toString();
        String desc = this.desc != null ? this.desc : "";
        String separator = desc.startsWith("(") ? "" : (this.desc != null ? ":" : "");
        String tail = this.tail != null ? " " + HandlerInfo.ARROW + " " + this.tail : "";
        return owner + name + quantifier + separator + desc + tail;
    }

    /**
     * Return this HandlerInfo as an SRG mapping
     *
     * @return SRG representation of this HandlerInfo
     * @deprecated use m.asMethodMapping().serialise() instead
     */
    @Deprecated
    public String toSrg() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference to SRG mapping");
        }

        if (this.desc.startsWith("(")) {
            return this.owner + "/" + this.name + " " + this.desc;
        }

        return this.owner + "/" + this.name;
    }

    /**
     * Returns this HandlerInfo as a java-style descriptor 
     */
    @Override
    public String toDescriptor() {
        if (this.desc == null) {
            return "";
        }

        return new SignaturePrinter(this).setFullyQualified(true).toDescriptor();
    }

    /**
     * Returns the <em>constructor type</em> represented by this HandlerInfo
     */
    @Override
    public String toCtorType() {
        if (this.input == null) {
            return null;
        }

        String returnType = this.getReturnType();
        if (returnType != null) {
            return returnType;
        }

        if (this.owner != null) {
            return this.owner;
        }

        if (this.name != null && this.desc == null) {
            return this.name;
        }

        return this.desc != null ? this.desc : this.input;
    }

    /**
     * Returns the <em>constructor descriptor</em> represented by this
     * HandlerInfo, returns null if no descriptor is present.
     */
    @Override
    public String toCtorDesc() {
        return Bytecode.changeDescriptorReturnType(this.desc, "V");
    }

    /**
     * Get the return type for this HandlerInfo, if the decriptor is present,
     * returns null if the descriptor is absent or if this HandlerInfo represents
     * a field
     */
    private String getReturnType() {
        if (this.desc == null || this.desc.indexOf(')') == -1 || this.desc.indexOf('(') != 0 ) {
            return null;
        }

        String returnType = this.desc.substring(this.desc.indexOf(')') + 1);
        if (returnType.startsWith("L") && returnType.endsWith(";")) {
            return returnType.substring(1, returnType.length() - 1);
        }
        return returnType;
    }

    /**
     * Returns this HandlerInfo as a {@link MappingField} or
     * {@link MappingMethod}
     */
    @Override
    public IMapping<?> asMapping() {
        return this.isField() ? this.asFieldMapping() : this.asMethodMapping();
    }

    /**
     * Returns this HandlerInfo as a mapping method
     */
    @Override
    public MappingMethod asMethodMapping() {
        if (!this.isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
        }

        if (this.isField()) {
            throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
        }

        return new MappingMethod(this.owner, this.name, this.desc);
    }

    /**
     * Returns this HandlerInfo as a mapping field
     */
    @Override
    public MappingField asFieldMapping() {
        if (!this.isField()) {
            throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
        }

        return new MappingField(this.owner, this.name, this.desc);
    }

    @Override
    public boolean isFullyQualified() {
        return this.owner != null && this.name != null && this.desc != null;
    }

    /**
     * Get whether this HandlerInfo is definitely a field, the output of this
     * method is undefined if {@link #isFullyQualified} returns false.
     *
     * @return true if this is definitely a field
     */
    @Override
    public boolean isField() {
        return this.forceField || (this.desc != null && !this.desc.startsWith("("));
    }

    /**
     * Get whether this member represents a constructor
     *
     * @return true if member name is <tt>&lt;init&gt;</tt>
     */
    @Override
    public boolean isConstructor() {
        return Constants.CTOR.equals(this.name);
    }

    /**
     * Get whether this member represents a class initialiser
     *
     * @return true if member name is <tt>&lt;clinit&gt;</tt>
     */
    @Override
    public boolean isClassInitialiser() {
        return Constants.CLINIT.equals(this.name);
    }

    /**
     * Get whether this member represents a constructor or class initialiser
     *
     * @return true if member name is <tt>&lt;init&gt;</tt> or
     *      <tt>&lt;clinit&gt;</tt>
     */
    @Override
    public boolean isInitialiser() {
        return this.isConstructor() || this.isClassInitialiser();
    }

    /**
     * Perform ultra-simple validation of the descriptor, checks that the parts
     * of the descriptor are basically sane.
     *
     * @return fluent
     *
     * @throws InvalidSelectorException if any validation check fails
     */
    @Override
    public HandlerInfo validate() throws InvalidSelectorException {
        // Parse emits a match count of 0 if the quantifier is incorrectly specified
        if (this.getMaxMatchCount() == 0) {
            throw new InvalidMemberDescriptorException(this.input, "Malformed quantifier in selector: " + this.input);
        }

        // Extremely naive class name validation, just to spot really egregious errors
        if (this.owner != null) {
            if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
                throw new InvalidMemberDescriptorException(this.input, "Invalid owner: " + this.owner);
            }
            // We can't detect this situation 100% reliably, but we can take a
            // decent stab at it in order to detect really obvious cases where
            // the user types a dot instead of a semicolon
            if (this.input != null && this.input.lastIndexOf('.') > 0 && this.owner.startsWith("L")) {
                throw new InvalidMemberDescriptorException(this.input, "Malformed owner: " + this.owner + " If you are seeing this message"
                        + "unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner
                        + "; to suppress this error");
            }
        }

        // Also naive validation, we're looking for stupid errors here
        if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}-]+>?$")) {
            throw new InvalidMemberDescriptorException(this.input, "Invalid name: " + this.name);
        }

        if (this.desc != null) {
            if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
                throw new InvalidMemberDescriptorException(this.input, "Invalid descriptor: " + this.desc);
            }
            if (this.isField()) {
                if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
                    throw new InvalidMemberDescriptorException(this.input, "Invalid field type in descriptor: " + this.desc);
                }
            } else {
                try {
                    // getArgumentTypes can choke on some invalid descriptors
                    Type[] argTypes = Type.getArgumentTypes(this.desc);
                    // getInternalName is a useful litmus test for improperly-formatted types which parse out of
                    // the descriptor correctly but are actually invalid, for example unterminated class names.
                    // However it doesn't support primitive types properly in ASM versions before 6 so don't run
                    // the test unless running ASM 6 or later
                    if (ASM.isAtLeastVersion(6)) {
                        for (Type argType : argTypes) {
                            argType.getInternalName();
                        }
                    }
                } catch (Exception ex) {
                    throw new InvalidMemberDescriptorException(this.input, "Invalid descriptor: " + this.desc);
                }

                String retString = this.desc.substring(this.desc.indexOf(')') + 1);
                try {
                    Type retType = Type.getType(retString);
                    int sort = retType.getSort();
                    if (sort >= Type.ARRAY) {
                        retType.getInternalName(); // sanity check
                    }
                    if (!retString.equals(retType.getDescriptor())) {
                        throw new InvalidMemberDescriptorException(this.input, "Invalid return type \"" + retString + "\" in descriptor: "
                                + this.desc);
                    }
                } catch (Exception ex) {
                    throw new InvalidMemberDescriptorException(this.input, "Invalid return type \"" + retString + "\" in descriptor: "
                            + this.desc);
                }
            }
        }

        return this;
    }

    /* (non-Javadoc)
     * @see org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
     *      #match(org.spongepowered.asm.util.asm.ElementNode)
     */
    @Override
    public <TNode> MatchResult match(ElementNode<TNode> node) {
        return node == null ? MatchResult.NONE : this.matches(node.getOwner(), node.getName(), node.getDesc());
    }

    /* (non-Javadoc)
     * @see org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
     *      #matches(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public MatchResult matches(String owner, String name, String desc) {
        if (this.desc != null && desc != null && !this.desc.equals(desc)) {
            return MatchResult.NONE;
        }
        if (this.owner != null && owner != null && !this.owner.equals(owner)) {
            return MatchResult.NONE;
        }
        if (this.name != null && name != null) {
            if (this.name.equals(name)) {
                return MatchResult.EXACT_MATCH;
            }
            if (this.name.equalsIgnoreCase(name)) {
                return MatchResult.MATCH;
            }
            if (name.endsWith(this.name)) {
                return MatchResult.EXACT_MATCH;
            }
            if (name.toLowerCase().endsWith(this.name.toLowerCase())) {
                return MatchResult.MATCH;
            }
            return MatchResult.NONE;
        }
        return MatchResult.EXACT_MATCH;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ITargetSelectorByName)) {
            return false;
        }

        ITargetSelectorConstructor other = (ITargetSelectorConstructor)obj;
        boolean otherForceField = other instanceof HandlerInfo ? ((HandlerInfo)other).forceField
                : other instanceof ITargetSelectorRemappable && ((ITargetSelectorRemappable) other).isField();

        return this.compareMatches(other) && this.forceField == otherForceField
                && Objects.equal(this.owner, other.getOwner())
                && Objects.equal(this.name, other.getName())
                && Objects.equal(this.desc, other.getDesc());
    }

    /**
     * Compare local match count with match count of other selector
     */
    private boolean compareMatches(ITargetSelectorByName other) {
        if (other instanceof HandlerInfo) {
            return ((HandlerInfo)other).matches.equals(this.matches);
        }
        return this.getMinMatchCount() == other.getMinMatchCount() && this.getMaxMatchCount() == other.getMaxMatchCount();
    }

    /**
     * Differentiate between MatchInfo and HandlerInfo
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.matches, this.owner, this.name, this.desc, HandlerInfo.class);
    }

    /* (non-Javadoc)
     * @see org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
     *      #mutate(java.lang.String[])
     */
    @Override
    public ITargetSelector configure(Configure request, String... args) {
        request.checkArgs(args);
        switch (request) {
            case SELECT_MEMBER:
                if (this.matches.isDefault()) {
                    return new MemberInfo(this.name, this.owner, this.desc, Quantifier.SINGLE, this.tail);
                }
                break;
            case SELECT_INSTRUCTION:
                if (this.matches.isDefault()) {
                    return new MemberInfo(this.name, this.owner, this.desc, Quantifier.ANY, this.tail);
                }
                break;
            case MOVE:
                return this.move(Strings.emptyToNull(args[0]));
            case ORPHAN:
                return this.move(null);
            case TRANSFORM:
                return this.transform(Strings.emptyToNull(args[0]));
            case PERMISSIVE:
                return this.transform(null);
            case CLEAR_LIMITS:
                if (this.matches.getMin() != 0 || this.matches.getMax() < Integer.MAX_VALUE) {
                    return new MemberInfo(this.name, this.owner, this.desc, Quantifier.ANY, this.tail);
                }
                break;
        }
        return this;
    }

    /* (non-Javadoc)
     * @see org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
     *      #attach(org.spongepowered.asm.mixin.refmap.IMixinContext)
     */
    @Override
    public ITargetSelector attach(ISelectorContext context) throws InvalidSelectorException {
        if (this.owner != null && !this.owner.equals(context.getMixin().getTargetClassRef())) {
            throw new TargetNotSupportedException(this.owner);
        }
        return this;
    }

    /**
     * Create a new version of this member with a different owner
     *
     * @param newOwner New owner for this member
     */
    @Override
    public ITargetSelectorRemappable move(String newOwner) {
        if ((newOwner == null && this.owner == null) || (newOwner != null && newOwner.equals(this.owner))) {
            return this;
        }
        return new HandlerInfo(this, newOwner);
    }

    /**
     * Create a new version of this member with a different descriptor
     *
     * @param newDesc New descriptor for this member
     */
    @Override
    public ITargetSelectorRemappable transform(String newDesc) {
        if ((newDesc == null && this.desc == null) || (newDesc != null && newDesc.equals(this.desc))) {
            return this;
        }
        return new HandlerInfo(this.name, this.owner, newDesc, this.matches);
    }

    /**
     * Create a remapped version of this member using the supplied method data
     *
     * @param srgMethod SRG method data to use
     * @param setOwner True to set the owner as well as the name
     * @return New MethodInfo with remapped values
     */
    @Override
    public ITargetSelectorRemappable remapUsing(MappingMethod srgMethod, boolean setOwner) {
        return new HandlerInfo(this, srgMethod, setOwner);
    }

    public static void parse(Iterable<?> selectors, ISelectorContext context, Set<ITargetSelector> parsed) {
        for (Object selector : selectors) {
            if (selector instanceof String) {
                String string = (String)selector;
                if(string.endsWith("/")) {
                    // TODO: HandlerMemberMatcher
                    MemberMatcher regexMatcher = MemberMatcher.parse(string, context);
                    parsed.add(regexMatcher);
                    continue;
                }
                if(!string.startsWith("@")) {
                    parsed.add(HandlerInfo.parse((String)selector, context));
                }
            }
        }
    }

    /**
     * Parse a HandlerInfo from a string
     *
     * @param input String to parse HandlerInfo from
     * @param context Selector context for this parse request
     * @return parsed HandlerInfo
     */
    public static HandlerInfo parse(final String input, final ISelectorContext context) {
        String desc = null;
        String owner = null;
        String name = Strings.nullToEmpty(input).replaceAll("\\s", "");
        String tail = null;

        int arrowPos = name.indexOf(HandlerInfo.ARROW);
        if (arrowPos > -1) {
            tail = name.substring(arrowPos + 2);
            name = name.substring(0, arrowPos);
        }

        if (context != null) {
            name = context.remap(name);
        }

        int lastDotPos = name.lastIndexOf('.');
        int semiColonPos = name.indexOf(';');
        if (lastDotPos > -1) {
            owner = name.substring(0, lastDotPos).replace('.', '/');
            name = name.substring(lastDotPos + 1);
        } else if (semiColonPos > -1 && name.startsWith("L")) {
            owner = name.substring(1, semiColonPos).replace('.', '/');
            name = name.substring(semiColonPos + 1);
        }

        int parenPos = name.indexOf('(');
        int colonPos = name.indexOf(':');
        if (parenPos > -1) {
            desc = name.substring(parenPos);
            name = name.substring(0, parenPos);
        } else if (colonPos > -1) {
            desc = name.substring(colonPos + 1);
            name = name.substring(0, colonPos);
        }

        if ((name.indexOf('/') > -1 || name.indexOf('.') > -1) && owner == null) {
            owner = name;
            name = "";
        }

        // Use default quantifier with negative max value. Used to indicate that
        // an explicit quantifier was not parsed from the selector string, this
        // allows us to provide backward-compatible behaviour for injection
        // points vs. selecting target members which have different default
        // semantics when omitting the quantifier. This is handled by consumers
        // calling configure() with SELECT_MEMBER or SELECT_INSTRUCTION to
        // promote the default case to a concrete case.
        Quantifier quantifier = Quantifier.DEFAULT;
        if (name.endsWith("*")) {
            quantifier = Quantifier.ANY;
            name = name.substring(0, name.length() - 1);
        } else if (name.endsWith("+")) {
            quantifier = Quantifier.PLUS;
            name = name.substring(0, name.length() - 1);
        } else if (name.endsWith("}")) {
            quantifier = Quantifier.NONE; // Assume invalid until quantifier is parsed
            int bracePos = name.indexOf("{");
            if (bracePos >= 0) {
                try {
                    quantifier = Quantifier.parse(name.substring(bracePos));
                    name = name.substring(0, bracePos);
                } catch (Exception ex) {
                    // Handled later in validate since matchCount will be 0
                }
            }
        } else if (name.contains("{")) {
            quantifier = Quantifier.NONE; // Probably incomplete quantifier
        }

        if (name.isEmpty()) {
            name = null;
        }

        return new HandlerInfo(name, owner, desc, quantifier, tail, input);
    }

    /**
     * Return the supplied mapping parsed as a HandlerInfo
     *
     * @param mapping mapping to parse
     * @return new HandlerInfo
     */
    public static HandlerInfo fromMapping(IMapping<?> mapping) {
        return new HandlerInfo(mapping);
    }

}
