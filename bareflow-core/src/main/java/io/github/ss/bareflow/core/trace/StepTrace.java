package io.github.ss.bareflow.core.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds execution trace for all steps in a flow.
 * Pure container with no business logic.
 */
public class StepTrace {
    private final List<StepTraceEntry> entries = new ArrayList<>();

    public void record(StepTraceEntry entry) {
        entries.add(entry);
    }

    public List<StepTraceEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}