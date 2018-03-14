package au.id.tmm.utilities.logging

import java.util.Objects

import org.apache.commons.lang3.StringUtils
import org.slf4j

/**
  * A wrapper around an [[slf4j.Logger]] to support rudimentary structured logging.
  */
case class Logger (underlying: slf4j.Logger) {

  import Logger._

  /**
    * Logs an event with the given id and key-value pairs at the `ERROR` level.
    */
  @inline
  def error(eventId: String, kvPairs: (String, Any)*): Unit =
    if (underlying.isErrorEnabled) underlying.error(format(eventId, kvPairs))

  /**
    * Logs the given event at the `ERROR` level.
    */
  @inline
  def error(loggedEvent: LoggedEvent): Unit =
    if (underlying.isErrorEnabled) {
      val msg = format(loggedEvent.eventId, loggedEvent.kvPairs)
      if (loggedEvent.exception.isDefined) {
        underlying.error(msg, loggedEvent.exception.get)
      } else {
        underlying.error(msg)
      }
    }

  /**
    * Logs an event with the given id and key-value pairs at the `ERROR` level. The key-value pairs are only evaluated
    * if the `ERROR` level is enabled.
    */
  @inline
  def lazyEvalError(eventId: String, kvPairs: => Iterable[(String, Any)]): Unit =
    if (underlying.isErrorEnabled) underlying.error(format(eventId, kvPairs))

  /**
    * Logs an event with the given id and key-value pairs at the `WARN` level.
    */
  @inline
  def warn(eventId: String, kvPairs: (String, Any)*): Unit =
    if (underlying.isWarnEnabled) underlying.warn(format(eventId, kvPairs))

  /**
    * Logs the given event at the `WARN` level.
    */
  @inline
  def warn(loggedEvent: LoggedEvent): Unit =
    if (underlying.isWarnEnabled) {
      val msg = format(loggedEvent.eventId, loggedEvent.kvPairs)
      if (loggedEvent.exception.isDefined) {
        underlying.warn(msg, loggedEvent.exception.get)
      } else {
        underlying.warn(msg)
      }
    }

  /**
    * Logs an event with the given id and key-value pairs at the `WARN` level. The key-value pairs are only evaluated
    * if the `WARN` level is enabled.
    */
  @inline
  def lazyEvalWarn(eventId: String, kvPairs: => Iterable[(String, Any)]): Unit =
    if (underlying.isWarnEnabled) underlying.warn(format(eventId, kvPairs))

  /**
    * Logs an event with the given id and key-value pairs at the `INFO` level.
    */
  @inline
  def info(eventId: String, kvPairs: (String, Any)*): Unit =
    if (underlying.isInfoEnabled) underlying.info(format(eventId, kvPairs))

  /**
    * Logs the given event at the `INFO` level.
    */
  @inline
  def info(loggedEvent: LoggedEvent): Unit =
    if (underlying.isInfoEnabled) {
      val msg = format(loggedEvent.eventId, loggedEvent.kvPairs)
      if (loggedEvent.exception.isDefined) {
        underlying.info(msg, loggedEvent.exception.get)
      } else {
        underlying.info(msg)
      }
    }

  /**
    * Logs an event with the given id and key-value pairs at the `INFO` level. The key-value pairs are only evaluated
    * if the `INFO` level is enabled.
    */
  @inline
  def lazyEvalInfo(eventId: String, kvPairs: => Iterable[(String, Any)]): Unit =
    if (underlying.isInfoEnabled) underlying.info(format(eventId, kvPairs))

  /**
    * Logs an event with the given id and key-value pairs at the `DEBUG` level.
    */
  @inline
  def debug(eventId: String, kvPairs: (String, Any)*): Unit =
    if (underlying.isDebugEnabled) underlying.debug(format(eventId, kvPairs))

  /**
    * Logs the given event at the `DEBUG` level.
    */
  @inline
  def debug(loggedEvent: LoggedEvent): Unit =
    if (underlying.isDebugEnabled) {
      val msg = format(loggedEvent.eventId, loggedEvent.kvPairs)
      if (loggedEvent.exception.isDefined) {
        underlying.debug(msg, loggedEvent.exception.get)
      } else {
        underlying.debug(msg)
      }
    }

  /**
    * Logs an event with the given id and key-value pairs at the `DEBUG` level. The key-value pairs are only evaluated
    * if the `DEBUG` level is enabled.
    */
  @inline
  def lazyEvalDebug(eventId: String, kvPairs: => Iterable[(String, Any)]): Unit =
    if (underlying.isDebugEnabled) underlying.debug(format(eventId, kvPairs))

  /**
    * Logs an event with the given id and key-value pairs at the `TRACE` level.
    */
  @inline
  def trace(eventId: String, kvPairs: (String, Any)*): Unit =
    if (underlying.isTraceEnabled) underlying.trace(format(eventId, kvPairs))

  /**
    * Logs the given event at the `TRACE` level.
    */
  @inline
  def trace(loggedEvent: LoggedEvent): Unit =
    if (underlying.isTraceEnabled) {
      val msg = format(loggedEvent.eventId, loggedEvent.kvPairs)
      if (loggedEvent.exception.isDefined) {
        underlying.trace(msg, loggedEvent.exception.get)
      } else {
        underlying.trace(msg)
      }
    }

  /**
    * Logs an event with the given id and key-value pairs at the `TRACE` level. The key-value pairs are only evaluated
    * if the `TRACE` level is enabled.
    */
  @inline
  def lazyEvalTrace(eventId: String, kvPairs: => Iterable[(String, Any)]): Unit =
    if (underlying.isTraceEnabled) underlying.trace(format(eventId, kvPairs))

}

object Logger {

  /**
    * Constructs a `Logger` with the given name, stripping any trailing `$`, as occurs in a Scala object class name.
    */
  def apply(name: String): Logger = {
    val className = name.stripSuffix("$")

    val underlying = slf4j.LoggerFactory.getLogger(className)

    new Logger(underlying)
  }

  /**
    * Constructs a `Logger` for the given class. Strips the trailing `$` from the class name so that a class and its
    * companion object have loggers with the same name.
    */
  def apply(cls: Class[_]): Logger = apply(cls.getName)

  /**
    * Constructs a `Logger` for the class that encloses the call to this method. This is achieved by obtaining the
    * stacktrace element immediately prior to this method call.
    */
  def apply(): Logger = {
    val stackTrace = Thread.currentThread().getStackTrace

    val callingClassName = stackTrace
      .toStream
      .filterNot(_.getClassName == classOf[Thread].getName)
      .filterNot(_.getClassName == getClass.getName)
      .head
      .getClassName

    Logger(callingClassName)
  }

  private[logging] def format(eventId: String,
                              kvPairs: Iterable[(String, Any)],
                             ): String = {
    val logMessage = new StringBuilder

    logMessage.append("event_id=").append(escapeForFormat(eventId))

    for ((key, valueObject) <- kvPairs) {
      val valueString = Objects.toString(valueObject, null)

      logMessage.append(separatorChar).append(" ")

      logMessage.append(key).append("=")

      logMessage.append(escapeForFormat(valueString))
    }

    logMessage.toString
  }

  private val nullRepresentation = "<null>"

  private val quoteChar = '"'
  private val separatorChar = ';'
  private val equalityChar = '='
  private val escapeChar = '\\'

  private val searchChars = Array(quoteChar, separatorChar, equalityChar, escapeChar)

  private val searchStrings: Array[String] = searchChars.map(String.valueOf)
  private val replacements = searchStrings.map("" + escapeChar + _)

  private def escapeForFormat(value: String): String = {
    if (value eq null) {
      nullRepresentation
    } else if (StringUtils.containsAny(value, searchChars) || value == nullRepresentation || StringUtils.containsWhitespace(value)) {
      val output = new StringBuilder

      output.append(quoteChar)
      output.append(StringUtils.replaceEach(value, searchStrings, replacements))
      output.append(quoteChar)

      output.toString()
    } else {
      value
    }
  }

}