package run.bareflow.core.resolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import run.bareflow.core.definition.FlowDefinition;

/**
 * Contract test for FlowDefinitionResolver implementations.
 *
 * Concrete resolver tests should extend this class and provide
 * an implementation of createResolver().
 */
public abstract class FlowDefinitionResolverContractTest {
    /**
     * Implementations must provide a resolver instance.
     */
    protected abstract FlowDefinitionResolver createResolver();

    @Test
    void testResolveReturnsFlowDefinition() {
        FlowDefinitionResolver resolver = createResolver();

        FlowDefinition def = resolver.resolve("testFlow");

        assertNotNull(def, "Resolver must return a FlowDefinition");
        assertNotNull(def.getName(), "FlowDefinition must have a name");
        assertNotNull(def.getSteps(), "FlowDefinition must have steps");
    }

    @Test
    void testResolveUnknownFlowThrowsException() {
        FlowDefinitionResolver resolver = createResolver();

        assertThrows(RuntimeException.class, () -> resolver.resolve("unknownFlow"));
    }

    @Test
    void testResolveNullThrowsException() {
        FlowDefinitionResolver resolver = createResolver();

        assertThrows(RuntimeException.class, () -> resolver.resolve(null));
    }
}