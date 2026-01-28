package io.github.ss.bareflow.core.trace;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a single step execution record.
 * Pure data model with no logic.
 */
public class StepTraceEntry {
    private final String stepId;

    private final Map<String, Object> contextBefore;
    private final Map<String, Object> evaluatedInput;
    private final Map<String, Object> output;

    private final Throwable error;

    private final Instant startTime;
    private final Instant endTime;

    public StepTraceEntry(String stepId,
            Map<String, Object> contextBefore,
            Map<String, Object> evaluatedInput,
            Map<String, Object> output,
            Throwable error,
            Instant startTime,
            Instant endTime) {
        this.stepId = stepId;
        this.contextBefore = contextBefore;
        this.evaluatedInput = evaluatedInput;
        this.output = output;
        this.error = error;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStepId() {
        return stepId;
    }

    public Map<String, Object> getContextBefore() {
        return contextBefore;
    }

    public Map<String, Object> getEvaluatedInput() {
        return evaluatedInput;
    }

    public Map<String, Object> getOutput() {
        return output;
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
}
