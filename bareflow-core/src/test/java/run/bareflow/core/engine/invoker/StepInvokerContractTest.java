package run.bareflow.core.engine.invoker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import run.bareflow.core.exception.BusinessException;
import run.bareflow.core.exception.SystemException;

/**
 * Contract test for StepInvoker implementations.
 *
 * Concrete StepInvoker tests should extend this class and
 * provide an implementation of createInvoker().
 */
public abstract class StepInvokerContractTest {
    /**
     * Implementations must provide a StepInvoker instance.
     */
    protected abstract StepInvoker createInvoker();

    // ------------------------------------------------------------
    // Normal invocation
    // ------------------------------------------------------------
    @Test
    void testInvokeReturnsNonNullMap() throws Exception {
        StepInvoker invoker = createInvoker();

        Map<String, Object> result = invoker.invoke(
                "TestModule",
                "op",
                Map.of("x", 1));

        assertNotNull(result, "Invoker must not return null");
    }

    @Test
    void testInvokeReceivesModuleOperationAndInput() throws Exception {
        StepInvoker invoker = createInvoker();

        Map<String, Object> result = invoker.invoke(
                "MyModule",
                "doSomething",
                Map.of("a", 123));

        // Contract: implementation must return some map.
        assertNotNull(result);
    }

    // ------------------------------------------------------------
    // BusinessException
    // ------------------------------------------------------------
    @Test
    void testInvokeCanThrowBusinessException() {
        StepInvoker invoker = createInvoker();

        assertThrows(BusinessException.class, () -> {
            invoker.invoke("TestModule", "businessError", Map.of());
        });
    }

    // ------------------------------------------------------------
    // SystemException
    // ------------------------------------------------------------
    @Test
    void testInvokeCanThrowSystemException() {
        StepInvoker invoker = createInvoker();

        assertThrows(SystemException.class, () -> {
            invoker.invoke("TestModule", "systemError", Map.of());
        });
    }
}