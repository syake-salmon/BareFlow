package run.bareflow.core.definition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FlowDefinitionTest {
        @Test
        void testConstructorAndGetters() {
                StepDefinition step = new StepDefinition(
                                "step1",
                                "TestModule",
                                "op",
                                Map.of("in", 1),
                                Map.of("out", 2),
                                null,
                                null);

                OnErrorDefinition onError = new OnErrorDefinition(
                                OnErrorDefinition.Action.CONTINUE,
                                1000L,
                                Map.of("errorFlag", true));

                Map<String, Object> metadata = Map.of("version", "1.0");

                FlowDefinition flow = new FlowDefinition(
                                "testFlow",
                                List.of(step),
                                onError,
                                metadata);

                assertEquals("testFlow", flow.getName());
                assertEquals(1, flow.getSteps().size());
                assertSame(step, flow.getSteps().get(0));

                assertSame(onError, flow.getOnError());
                assertEquals(OnErrorDefinition.Action.CONTINUE, flow.getOnError().getAction());
                assertEquals(1000L, flow.getOnError().getDelayMillis());
                assertEquals(true, flow.getOnError().getOutput().get("errorFlag"));

                assertEquals("1.0", flow.getMetadata().get("version"));
        }

        @Test
        void testStepsListIsImmutable() {
                StepDefinition step = new StepDefinition(
                                "step1",
                                "TestModule",
                                "op",
                                Map.of(),
                                Map.of(),
                                null,
                                null);

                FlowDefinition flow = new FlowDefinition(
                                "flow",
                                List.of(step),
                                null,
                                Map.of());

                var steps = flow.getSteps();
                assertThrows(UnsupportedOperationException.class, () -> steps.add(step));
        }

        @Test
        void testMetadataIsImmutable() {
                FlowDefinition flow = new FlowDefinition(
                                "flow",
                                List.of(),
                                null,
                                Map.of("k", "v"));

                var metadata = flow.getMetadata();
                assertThrows(UnsupportedOperationException.class, () -> metadata.put("x", "y"));
        }

        @Test
        void testMetadataNullBecomesEmptyMap() {
                FlowDefinition flow = new FlowDefinition(
                                "flow",
                                List.of(),
                                null,
                                null);

                assertNotNull(flow.getMetadata());
                assertTrue(flow.getMetadata().isEmpty());
        }

        @Test
        void testStepsAreCopiedDefensively() {
                StepDefinition step = new StepDefinition(
                                "step1",
                                "TestModule",
                                "op",
                                Map.of(),
                                Map.of(),
                                null,
                                null);

                List<StepDefinition> original = new java.util.ArrayList<>();
                original.add(step);

                FlowDefinition flow = new FlowDefinition(
                                "flow",
                                original,
                                null,
                                Map.of());

                original.add(
                                new StepDefinition("step2", "M", "O", Map.of(), Map.of(), null, null));

                assertEquals(1, flow.getSteps().size());
                assertSame(step, flow.getSteps().get(0));
        }
}