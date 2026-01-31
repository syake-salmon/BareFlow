package run.bareflow.core.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StepExecutionExceptionTest {
    @Test
    void testMessageConstructor() {
        StepExecutionException ex = new StepExecutionException("step failed");

        assertEquals("step failed", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("root cause");

        StepExecutionException ex = new StepExecutionException("wrapper", cause);

        assertEquals("wrapper", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testCauseOnlyConstructor() {
        Throwable cause = new RuntimeException("root cause");

        StepExecutionException ex = new StepExecutionException(cause);

        assertEquals(cause.toString(), ex.getMessage());
        assertSame(cause, ex.getCause());
    }
}