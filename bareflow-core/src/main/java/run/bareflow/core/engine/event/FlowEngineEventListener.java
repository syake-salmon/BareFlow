package run.bareflow.core.engine.event;

public interface FlowEngineEventListener {
    /**
     * Called whenever the FlowEngine emits an event.
     *
     * @param event the event emitted by the engine
     */
    void onEvent(FlowEngineEvent event);
}
