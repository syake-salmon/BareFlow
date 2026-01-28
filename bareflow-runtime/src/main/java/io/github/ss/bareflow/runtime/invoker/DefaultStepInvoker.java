package io.github.ss.bareflow.runtime.invoker;

import io.github.ss.bareflow.core.engine.StepInvoker;
import io.github.ss.bareflow.core.exception.BusinessException;
import io.github.ss.bareflow.core.exception.SystemException;
import io.github.ss.bareflow.runtime.resolver.ModuleResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Default StepInvoker implementation using a ModuleResolver.
 */
public class DefaultStepInvoker implements StepInvoker {
    private final ModuleResolver moduleResolver;

    public DefaultStepInvoker(ModuleResolver moduleResolver) {
        this.moduleResolver = moduleResolver;
    }

    @Override
    public Map<String, Object> invoke(String module,
            String operation,
            Map<String, Object> input) {

        try {
            Class<?> clazz = moduleResolver.resolve(module);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Method method = resolveMethod(clazz, operation);

            Object result = method.invoke(instance, input);

            if (!(result instanceof Map)) {
                throw new SystemException(
                        "StepInvoker: method must return Map<String,Object>. " +
                                "module=" + module + ", operation=" + operation);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> output = (Map<String, Object>) result;
            return output;

        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof BusinessException) {
                throw (BusinessException) target;
            }
            throw new SystemException("Error during step invocation", target);

        } catch (Exception e) {
            throw new SystemException("Failed to invoke step", e);
        }
    }

    private Method resolveMethod(Class<?> clazz, String operation) {
        try {
            return clazz.getMethod(operation, Map.class);
        } catch (NoSuchMethodException e) {
            throw new SystemException(
                    "Operation method not found: " + clazz.getName() + "#" + operation + "(Map)", e);
        }
    }
}