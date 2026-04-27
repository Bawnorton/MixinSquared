package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.reflection.FieldReference;
import com.bawnorton.mixinsquared.reflection.MethodReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReflectionReferenceTest {
    @Test
    void readsAndWritesPrivateField() {
        Sample sample = new Sample();
        FieldReference<String> reference = new FieldReference<>(Sample.class, "value");

        assertEquals("start", reference.get(sample));
        reference.set(sample, "updated");
        assertEquals("updated", reference.get(sample));
    }

    @Test
    void invokesPrivateMethod() {
        Sample sample = new Sample();
        MethodReference<Integer> add = new MethodReference<>(Sample.class, "add", int.class, int.class);
        MethodReference<String> join = new MethodReference<>(Sample.class, "join", String.class);

        assertEquals(5, add.invoke(sample, 2, 3));
        assertEquals("start-tail", join.invoke(sample, "-tail"));
    }

    @Test
    void wrapsMissingMembersInRuntimeException() {
        assertThrows(RuntimeException.class, () -> new FieldReference<>(Sample.class, "missingField"));
        assertThrows(RuntimeException.class, () -> new MethodReference<>(Sample.class, "missingMethod"));
    }

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
    private static final class Sample {
        private String value = "start";

        private int add(int left, int right) {
            return left + right;
        }

        private String join(String suffix) {
            return value + suffix;
        }
    }
}

