package com.bawnorton.mixinsquared.adjuster.tools.type;

import java.util.function.UnaryOperator;

public interface MatchCountAnnotationNode extends MutableAnnotationNode{
    default int getRequire() {
        return this.<Integer>get("require").orElse(-1);
    }

    default void setRequire(int require) {
        this.set("require", require);
    }

    default MatchCountAnnotationNode withRequire(UnaryOperator<Integer> require) {
        setRequire(require.apply(getRequire()));
        return this;
    }

    default int getExpect() {
        return this.<Integer>get("expect").orElse(1);
    }

    default void setExpect(int expect) {
        this.set("expect", expect);
    }

    default MatchCountAnnotationNode withExpect(UnaryOperator<Integer> expect) {
        setExpect(expect.apply(getExpect()));
        return this;
    }

    default int getAllow() {
        return this.<Integer>get("allow").orElse(-1);
    }

    default void setAllow(int allow) {
        this.set("allow", allow);
    }

    default MatchCountAnnotationNode withAllow(UnaryOperator<Integer> allow) {
        setAllow(allow.apply(getAllow()));
        return this;
    }
}
