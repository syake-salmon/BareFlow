package io.github.ss.bareflow.core.definition;

import java.util.Collections;
import java.util.List;

/**
 * Flow-level definition.
 * Immutable model representing a whole flow.
 */
public class FlowDefinition {
    private final String id;
    private final String description;
    private final OnErrorDefinition onError;
    private final List<StepDefinition> steps;

    public FlowDefinition(String id,
            String description,
            OnErrorDefinition onError,
            List<StepDefinition> steps) {
        this.id = id;
        this.description = description;
        this.onError = onError;
        this.steps = steps == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(steps);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public OnErrorDefinition getOnError() {
        return onError;
    }

    public List<StepDefinition> getSteps() {
        return steps;
    }
}