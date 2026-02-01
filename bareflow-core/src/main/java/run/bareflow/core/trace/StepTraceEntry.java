package run.bareflow.core.trace;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a single execution attempt of a step.
 *
 * <p>
 * This entry captures:
 * </p>
 * <ul>
 * <li>the context snapshot before execution</li>
 * <li>the evaluated input passed to the step</li>
 * <li>the raw output returned by the StepInvoker (if any)</li>
 * <li>the error thrown during execution (if any)</li>
 * <li>the start and end timestamps of the attempt</li>
 * <li>the attempt number (1 = first attempt, 2+ = retries)</li>
 * </ul>
 *
 * <p>
 * All fields are immutable. StepTraceEntry is a pure data carrier and
 * contains no business logic beyond simple success/error checks.
 * </p>
 */
public class StepTraceEntry {
    /** The logical step name defined in the FlowDefinition. */
    private final String stepName;

    /** Snapshot of the ExecutionContext before this attempt started. */
    private final Map<String, Object> beforeContext;

    /** Input after placeholder evaluation. */
    private final Map<String, Object> evaluatedInput;

    /** Raw output returned by the StepInvoker. Null if execution failed. */
    private final Map<String, Object> rawOutput;

    /** Error thrown during execution, or null if the attempt succeeded. */
    private final Throwable error;

    /** Timestamp when the attempt started. */
    private final Instant startTime;

    /** Timestamp when the attempt finished. */
    private final Instant endTime;

    /**
     * Attempt number:
     * <ul>
     * <li>1 = initial execution</li>
     * <li>2+ = retries (RetryPolicy or onError.RETRY)</li>
     * </ul>
     */
    private final int attempt;

    public StepTraceEntry(
            final String stepName,
            final Map<String, Object> beforeContext,
            final Map<String, Object> evaluatedInput,
            final Map<String, Object> rawOutput,
            final Throwable error,
            final Instant startTime,
            final Instant endTime,
            final int attempt) {

        this.stepName = stepName;
        this.beforeContext = beforeContext;
        this.evaluatedInput = evaluatedInput;
        this.rawOutput = rawOutput;
        this.error = error;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attempt = attempt;
    }

    public String getStepName() {
        return stepName;
    }

    public Map<String, Object> getBeforeContext() {
        return beforeContext;
    }

    public Map<String, Object> getEvaluatedInput() {
        return evaluatedInput;
    }

    public Map<String, Object> getRawOutput() {
        return rawOutput;
    }

    public Throwable getError() {
        return error;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Returns the attempt number (1 = first attempt).
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * Returns true if this attempt succeeded (i.e., no error was thrown).
     */
    public boolean isSuccess() {
        return error == null;
    }
}