package run.bareflow.core.context;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ExecutionContextTest {
    @Test
    void testPutAndGet() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("x", 10);
        ctx.put("y", "hello");

        assertEquals(10, ctx.get("x"));
        assertEquals("hello", ctx.get("y"));
    }

    @Test
    void testContains() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("key", 123);

        assertTrue(ctx.contains("key"));
        assertFalse(ctx.contains("missing"));
    }

    @Test
    void testMergeAddsValues() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("a", 1);
        ctx.merge(Map.of("b", 2));

        assertEquals(1, ctx.get("a"));
        assertEquals(2, ctx.get("b"));
    }

    @Test
    void testMergeOverwritesExistingKeys() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("a", 1);
        ctx.merge(Map.of("a", 999));

        assertEquals(999, ctx.get("a"));
    }

    @Test
    void testMergeNullDoesNothing() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("a", 1);
        ctx.merge(null);

        assertEquals(1, ctx.get("a"));
    }

    @Test
    void testSnapshotIsImmutable() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("x", 10);

        Map<String, Object> snapshot = ctx.snapshot();

        assertEquals(10, snapshot.get("x"));
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("y", 20));
    }

    @Test
    void testSnapshotIsDefensiveCopy() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("x", 10);

        Map<String, Object> snapshot = ctx.snapshot();

        ctx.put("x", 999);

        // snapshot は変更されない
        assertEquals(10, snapshot.get("x"));
    }

    @Test
    void testViewIsImmutableButReflectsLiveData() {
        ExecutionContext ctx = new ExecutionContext();

        ctx.put("x", 10);

        Map<String, Object> view = ctx.view();

        assertEquals(10, view.get("x"));
        assertThrows(UnsupportedOperationException.class, () -> view.put("y", 20));

        // view は live view なので変更が反映される
        ctx.put("x", 999);
        assertEquals(999, view.get("x"));
    }
}