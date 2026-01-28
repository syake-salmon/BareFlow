package io.github.ss.bareflow.core.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Execution context shared across steps.
 * Holds key-value pairs and allows merging outputs.
 */
public class ExecutionContext {
    private final Map<String, Object> values = new HashMap<>();

    public boolean contains(String key) {
        return values.containsKey(key);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public void put(String key, Object value) {
        values.put(key, value);
    }

    /**
     * Merge output map into context.
     */
    public void merge(Map<String, Object> output) {
        if (output == null)
            return;
        values.putAll(output);
    }

    /**
     * Create a defensive snapshot of the current context.
     * Used for StepTrace recording.
     */
    public Map<String, Object> snapshot() {
        return Collections.unmodifiableMap(new HashMap<>(values));
    }

    /**
     * Expose internal map (read-only).
     */
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(values);
    }
}