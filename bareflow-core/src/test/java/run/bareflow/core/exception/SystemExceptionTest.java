package run.bareflow.core.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SystemExceptionTest {
    @Test
    void testMessageConstructor() {
        SystemException ex = new SystemException("error occurred");

        assertEquals("error occurred", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("root cause");

        SystemException ex = new SystemException("wrapper", cause);

        assertEquals("wrapper", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testCauseOnlyConstructor() {
        Throwable cause = new RuntimeException("root cause");

        SystemException ex = new SystemException(cause);

        assertEquals(cause.toString(), ex.getMessage());
        assertSame(cause, ex.getCause());
    }
}