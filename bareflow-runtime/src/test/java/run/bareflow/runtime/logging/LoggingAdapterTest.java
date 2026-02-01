package run.bareflow.runtime.logging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import run.bareflow.core.definition.FlowDefinition;
import run.bareflow.core.engine.event.FlowEngineEvent;
import run.bareflow.core.engine.event.FlowEngineEvent.FlowStartEvent;

public class LoggingAdapterTest {
    @Test
    public void traceLevelCallsFormatTrace() {
        Logger logger = mock(Logger.class);
        when(logger.isTraceEnabled()).thenReturn(true);

        LogFormatter formatter = mock(LogFormatter.class);
        when(formatter.formatTrace(any())).thenReturn("trace-log");

        LoggingAdapter adapter = new LoggingAdapter(logger, formatter);

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        adapter.onEvent(event);

        verify(formatter).formatTrace(event);
        verify(logger).trace("trace-log");
    }

    @Test
    public void debugLevelCallsFormatDebug() {
        Logger logger = mock(Logger.class);
        when(logger.isDebugEnabled()).thenReturn(true);

        LogFormatter formatter = mock(LogFormatter.class);
        when(formatter.formatDebug(any())).thenReturn("debug-log");

        LoggingAdapter adapter = new LoggingAdapter(logger, formatter);

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        adapter.onEvent(event);

        verify(formatter).formatDebug(event);
        verify(logger).debug("debug-log");
    }

    @Test
    public void infoLevelCallsFormatInfo() {
        Logger logger = mock(Logger.class);
        when(logger.isInfoEnabled()).thenReturn(true);

        LogFormatter formatter = mock(LogFormatter.class);
        when(formatter.formatInfo(any())).thenReturn("info-log");

        LoggingAdapter adapter = new LoggingAdapter(logger, formatter);

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        adapter.onEvent(event);

        verify(formatter).formatInfo(event);
        verify(logger).info("info-log");
    }

    @Test
    public void warnLevelCallsFormatError() {
        Logger logger = mock(Logger.class);
        when(logger.isWarnEnabled()).thenReturn(true);

        LogFormatter formatter = mock(LogFormatter.class);
        when(formatter.formatError(any())).thenReturn("warn-log");

        LoggingAdapter adapter = new LoggingAdapter(logger, formatter);

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        adapter.onEvent(event);

        verify(formatter).formatError(event);
        verify(logger).error("warn-log");
    }

    @Test
    public void errorLevelCallsFormatError() {
        Logger logger = mock(Logger.class);
        when(logger.isErrorEnabled()).thenReturn(true);

        LogFormatter formatter = mock(LogFormatter.class);
        when(formatter.formatError(any())).thenReturn("error-log");

        LoggingAdapter adapter = new LoggingAdapter(logger, formatter);

        FlowEngineEvent event = new FlowStartEvent(mock(FlowDefinition.class), Instant.now());

        adapter.onEvent(event);

        verify(formatter).formatError(event);
        verify(logger).error("error-log");
    }
}
