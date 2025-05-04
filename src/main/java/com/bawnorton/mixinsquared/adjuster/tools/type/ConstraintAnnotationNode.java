package com.bawnorton.mixinsquared.adjuster.tools.type;

import java.util.function.UnaryOperator;

public interface ConstraintAnnotationNode extends MutableAnnotationNode {
    default String getConstraints() {
        return this.<String>get("constraints").orElse("");
    }

    default void setConstraints(String constraints) {
        this.set("constraints", constraints);
    }

    default ConstraintAnnotationNode withConstraints(UnaryOperator<String> constraints) {
        this.setConstraints(constraints.apply(this.getConstraints()));
        return this;
    }
}
