package run.bareflow.core.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the full execution trace of a flow.
 * Stores an ordered list of StepTraceEntry objects.
 *
 * StepTrace is mutable only through the record() method.
 * Consumers receive an immutable view of the entries.
 */
public class StepTrace {
    private final List<StepTraceEntry> entries = new ArrayList<>();

    /**
     * Record a new step execution entry.
     */
    public void record(StepTraceEntry entry) {
        entries.add(entry);
    }

    /**
     * Returns an immutable list of all recorded entries.
     */
    public List<StepTraceEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Returns true only if every recorded attempt succeeded.
     * This is stricter than "flow succeeded", because retries record failures.
     */
    public boolean isAllSuccessful() {
        return entries.stream().allMatch(StepTraceEntry::isSuccess);
    }

    /**
     * Returns true if the last recorded attempt succeeded.
     * This represents the final outcome of the step, regardless of retries.
     */
    public boolean isFinallySuccessful() {
        if (entries.isEmpty())
            return false;
        return entries.get(entries.size() - 1).getError() == null;
    }

    /**
     * Returns true if the step was executed more than once,
     * regardless of whether the retry was caused by RetryPolicy or onError.RETRY.
     */
    public boolean wasRetried() {
        return entries.stream().anyMatch(e -> e.getAttempt() > 1);
    }

    /**
     * Returns the total number of execution attempts for this step,
     * including the initial attempt and any retries.
     */
    public int getTotalAttempts() {
        return entries.stream()
                .mapToInt(StepTraceEntry::getAttempt)
                .max()
                .orElse(0);
    }
}