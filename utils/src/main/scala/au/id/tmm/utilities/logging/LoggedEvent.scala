package au.id.tmm.utilities.logging

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
  * A logged event, with a collection of key-value pairs containing information about the event.
  *
  * @param eventId   the event id
  * @param kvPairs   a set of key-value pairs containing information about the event
  * @param exception an exception associated with this event, if one exists
  */
final class LoggedEvent private (val eventId: String,
                                 val kvPairs: mutable.ArrayBuffer[(String, Any)] = mutable.ArrayBuffer[(String, Any)](),
                                 var exception: Option[Throwable] = None,
                                ) {

  def markSuccessful(): Unit = kvPairs.+=:("successful" -> true)

  def markFailed(): Unit = kvPairs.+=:("successful" -> false)

  /**
    * Logs this event once the given block is complete. The amount of time taken to execute the block is added to the
    * key-value pairs for the event.
    *
    * If the block completes via an exception, the event is marked as failed and is logged at the `ERROR` level.
    * Otherwise it is logged at the `INFO` level.
    */
  def logWithTimeOnceFinished[A](block: => A)(implicit logger: Logger): A = {
    this.logOnceFinished {
      val start = System.currentTimeMillis()
      val result = block
      val duration = System.currentTimeMillis() - start

      kvPairs.+=:("duration" -> duration)

      result
    }
  }

  /**
    * Logs this event once the given block is complete.
    *
    * If the block completes via an exception, the event is marked as failed and is logged at the `ERROR` level.
    * Otherwise it is logged at the `INFO` level.
    */
  def logOnceFinished[A](block: => A)(implicit logger: Logger): A = {
    try {
      val result = block

      markSuccessful()
      logger.info(this)

      result
    } catch {
      case e: Throwable => {
        this.exception = Some(e)
        markFailed()
        logger.error(this)

        throw e
      }
    }
  }
}

object LoggedEvent {
  def apply(eventId: String) = new LoggedEvent(eventId)

  def apply(eventId: String, kvPairs: (String, Any)*) = new LoggedEvent(eventId, kvPairs = ArrayBuffer(kvPairs :_*))

  implicit class TryOps[A](aTry: Try[A]) {

    /**
      * Log an event with the given id and key-value pairs, based on this Try. If this Try is a `Success`, the event is
      * marked as successful and logged at the `INFO` level. If it is a `Failure`, it is marked as failed, associated
      * with the thrown exception, and logged at the `ERROR` level.
      */
    def logEvent(eventId: String, kvPairs: (String, Any)*)(implicit logger: Logger): Try[A] = {
      val loggedEvent = LoggedEvent(eventId, kvPairs: _*)

      aTry match {
        case Success(_) => {
          loggedEvent.markSuccessful()
          logger.info(loggedEvent)
        }
        case Failure(e) => {
          loggedEvent.markFailed()
          loggedEvent.exception = Some(e)
          logger.error(loggedEvent)
        }
      }

      aTry
    }
  }

  implicit class FutureOps[A](future: Future[A]) {

    /**
      * Log an event with the given id and key-value pairs, based on this Future. If the Future completes successfully,
      * the event is marked as successful and logged at the `INFO` level. If the Future fails, the logged event is
      * marked as failed, associated with the thrown exception, and logged at the `ERROR` level.
      */
    def logEvent(eventId: String, kvPairs: (String, Any)*)(implicit logger: Logger, ec: ExecutionContext): Future[A] = {
      future.andThen {
        case t: Try[A] => t.logEvent(eventId, kvPairs: _*)
      }
    }
  }
}