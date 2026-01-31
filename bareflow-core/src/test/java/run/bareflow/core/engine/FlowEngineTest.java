package run.bareflow.core.engine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import run.bareflow.core.context.ExecutionContext;
import run.bareflow.core.definition.FlowDefinition;
import run.bareflow.core.definition.OnErrorDefinition;
import run.bareflow.core.definition.OnErrorDefinition.Action;
import run.bareflow.core.definition.RetryPolicy;
import run.bareflow.core.definition.StepDefinition;
import run.bareflow.core.engine.evaluator.StepEvaluator;
import run.bareflow.core.engine.invoker.StepInvoker;
import run.bareflow.core.exception.BusinessException;
import run.bareflow.core.exception.StepExecutionException;
import run.bareflow.core.exception.SystemException;
import run.bareflow.core.trace.StepTrace;

public class FlowEngineTest {
    // ------------------------------------------------------------
    // Helper: create StepDefinition
    // ------------------------------------------------------------
    private StepDefinition step(
            String name,
            Map<String, Object> input,
            Map<String, Object> output,
            RetryPolicy retry,
            OnErrorDefinition onError) {
        return new StepDefinition(
                name,
                "M",
                "op",
                input,
                output,
                retry,
                onError);
    }

    // ------------------------------------------------------------
    // Helper: create FlowDefinition
    // ------------------------------------------------------------
    private FlowDefinition flow(OnErrorDefinition flowOnError, StepDefinition... steps) {
        return new FlowDefinition(
                "testFlow",
                List.of(steps),
                flowOnError,
                Map.of() // metadata
        );
    }

    // ------------------------------------------------------------
    // 1. 正常系：1 ステップ成功
    // ------------------------------------------------------------
    @Test
    void testSingleStepSuccess() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        StepDefinition step = step("s1", Map.of(), Map.of(), null, null);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any())).thenReturn(Map.of());

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(null, step), new ExecutionContext());

        assertEquals(1, trace.getEntries().size());
        assertTrue(trace.isSuccess());
    }

    // ------------------------------------------------------------
    // 2. SystemException + RetryPolicy → retry して成功
    // ------------------------------------------------------------
    @Test
    void testRetryPolicyRetriesThenSucceeds() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        RetryPolicy retry = new RetryPolicy(2, 0);
        StepDefinition step = step("s1", Map.of(), Map.of(), retry, null);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new SystemException("fail"))
                .thenReturn(Map.of("ok", true));

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(null, step), new ExecutionContext());

        assertEquals(1, trace.getEntries().size());
        assertTrue(trace.isSuccess());
    }

    // ------------------------------------------------------------
    // 3. BusinessException → retry しない
    // ------------------------------------------------------------
    @Test
    void testBusinessExceptionNoRetry() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        OnErrorDefinition onError = new OnErrorDefinition(Action.CONTINUE, 0, null);
        StepDefinition step = step("s1", Map.of(), Map.of(), null, onError);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new BusinessException("biz"));

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(null, step), new ExecutionContext());

        assertEquals(1, trace.getEntries().size());
        assertFalse(trace.isSuccess());
    }

    // ------------------------------------------------------------
    // 4. onError = STOP → StepExecutionException
    // ------------------------------------------------------------
    @Test
    void testOnErrorStopThrows() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        OnErrorDefinition onError = new OnErrorDefinition(Action.STOP, 0, null);
        StepDefinition step = step("s1", Map.of(), Map.of(), null, onError);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new SystemException("fail"));

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        assertThrows(StepExecutionException.class, () -> engine.execute(flow(null, step), new ExecutionContext()));
    }

    // ------------------------------------------------------------
    // 5. onError = CONTINUE → 次のステップへ進む
    // ------------------------------------------------------------
    @Test
    void testOnErrorContinueMovesToNextStep() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        OnErrorDefinition onError = new OnErrorDefinition(Action.CONTINUE, 0, null);

        StepDefinition s1 = step("s1", Map.of(), Map.of(), null, onError);
        StepDefinition s2 = step("s2", Map.of(), Map.of(), null, null);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new SystemException("fail"))
                .thenReturn(Map.of());

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(null, s1, s2), new ExecutionContext());

        assertEquals(2, trace.getEntries().size());
        assertFalse(trace.isSuccess()); // s1 failed
    }

    // ------------------------------------------------------------
    // 6. onError = RETRY → 1 回だけ retry
    // ------------------------------------------------------------
    @Test
    void testOnErrorRetryRetriesOnce() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        OnErrorDefinition onError = new OnErrorDefinition(Action.RETRY, 0, null);
        StepDefinition step = step("s1", Map.of(), Map.of(), null, onError);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new SystemException("fail"))
                .thenReturn(Map.of());

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(null, step), new ExecutionContext());

        assertEquals(1, trace.getEntries().size());
        assertFalse(trace.isSuccess());
    }

    // ------------------------------------------------------------
    // 7. flow-level onError が step-level より後に適用される
    // ------------------------------------------------------------
    @Test
    void testFlowLevelOnErrorUsedWhenStepHasNoOnError() throws Exception {
        StepEvaluator evaluator = mock(StepEvaluator.class);
        StepInvoker invoker = mock(StepInvoker.class);

        OnErrorDefinition flowOnError = new OnErrorDefinition(Action.CONTINUE, 0, null);

        StepDefinition step = step("s1", Map.of(), Map.of(), null, null);

        when(evaluator.evaluateInput(any(), any())).thenReturn(Map.of());
        when(invoker.invoke(any(), any(), any()))
                .thenThrow(new SystemException("fail"));

        FlowEngine engine = new FlowEngine(evaluator, invoker);

        StepTrace trace = engine.execute(flow(flowOnError, step), new ExecutionContext());

        assertEquals(1, trace.getEntries().size());
        assertFalse(trace.isSuccess());
    }
}