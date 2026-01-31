package run.bareflow.core.definition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class OnErrorDefinitionTest {
    @Test
    void testConstructorAndGetters() {
        OnErrorDefinition def = new OnErrorDefinition(
                OnErrorDefinition.Action.RETRY,
                1500L,
                Map.of("flag", true));

        assertEquals(OnErrorDefinition.Action.RETRY, def.getAction());
        assertEquals(1500L, def.getDelayMillis());
        assertEquals(true, def.getOutput().get("flag"));
    }

    @Test
    void testOutputIsUnmodifiable() {
        OnErrorDefinition def = new OnErrorDefinition(
                OnErrorDefinition.Action.CONTINUE,
                0L,
                Map.of("x", 1));

        assertThrows(UnsupportedOperationException.class, () -> {
            def.getOutput().put("y", 2);
        });
    }

    @Test
    void testOutputNullBecomesEmptyMap() {
        OnErrorDefinition def = new OnErrorDefinition(
                OnErrorDefinition.Action.STOP,
                0L,
                null);

        assertNotNull(def.getOutput());
        assertTrue(def.getOutput().isEmpty());
    }

    @Test
    void testOutputIsDefensiveCopy() {
        Map<String, Object> original = Map.of("a", 1);

        OnErrorDefinition def = new OnErrorDefinition(
                OnErrorDefinition.Action.CONTINUE,
                0L,
                original);

        assertNotSame(original, def.getOutput());
    }
}