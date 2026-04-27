package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.util.AnnotationEqualityVisitor;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnnotationEqualityVisitorTest {
    @Test
    void matchesEquivalentAnnotationData() {
        AnnotationNode expected = annotation(
                "Ltest/Root;",
                "name", "alpha",
                "count", 3,
                "mode", new String[]{"Ltest/Mode;", "FAST"},
                "targets", Arrays.asList("a", "b"),
                "nested", annotation("Ltest/Nested;", "flag", true)
        );

        AnnotationNode actual = annotation(
                "Ltest/Root;",
                "name", "alpha",
                "count", 3,
                "mode", new String[]{"Ltest/Mode;", "FAST"},
                "targets", Arrays.asList("a", "b"),
                "nested", annotation("Ltest/Nested;", "flag", true)
        );

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertTrue(visitor.isEqual());
    }

    @Test
    void detectsDifferentScalarValue() {
        AnnotationNode expected = annotation("Ltest/Root;", "name", "alpha");
        AnnotationNode actual = annotation("Ltest/Root;", "name", "beta");

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertFalse(visitor.isEqual());
    }

    @Test
    void detectsDifferentArrayLength() {
        AnnotationNode expected = annotation("Ltest/Root;", "targets", Arrays.asList("a", "b"));
        AnnotationNode actual = annotation("Ltest/Root;", "targets", Collections.singletonList("a"));

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertFalse(visitor.isEqual());
    }

    @Test
    void nullAnnotationIsNotEqual() {
        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(annotation("Ltest/Root;", "name", "alpha"));
        visitor.visit(null);

        assertFalse(visitor.isEqual());
    }

    @Test
    void detectsTopLevelDescriptorChange() {
        AnnotationNode expected = annotation("Ltest/Root;", "name", "alpha");
        AnnotationNode actual = annotation("Ltest/RootChanged;", "name", "alpha");

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertFalse(visitor.isEqual());
    }

    @Test
    void detectsNestedAnnotationValueChange() {
        AnnotationNode expected = annotation(
                "Ltest/Root;",
                "nested", annotation("Ltest/Nested;", "flag", true)
        );
        AnnotationNode actual = annotation(
                "Ltest/Root;",
                "nested", annotation("Ltest/Nested;", "flag", false)
        );

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertFalse(visitor.isEqual());
    }

    @Test
    void detectsNestedAnnotationDescriptorChange() {
        AnnotationNode expected = annotation(
                "Ltest/Root;",
                "nested", annotation("Ltest/Nested;", "flag", true)
        );
        AnnotationNode actual = annotation(
                "Ltest/Root;",
                "nested", annotation("Ltest/OtherNested;", "flag", true)
        );

        AnnotationEqualityVisitor visitor = new AnnotationEqualityVisitor(expected);
        visitor.visit(actual);

        assertFalse(visitor.isEqual());
    }

    private static AnnotationNode annotation(String desc, Object... nameValuePairs) {
        AnnotationNode node = new AnnotationNode(desc);
        node.values = new ArrayList<>(Arrays.asList(nameValuePairs));
        return node;
    }
}

