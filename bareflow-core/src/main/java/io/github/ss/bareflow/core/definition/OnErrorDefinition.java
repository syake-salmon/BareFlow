package io.github.ss.bareflow.core.definition;

/**
 * Error handling behavior for a flow or step.
 */
public class OnErrorDefinition {
    public enum Action {
        STOP,
        CONTINUE,
        RETRY
    }

    private final Action action;
    private final long delayMillis;

    public OnErrorDefinition(Action action, long delayMillis) {
        if (action == null) {
            throw new IllegalArgumentException("action must not be null");
        }
        this.action = action;
        this.delayMillis = Math.max(0, delayMillis);
    }

    public Action getAction() {
        return action;
    }

    public long getDelayMillis() {
        return delayMillis;
    }
}