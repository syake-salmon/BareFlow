package io.github.ss.bareflow.core.definition;

import java.util.Collections;
import java.util.Map;

/**
 * Step-level definition.
 * Immutable model representing a single step in a flow.
 */
public class StepDefinition {
    private final String id;
    private final String module;
    private final String operation;

    private final Map<String, Object> input;
    private final Map<String, String> output;

    private final RetryPolicy retryPolicy;
    private final OnErrorDefinition onError;

    public StepDefinition(String id,
            String module,
            String operation,
            Map<String, Object> input,
            Map<String, String> output,
            RetryPolicy retryPolicy,
            OnErrorDefinition onError) {
        this.id = id;
        this.module = module;
        this.operation = operation;
        this.input = input == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(input);
        this.output = output == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(output);
        this.retryPolicy = retryPolicy;
        this.onError = onError;
    }

    public String getId() {
        return id;
    }

    public String getModule() {
        return module;
    }

    public String getOperation() {
        return operation;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public Map<String, String> getOutput() {
        return output;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public OnErrorDefinition getOnError() {
        return onError;
    }
}