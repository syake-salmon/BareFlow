package run.bareflow.core.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BusinessExceptionTest {
    @Test
    void testMessageConstructor() {
        BusinessException ex = new BusinessException("business error");

        assertEquals("business error", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("root cause");

        BusinessException ex = new BusinessException("wrapper", cause);

        assertEquals("wrapper", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testCauseOnlyConstructor() {
        Throwable cause = new RuntimeException("root cause");

        BusinessException ex = new BusinessException(cause);

        assertEquals(cause.toString(), ex.getMessage());
        assertSame(cause, ex.getCause());
    }
}