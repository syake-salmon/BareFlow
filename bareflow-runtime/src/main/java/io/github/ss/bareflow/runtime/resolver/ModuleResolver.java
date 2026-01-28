package io.github.ss.bareflow.runtime.resolver;

/**
 * Resolves a module name to a Java Class.
 * This abstraction allows different resolution strategies:
 * - classpath-based
 * - filesystem-based
 * - DI container-based
 * - custom application logic
 */
public interface ModuleResolver {
    /**
     * Resolve a module name to a Java Class.
     *
     * @param moduleName logical module name from YAML
     * @return resolved Class
     */
    Class<?> resolve(String moduleName);
}
