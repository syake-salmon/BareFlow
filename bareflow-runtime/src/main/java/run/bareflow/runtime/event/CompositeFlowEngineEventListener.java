package run.bareflow.runtime.event;

import java.util.List;

import run.bareflow.core.engine.event.FlowEngineEvent;
import run.bareflow.core.engine.event.FlowEngineEventListener;

/**
 * A simple composite that forwards {@link FlowEngineEvent} instances to
 * multiple
 * {@link FlowEngineEventListener} implementations.
 *
 * <p>
 * This listener performs no filtering, transformation, or error handling.
 * Each registered listener receives the event in the order provided.
 * </p>
 *
 * <p>
 * Typical use cases include combining logging, metrics, tracing, or other
 * observation adapters into a single listener instance that can be registered
 * with the FlowEngine.
 * </p>
 */
public final class CompositeFlowEngineEventListener implements FlowEngineEventListener {
    private final List<FlowEngineEventListener> listeners;

    /**
     * Creates a composite listener that delegates events to the given listeners.
     *
     * @param listeners the listeners to notify, in order
     */
    public CompositeFlowEngineEventListener(List<FlowEngineEventListener> listeners) {
        this.listeners = List.copyOf(listeners);
    }

    @Override
    public void onEvent(FlowEngineEvent event) {
        for (FlowEngineEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}