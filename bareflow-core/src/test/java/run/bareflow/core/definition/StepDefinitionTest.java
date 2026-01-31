package run.bareflow.core.definition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class StepDefinitionTest {
    @Test
    void testConstructorAndGetters() {
        RetryPolicy retry = new RetryPolicy(3, 1000);
        OnErrorDefinition onError = new OnErrorDefinition(
                OnErrorDefinition.Action.CONTINUE,
                500L,
                Map.of("flag", true));

        StepDefinition step = new StepDefinition(
                "step1",
                "TestModule",
                "op",
                Map.of("in", 1),
                Map.of("out", 2),
                retry,
                onError);

        assertEquals("step1", step.getName());
        assertEquals("TestModule", step.getModule());
        assertEquals("op", step.getOperation());

        assertEquals(1, step.getInput().get("in"));
        assertEquals(2, step.getOutput().get("out"));

        assertSame(retry, step.getRetryPolicy());
        assertSame(onError, step.getOnError());
    }

    @Test
    void testInputIsUnmodifiable() {
        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                Map.of("a", 1),
                Map.of(),
                null,
                null);

        assertThrows(UnsupportedOperationException.class, () -> {
            step.getInput().put("b", 2);
        });
    }

    @Test
    void testOutputIsUnmodifiable() {
        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                Map.of(),
                Map.of("a", 1),
                null,
                null);

        assertThrows(UnsupportedOperationException.class, () -> {
            step.getOutput().put("b", 2);
        });
    }

    @Test
    void testInputNullBecomesEmptyMap() {
        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                null,
                Map.of(),
                null,
                null);

        assertNotNull(step.getInput());
        assertTrue(step.getInput().isEmpty());
    }

    @Test
    void testOutputNullBecomesEmptyMap() {
        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                Map.of(),
                null,
                null,
                null);

        assertNotNull(step.getOutput());
        assertTrue(step.getOutput().isEmpty());
    }

    @Test
    void testRetryPolicyIsStoredAsIs() {
        RetryPolicy retry = new RetryPolicy(5, 2000);

        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                Map.of(),
                Map.of(),
                retry,
                null);

        assertSame(retry, step.getRetryPolicy());
    }

    @Test
    void testOnErrorIsStoredAsIs() {
        OnErrorDefinition onError = new OnErrorDefinition(
                OnErrorDefinition.Action.RETRY,
                300L,
                Map.of("x", 1));

        StepDefinition step = new StepDefinition(
                "s",
                "m",
                "o",
                Map.of(),
                Map.of(),
                null,
                onError);

        assertSame(onError, step.getOnError());
    }
}