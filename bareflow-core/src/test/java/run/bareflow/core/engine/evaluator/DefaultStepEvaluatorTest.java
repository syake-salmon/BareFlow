package run.bareflow.core.engine.evaluator;

public class DefaultStepEvaluatorTest extends StepEvaluatorContractTest {
    @Override
    protected StepEvaluator createEvaluator() {
        return new DefaultStepEvaluator();
    }
}