package io.github.ss.bareflow.runtime.evaluator;

import java.util.HashMap;

import java.util.Map;

import io.github.ss.bareflow.core.context.ExecutionContext;
import io.github.ss.bareflow.core.engine.StepEvaluator;

/**
 * Default implementation of StepEvaluator.
 *
 * This evaluator performs minimal value resolution:
 * - Returns the input map as-is
 * - Does not perform template expansion (e.g., ${key})
 * - Does not resolve values from ExecutionContext
 *
 * Applications can replace this with a custom evaluator
 * to support template expressions, context lookups, or
 * any advanced resolution logic.
 */
public class DefaultStepEvaluator implements StepEvaluator {
    @Override
    public Map<String, Object> evaluateInput(Map<String, Object> input, ExecutionContext ctx) {
        if (input == null) {
            return Map.of();
        }

        // Return a shallow copy to avoid accidental mutation
        return new HashMap<>(input);
    }

    @Override
    public Map<String, Object> evaluateOutput(Map<String, Object> output, ExecutionContext ctx) {
        if (output == null) {
            return Map.of();
        }

        // Return a shallow copy to avoid accidental mutation
        return new HashMap<>(output);
    }
}