package run.bareflow.runtime.logging;

import org.slf4j.Logger;

import run.bareflow.core.engine.event.FlowEngineEvent;
import run.bareflow.core.engine.event.FlowEngineEventListener;

/**
 * {@link FlowEngineEventListener} implementation that emits
 * {@link FlowEngineEvent}
 * instances to an SLF4J {@link Logger} using a pluggable {@link LogFormatter}.
 *
 * <p>
 * The {@code LoggingAdapter} does not perform any interpretation of the event
 * itself. Its sole responsibility is to:
 * </p>
 *
 * <ul>
 * <li>determine the active SLF4J log level,</li>
 * <li>delegate formatting to the corresponding method on the configured
 * {@link LogFormatter}, and</li>
 * <li>emit the resulting string to the appropriate SLF4J logging method.</li>
 * </ul>
 *
 * <p>
 * This design keeps the adapter minimal and predictable while allowing users to
 * fully customize log output by supplying their own {@link LogFormatter}
 * implementation. The adapter does not enforce any particular log structure
 * (key/value, JSON, etc.) nor does it decide which events should be logged;
 * those concerns are delegated to SLF4J configuration and the formatter.
 * </p>
 *
 * <p>
 * Log-level mapping is intentionally simple:
 * </p>
 *
 * <ul>
 * <li>{@code TRACE} → {@link LogFormatter#formatTrace(FlowEngineEvent)}</li>
 * <li>{@code DEBUG} → {@link LogFormatter#formatDebug(FlowEngineEvent)}</li>
 * <li>{@code INFO} → {@link LogFormatter#formatInfo(FlowEngineEvent)}</li>
 * <li>{@code WARN}/{@code ERROR} →
 * {@link LogFormatter#formatError(FlowEngineEvent)}</li>
 * </ul>
 *
 * <p>
 * If multiple log levels are enabled, the most verbose level takes precedence.
 * </p>
 */
public final class LoggingAdapter implements FlowEngineEventListener {
    private final Logger log;
    private final LogFormatter formatter;

    /**
     * Creates a new adapter that logs events using the given SLF4J logger and
     * formatter.
     *
     * @param logger    the SLF4J logger to emit log lines to
     * @param formatter the formatter responsible for converting events into log
     *                  strings
     */
    public LoggingAdapter(Logger logger, LogFormatter formatter) {
        this.log = logger;
        this.formatter = formatter;
    }

    @Override
    public void onEvent(FlowEngineEvent event) {
        if (log.isTraceEnabled()) {
            log.trace(formatter.formatTrace(event));
        } else if (log.isDebugEnabled()) {
            log.debug(formatter.formatDebug(event));
        } else if (log.isInfoEnabled()) {
            log.info(formatter.formatInfo(event));
        } else if (log.isWarnEnabled() || log.isErrorEnabled()) {
            log.error(formatter.formatError(event));
        }
    }
}