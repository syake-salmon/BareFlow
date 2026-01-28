package io.github.ss.bareflow.runtime.executor;

import io.github.ss.bareflow.core.context.ExecutionContext;
import io.github.ss.bareflow.core.definition.FlowDefinition;
import io.github.ss.bareflow.core.engine.FlowEngine;
import io.github.ss.bareflow.core.engine.StepEvaluator;
import io.github.ss.bareflow.core.engine.StepInvoker;
import io.github.ss.bareflow.core.trace.StepTrace;
import io.github.ss.bareflow.runtime.parser.FlowYamlParser;

import java.io.InputStream;

/**
 * High-level executor that ties together:
 * - YAML parsing
 * - FlowEngine execution
 * - Result aggregation
 *
 * This class is intentionally minimal. It does not manage DI, logging,
 * module resolution, or any application-specific concerns.
 */
public class FlowExecutor {
    private final FlowYamlParser yamlParser;
    private final FlowEngine flowEngine;

    /**
     * @param evaluator StepEvaluator implementation
     * @param invoker   StepInvoker implementation
     */
    public FlowExecutor(StepEvaluator evaluator, StepInvoker invoker) {
        this.yamlParser = new FlowYamlParser();
        this.flowEngine = new FlowEngine(evaluator, invoker);
    }

    /**
     * Execute a flow from a YAML InputStream.
     *
     * @param yamlInput YAML file input
     * @return FlowResult containing context and trace
     */
    public FlowResult execute(InputStream yamlInput) {
        FlowDefinition definition = yamlParser.parse(yamlInput);

        ExecutionContext ctx = new ExecutionContext();
        StepTrace trace = new StepTrace();

        flowEngine.execute(definition, ctx, trace);

        return new FlowResult(ctx, trace);
    }
}
