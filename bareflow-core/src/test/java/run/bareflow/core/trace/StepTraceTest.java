package run.bareflow.core.trace;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StepTraceTest {
    private StepTraceEntry entry(
            String name,
            boolean success) {
        return new StepTraceEntry(
                name,
                Map.of("before", 1),
                Map.of("input", 2),
                Map.of("output", 3),
                success ? null : new RuntimeException("err"),
                Instant.now(),
                Instant.now());
    }

    @Test
    void testRecordAndGetEntries() {
        StepTrace trace = new StepTrace();

        StepTraceEntry e1 = entry("step1", true);
        StepTraceEntry e2 = entry("step2", false);

        trace.record(e1);
        trace.record(e2);

        List<StepTraceEntry> entries = trace.getEntries();

        assertEquals(2, entries.size());
        assertSame(e1, entries.get(0));
        assertSame(e2, entries.get(1));
    }

    @Test
    void testGetEntriesIsImmutable() {
        StepTrace trace = new StepTrace();
        trace.record(entry("step1", true));

        List<StepTraceEntry> entries = trace.getEntries();

        assertThrows(UnsupportedOperationException.class, () -> {
            entries.add(entry("x", true));
        });
    }

    @Test
    void testIsSuccessTrueWhenAllEntriesSuccessful() {
        StepTrace trace = new StepTrace();

        trace.record(entry("s1", true));
        trace.record(entry("s2", true));

        assertTrue(trace.isSuccess());
    }

    @Test
    void testIsSuccessFalseWhenAnyEntryFails() {
        StepTrace trace = new StepTrace();

        trace.record(entry("s1", true));
        trace.record(entry("s2", false));

        assertFalse(trace.isSuccess());
    }

    @Test
    void testIsSuccessOnEmptyTrace() {
        StepTrace trace = new StepTrace();

        assertTrue(trace.isSuccess());
    }
}