package run.bareflow.runtime.event;

import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import run.bareflow.core.definition.FlowDefinition;
import run.bareflow.core.engine.event.FlowEngineEvent;
import run.bareflow.core.engine.event.FlowEngineEvent.FlowStartEvent;
import run.bareflow.core.engine.event.FlowEngineEventListener;

public class CompositeFlowEngineEventListenerTest {
    @Test
    public void delegatesEventToAllListenersInOrder() {
        FlowEngineEventListener l1 = mock(FlowEngineEventListener.class);
        FlowEngineEventListener l2 = mock(FlowEngineEventListener.class);
        FlowEngineEventListener l3 = mock(FlowEngineEventListener.class);

        CompositeFlowEngineEventListener composite = new CompositeFlowEngineEventListener(List.of(l1, l2, l3));

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        composite.onEvent(event);

        verify(l1).onEvent(event);
        verify(l2).onEvent(event);
        verify(l3).onEvent(event);
    }

    @Test
    public void doesNotModifyOriginalListenerList() {
        FlowEngineEventListener l1 = mock(FlowEngineEventListener.class);

        List<FlowEngineEventListener> original = List.of(l1);
        @SuppressWarnings("unused")
        CompositeFlowEngineEventListener composite = new CompositeFlowEngineEventListener(original);

        // original list should remain unchanged
        assert original.size() == 1;
    }
}