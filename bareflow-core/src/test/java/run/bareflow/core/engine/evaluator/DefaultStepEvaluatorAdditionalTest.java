package run.bareflow.core.engine.evaluator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import run.bareflow.core.context.ExecutionContext;

public class DefaultStepEvaluatorAdditionalTest {
    private final DefaultStepEvaluator evaluator = new DefaultStepEvaluator();

    private ExecutionContext ctx() {
        ExecutionContext ctx = new ExecutionContext();
        ctx.put("name", "Alice");
        return ctx;
    }

    @Test
    void testUnresolvedPlaceholderBecomesNull() {
        Map<String, Object> input = Map.of("x", "${unknown}");

        Map<String, Object> result = evaluator.evaluateInput(input, ctx());

        assertNull(result.get("x"));
    }

    @Test
    void testNonStringLiteralIsReturnedAsIs() {
        Map<String, Object> input = Map.of("x", 123);

        Map<String, Object> result = evaluator.evaluateInput(input, ctx());

        assertEquals(123, result.get("x"));
    }

    @Test
    void testNestedPlaceholderIsTreatedAsLiteral() {
        Map<String, Object> input = Map.of("x", "${a.b}");

        Map<String, Object> result = evaluator.evaluateInput(input, ctx());

        // isPlaceholder("${a.b}") → true（構文上はプレースホルダ扱い）
        // しかし ctx に "a.b" が無い → null
        assertNull(result.get("x"));
    }

    @Test
    void testRawOutputNullStillResolvesFromContext() {
        Map<String, Object> outputMapping = Map.of("x", "${name}");

        Map<String, Object> result = evaluator.evaluateOutput(outputMapping, null, ctx());

        assertEquals("Alice", result.get("x"));
    }

    @Test
    void testLiteralStringNotPlaceholder() {
        Map<String, Object> input = Map.of("x", "hello ${world");

        Map<String, Object> result = evaluator.evaluateInput(input, ctx());

        assertEquals("hello ${world", result.get("x"));
    }
}