package run.bareflow.runtime.logging;

import run.bareflow.core.engine.event.FlowEngineEvent;

/**
 * Strategy interface for formatting {@link FlowEngineEvent} instances into
 * log-ready strings.
 * <p>
 * A {@code LogFormatter} is responsible only for converting an event into a
 * textual
 * representation. It does not decide <em>which</em> events are logged or at
 * which
 * SLF4J log level they are emitted. That responsibility belongs to
 * {@link LoggingAdapter}, which delegates to this formatter based on the
 * currently enabled log level.
 * </p>
 *
 * <p>
 * Implementations may produce any format—key/value, JSON, CSV, or custom
 * layouts.
 * Users can plug in their own formatter by providing an implementation and
 * passing it to {@link LoggingAdapter}.
 * </p>
 *
 * <p>
 * Each method corresponds to a log-level category used by
 * {@link LoggingAdapter}.
 * The formatter may choose to vary the level of detail depending on the method,
 * or return the same structure for all levels.
 * </p>
 */
public interface LogFormatter {
    /**
     * Formats the given event for error-level logging.
     *
     * @param event the event to format
     * @return a formatted string suitable for error-level logs
     */
    String formatError(FlowEngineEvent event);

    /**
     * Formats the given event for info-level logging.
     *
     * @param event the event to format
     * @return a formatted string suitable for info-level logs
     */
    String formatInfo(FlowEngineEvent event);

    /**
     * Formats the given event for debug-level logging.
     *
     * @param event the event to format
     * @return a formatted string suitable for debug-level logs
     */
    String formatDebug(FlowEngineEvent event);

    /**
     * Formats the given event for trace-level logging.
     *
     * @param event the event to format
     * @return a formatted string suitable for trace-level logs
     */
    String formatTrace(FlowEngineEvent event);
}