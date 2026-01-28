package io.github.ss.bareflow.runtime.resolver;

import io.github.ss.bareflow.core.exception.SystemException;

/**
 * Default implementation of ModuleResolver.
 * Resolves modules by combining a base package and the module name.
 */
public class DefaultModuleResolver implements ModuleResolver {
    private final String basePackage;

    /**
     * @param basePackage base package where module classes are located
     */
    public DefaultModuleResolver(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public Class<?> resolve(String moduleName) {
        String className = basePackage + "." + moduleName;

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new SystemException("Module class not found: " + className, e);
        }
    }
}
