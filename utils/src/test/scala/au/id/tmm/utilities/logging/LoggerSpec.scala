package au.id.tmm.utilities.logging

import au.id.tmm.utilities.testing.ImprovedFlatSpec
import org.scalamock.scalatest.MockFactory

class LoggerSpec extends ImprovedFlatSpec with MockFactory {

  val testLogger = Logger()

  "a logger constructed for a class" should "use that class's name as the name of the underlying logger" in {
    val logger = Logger(this.getClass)

    assert(logger.underlying.getName === "au.id.tmm.utilities.logging.LoggerSpec")
  }

  "a logger constructed with a custom name" should "use that name for the underlying logger" in {
    val name = "asdf"

    val logger = Logger(name)

    assert(logger.underlying.getName === name)
  }

  "a logger constructed for an object" should "not end in a dollar sign" in {
    val logger = Logger(TestObject.getClass)

    assert(logger.underlying.getName === "au.id.tmm.utilities.logging.LoggerSpec$TestObject")
  }

  "a logger constructed without a name" should "use the name of the calling class" in {
    assert(testLogger.underlying.getName === "au.id.tmm.utilities.logging.LoggerSpec")
  }

  "error logging" should "support logging key value pairs against an event" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isErrorEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.error("eventId", "key" -> "value")

    (underlying.error(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isErrorEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.error(LoggedEvent("eventId", "key" -> "value"))

    (underlying.error(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent with an exception" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isErrorEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    val exception = new RuntimeException()

    val loggedEvent = LoggedEvent("eventId", "key" -> "value")

    loggedEvent.exception = Some(exception)

    logger.error(loggedEvent)

    (underlying.error(_: String, _: Throwable)).verify("event_id=eventId; key=value", exception)
  }

  it should "support lazily logging an iterable" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isErrorEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.lazyEvalError("eventId", Seq("key" -> "value"))

    (underlying.error(_: String)).verify("event_id=eventId; key=value")
  }

  "warn logging" should "support logging key value pairs against an event" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isWarnEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.warn("eventId", "key" -> "value")

    (underlying.warn(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isWarnEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.warn(LoggedEvent("eventId", "key" -> "value"))

    (underlying.warn(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent with an exception" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isWarnEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    val exception = new RuntimeException()

    val loggedEvent = LoggedEvent("eventId", "key" -> "value")

    loggedEvent.exception = Some(exception)

    logger.warn(loggedEvent)

    (underlying.warn(_: String, _: Throwable)).verify("event_id=eventId; key=value", exception)
  }

  it should "support lazily logging an iterable" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isWarnEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.lazyEvalWarn("eventId", Seq("key" -> "value"))

    (underlying.warn(_: String)).verify("event_id=eventId; key=value")
  }

  "info logging" should "support logging key value pairs against an event" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isInfoEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.info("eventId", "key" -> "value")

    (underlying.info(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isInfoEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.info(LoggedEvent("eventId", "key" -> "value"))

    (underlying.info(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent with an exception" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isInfoEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    val exception = new RuntimeException()

    val loggedEvent = LoggedEvent("eventId", "key" -> "value")

    loggedEvent.exception = Some(exception)

    logger.info(loggedEvent)

    (underlying.info(_: String, _: Throwable)).verify("event_id=eventId; key=value", exception)
  }

  it should "support lazily logging an iterable" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isInfoEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.lazyEvalInfo("eventId", Seq("key" -> "value"))

    (underlying.info(_: String)).verify("event_id=eventId; key=value")
  }

  "debug logging" should "support logging key value pairs against an event" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isDebugEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.debug("eventId", "key" -> "value")

    (underlying.debug(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isDebugEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.debug(LoggedEvent("eventId", "key" -> "value"))

    (underlying.debug(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent with an exception" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isDebugEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    val exception = new RuntimeException()

    val loggedEvent = LoggedEvent("eventId", "key" -> "value")

    loggedEvent.exception = Some(exception)

    logger.debug(loggedEvent)

    (underlying.debug(_: String, _: Throwable)).verify("event_id=eventId; key=value", exception)
  }

  it should "support lazily logging an iterable" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isDebugEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.lazyEvalDebug("eventId", Seq("key" -> "value"))

    (underlying.debug(_: String)).verify("event_id=eventId; key=value")
  }

  "trace logging" should "support logging key value pairs against an event" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isTraceEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.trace("eventId", "key" -> "value")

    (underlying.trace(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isTraceEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.trace(LoggedEvent("eventId", "key" -> "value"))

    (underlying.trace(_: String)).verify("event_id=eventId; key=value")
  }

  it should "support logging a LoggedEvent with an exception" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isTraceEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    val exception = new RuntimeException()

    val loggedEvent = LoggedEvent("eventId", "key" -> "value")

    loggedEvent.exception = Some(exception)

    logger.trace(loggedEvent)

    (underlying.trace(_: String, _: Throwable)).verify("event_id=eventId; key=value", exception)
  }

  it should "support lazily logging an iterable" in {
    val underlying = stub[org.slf4j.Logger]
    (underlying.isTraceEnabled _: () => Boolean).when().returns(true)

    val logger = new Logger(underlying)

    logger.lazyEvalTrace("eventId", Seq("key" -> "value"))

    (underlying.trace(_: String)).verify("event_id=eventId; key=value")
  }

  "the logger format" should "write the event id" in {
    val actualFormat = Logger.format("EVENT_ID", Vector())

    assert(actualFormat === "event_id=EVENT_ID")
  }

  it should "escape the event id as needed" in {
    val actualFormat = Logger.format("EVENT_ID\"", Vector())

    assert(actualFormat === """event_id="EVENT_ID\""""")
  }

  it should "escape null values" in {
    val actualFormat = Logger.format("EVENT_ID", Vector("a" -> null))

    assert(actualFormat === """event_id=EVENT_ID; a=<null>""")
  }

  it should "quote the null representation" in {
    val actualFormat = Logger.format("EVENT_ID", Vector("a" -> "<null>"))

    assert(actualFormat === """event_id=EVENT_ID; a="<null>"""")
  }

  it should "escape the escape char" in {
    val actualFormat = Logger.format("EVENT_ID", Vector("a" -> "\\"))

    assert(actualFormat === """event_id=EVENT_ID; a="\\"""")
  }

  it should "escape the separator char" in {
    val actualFormat = Logger.format("EVENT_ID", Vector("a" -> ";"))

    assert(actualFormat === """event_id=EVENT_ID; a="\;"""")
  }

  it should "quote strings with whitespace" in {
    val actualFormat = Logger.format("EVENT_ID", Vector("a" -> "a string"))

    assert(actualFormat === """event_id=EVENT_ID; a="a string"""")
  }

  private object TestObject
}
