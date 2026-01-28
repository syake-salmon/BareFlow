package io.github.ss.bareflow.core.engine;

import java.util.Map;

import io.github.ss.bareflow.core.context.ExecutionContext;

public interface StepEvaluator {

    /**
     * Evaluate the input map of a step, resolving ${ctx.key} references.
     *
     * @param rawInput raw input map from StepDefinition
     * @param ctx      execution context
     * @return evaluated map ready for StepInvoker
     */
    Map<String, Object> evaluate(Map<String, Object> rawInput, ExecutionContext ctx);
}
