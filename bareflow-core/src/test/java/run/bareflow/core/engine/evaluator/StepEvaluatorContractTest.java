package run.bareflow.core.engine.evaluator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import run.bareflow.core.context.ExecutionContext;

public abstract class StepEvaluatorContractTest {
    protected abstract StepEvaluator createEvaluator();

    protected ExecutionContext createContext() {
        ExecutionContext ctx = new ExecutionContext();
        ctx.put("name", "Alice");
        ctx.put("age", 30);
        ctx.put("city", "Tokyo");
        return ctx;
    }

    @Test
    void testEvaluateInput_literalValuesRemainAsIs() {
        StepEvaluator evaluator = createEvaluator();
        ExecutionContext ctx = createContext();

        Map<String, Object> input = Map.of(
                "x", 1,
                "y", "hello");

        Map<String, Object> result = evaluator.evaluateInput(input, ctx);

        assertEquals(1, result.get("x"));
        assertEquals("hello", result.get("y"));
    }

    @Test
    void testEvaluateInput_placeholdersAreResolvedFromContext() {
        StepEvaluator evaluator = createEvaluator();
        ExecutionContext ctx = createContext();

        Map<String, Object> input = Map.of(
                "user", "${name}",
                "location", "${city}");

        Map<String, Object> result = evaluator.evaluateInput(input, ctx);

        assertEquals("Alice", result.get("user"));
        assertEquals("Tokyo", result.get("location"));
    }

    @Test
    void testEvaluateOutput_literalValuesRemainAsIs() {
        StepEvaluator evaluator = createEvaluator();
        ExecutionContext ctx = createContext();

        Map<String, Object> outputMapping = Map.of(
                "x", 1,
                "y", "hello");

        Map<String, Object> rawOutput = Map.of();

        Map<String, Object> result = evaluator.evaluateOutput(outputMapping, rawOutput, ctx);

        assertEquals(1, result.get("x"));
        assertEquals("hello", result.get("y"));
    }

    @Test
    void testEvaluateOutput_placeholdersResolvedFromRawOutputFirst() {
        StepEvaluator evaluator = createEvaluator();
        ExecutionContext ctx = createContext();

        Map<String, Object> outputMapping = Map.of(
                "value", "${result}");

        Map<String, Object> rawOutput = Map.of(
                "result", 999);

        Map<String, Object> result = evaluator.evaluateOutput(outputMapping, rawOutput, ctx);

        assertEquals(999, result.get("value"));
    }

    @Test
    void testEvaluateOutput_fallbackToContextIfNotInRawOutput() {
        StepEvaluator evaluator = createEvaluator();
        ExecutionContext ctx = createContext();

        Map<String, Object> outputMapping = Map.of(
                "cityOut", "${city}");

        Map<String, Object> rawOutput = Map.of();

        Map<String, Object> result = evaluator.evaluateOutput(outputMapping, rawOutput, ctx);

        assertEquals("Tokyo", result.get("cityOut"));
    }
}