package run.bareflow.core.trace;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StepTraceEntryTest {
    @Test
    void testConstructorAndGetters() {
        Instant start = Instant.now();
        Instant end = start.plusMillis(50);

        Map<String, Object> before = Map.of("a", 1);
        Map<String, Object> input = Map.of("x", 10);
        Map<String, Object> output = Map.of("y", 20);

        Throwable error = new RuntimeException("failure");

        StepTraceEntry entry = new StepTraceEntry(
                "step1",
                before,
                input,
                output,
                error,
                start,
                end);

        assertEquals("step1", entry.getStepName());
        assertSame(before, entry.getBeforeContext());
        assertSame(input, entry.getEvaluatedInput());
        assertSame(output, entry.getRawOutput());
        assertSame(error, entry.getError());
        assertSame(start, entry.getStartTime());
        assertSame(end, entry.getEndTime());
    }

    @Test
    void testIsSuccessTrueWhenNoError() {
        StepTraceEntry entry = new StepTraceEntry(
                "step",
                Map.of(),
                Map.of(),
                Map.of(),
                null,
                Instant.now(),
                Instant.now());

        assertTrue(entry.isSuccess());
    }

    @Test
    void testIsSuccessFalseWhenErrorPresent() {
        StepTraceEntry entry = new StepTraceEntry(
                "step",
                Map.of(),
                Map.of(),
                Map.of(),
                new RuntimeException("err"),
                Instant.now(),
                Instant.now());

        assertFalse(entry.isSuccess());
    }
}