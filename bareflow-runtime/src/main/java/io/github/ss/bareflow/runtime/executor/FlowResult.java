package io.github.ss.bareflow.runtime.executor;

import io.github.ss.bareflow.core.context.ExecutionContext;
import io.github.ss.bareflow.core.trace.StepTrace;

/**
 * Result of a flow execution.
 * Contains the final ExecutionContext and the full StepTrace.
 */
public class FlowResult {
    private final ExecutionContext context;
    private final StepTrace trace;

    public FlowResult(ExecutionContext context, StepTrace trace) {
        this.context = context;
        this.trace = trace;
    }

    public ExecutionContext getContext() {
        return context;
    }

    public StepTrace getTrace() {
        return trace;
    }
}
