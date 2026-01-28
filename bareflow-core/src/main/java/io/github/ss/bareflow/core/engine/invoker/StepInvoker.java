package io.github.ss.bareflow.core.engine.invoker;

import io.github.ss.bareflow.core.exception.BusinessException;
import io.github.ss.bareflow.core.exception.SystemException;

import java.util.Map;

/**
 * Invokes a module operation with evaluated input.
 * The implementation is provided by the application layer.
 */
public interface StepInvoker {
    /**
     * Invoke a module's operation.
     *
     * @param module    module name
     * @param operation operation name within the module
     * @param input     evaluated input map
     * @return output map to be merged into ExecutionContext
     *
     * @throws BusinessException domain-specific failure (no retry)
     * @throws SystemException   infrastructure failure (may retry)
     */
    Map<String, Object> invoke(String module,
            String operation,
            Map<String, Object> input)
            throws BusinessException, SystemException;
}