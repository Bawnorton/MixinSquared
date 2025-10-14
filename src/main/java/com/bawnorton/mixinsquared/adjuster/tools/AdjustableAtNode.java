/*
 * MIT License
 *
 * Copyright (c) 2023-present Bawnorton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bawnorton.mixinsquared.adjuster.tools;

import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableAtNode extends RemapperHolderAnnotationNode {
	public AdjustableAtNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableAtNode defaultNode(InjectionPoint point) {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.AT.desc());
		AdjustableAtNode defaultNode = new AdjustableAtNode(node);
		defaultNode.setValue(point.name());
		return defaultNode;
	}

	public String getId() {
		return this.<String>get("id").orElse("");
	}

	public void setId(String id) {
		this.set("id", id);
	}

	public AdjustableAtNode withId(UnaryOperator<String> id) {
		this.setId(id.apply(this.getId()));
		return this;
	}

	public String getValue() {
		return this.<String>get("value").orElse(null);
	}

	public void setValue(String value) {
		if (value == null) throw new IllegalArgumentException("Value cannot be null");
		this.set("value", value);
	}

	public AdjustableAtNode withValue(UnaryOperator<String> value) {
		this.setValue(value.apply(this.getValue()));
		return this;
	}

	public String getSlice() {
		return this.<String>get("slice").orElse("");
	}

	public void setSlice(String slice) {
		this.set("slice", slice);
	}

	public AdjustableAtNode withSlice(UnaryOperator<String> slice) {
		this.setSlice(slice.apply(this.getSlice()));
		return this;
	}

	public At.Shift getShift() {
		return this.getEnum("shift", At.Shift.class).orElse(At.Shift.NONE);
	}

	public void setShift(At.Shift shift) {
		this.set("shift", new String[]{Type.getDescriptor(At.Shift.class), shift.name()});
	}

	public AdjustableAtNode withShift(UnaryOperator<At.Shift> shift) {
		this.setShift(shift.apply(this.getShift()));
		return this;
	}

	public int getBy() {
		return this.<Integer>get("by").orElse(0);
	}

	public void setBy(int by) {
		this.set("by", by);
	}

	public AdjustableAtNode withBy(UnaryOperator<Integer> by) {
		this.setBy(by.apply(this.getBy()));
		return this;
	}

	public List<String> getArgs() {
		return this.<List<String>>get("args").orElse(new ArrayList<>());
	}

	public void setArgs(List<String> args) {
		this.set("args", args);
	}

	public AdjustableAtNode withArgs(UnaryOperator<List<String>> args) {
		this.setArgs(args.apply(this.getArgs()));
		return this;
	}

	public String getTarget() {
		return this.<String>get("target").orElse("");
	}

	public void setTarget(String target) {
		this.set("target", target);
	}

	public AdjustableAtNode withTarget(UnaryOperator<String> target) {
		this.setTarget(target.apply(this.getTarget()));
		return this;
	}

	public AdjustableDescNode getDesc() {
		return this.<AnnotationNode>get("desc")
				.map(AdjustableDescNode::new)
				.orElse(AdjustableDescNode.defaultNode(""));
	}

	public void setDesc(AdjustableDescNode desc) {
		this.set("desc", desc);
	}

	public AdjustableAtNode withDesc(UnaryOperator<AdjustableDescNode> desc) {
		this.setDesc(desc.apply(this.getDesc()));
		return this;
	}

	public int getOrdinal() {
		return this.<Integer>get("ordinal").orElse(-1);
	}

	public void setOrdinal(int ordinal) {
		this.set("ordinal", ordinal);
	}

	public AdjustableAtNode withOrdinal(UnaryOperator<Integer> ordinal) {
		this.setOrdinal(ordinal.apply(this.getOrdinal()));
		return this;
	}

	public int getOpcode() {
		return this.<Integer>get("opcode").orElse(-1);
	}

	public void setOpcode(int opcode) {
		this.set("opcode", opcode);
	}

	public AdjustableAtNode withOpcode(UnaryOperator<Integer> opcode) {
		this.setOpcode(opcode.apply(this.getOpcode()));
		return this;
	}

	public boolean getUnsafe() {
		return this.<Boolean>get("unsafe").orElse(false);
	}

	public void setUnsafe(boolean unsafe) {
		this.set("unsafe", unsafe);
	}

	public AdjustableAtNode withUnsafe(UnaryOperator<Boolean> unsafe) {
		this.setUnsafe(unsafe.apply(this.getUnsafe()));
		return this;
	}

	@Override
	@ApiStatus.Internal
	public void applyRefmap(UnaryOperator<String> refmapApplicator) {
		this.withTarget(refmapApplicator);
		this.withArgs(args -> {
			List<String> remappedArgs = new ArrayList<>();
			for (String arg : args) {
				remappedArgs.add(refmapApplicator.apply(arg));
			}
			return remappedArgs;
		});
	}

	public enum InjectionPoint {
		HEAD,
		TAIL,
		INVOKE,
		INVOKE_ASSIGN,
		INVOKE_STRING,
		FIELD,
		NEW,
		CONSTANT,
		JUMP;

		public AdjustableAtNode toNode() {
			return AdjustableAtNode.defaultNode(this);
		}
	}
}
