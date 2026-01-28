package io.github.ss.bareflow.core.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Execution-time context shared across all steps in a flow.
 * This class intentionally contains no business logic.
 */
public class ExecutionContext {
    /** Internal mutable map */
    private final Map<String, Object> data = new HashMap<>();

    public ExecutionContext() {
        // NOP
    }

    public ExecutionContext(Map<String, Object> initialValues) {
        if (initialValues != null) {
            data.putAll(initialValues);
        }
    }

    // ===== Basic Operations =====
    public Object get(String key) {
        return data.get(key);
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(data);
    }

    // ===== Utility =====
    /**
     * Returns an immutable snapshot of the current context.
     */
    public ExecutionContext immutableCopy() {
        return new ExecutionContext(Collections.unmodifiableMap(new HashMap<>(data)));
    }

    /**
     * Merge another map into this context.
     * Used by FlowEngine after each step execution.
     */
    public void merge(Map<String, Object> values) {
        if (values != null) {
            data.putAll(values);
        }
    }

    @Override
    public String toString() {
        return "ExecutionContext{" +
                "data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ExecutionContext))
            return false;
        ExecutionContext that = (ExecutionContext) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
