package au.id.tmm.utilities.testing.scalatest

import org.scalatest.Suite

/**
  * Allows for tests to be ignored (by name) once they have been registered. Normally you would want to use the
  * `@Ignore ` annotation or `ignore`, but sometimes (eg with laws testing) ignoring this way is simpler.
  */
trait AdHocTestIgnore extends Suite {

  protected def ignoredTestNames: Set[String]

  override def tags: Map[String, Set[String]] =
    ignoredTestNames.foldLeft(super.tags) { case (tags, ignoredTestName) =>
      val oldTags: Set[String] = tags.getOrElse(ignoredTestName, Set.empty)

      val newTags = oldTags + "org.scalatest.Ignore"

      tags.updated(ignoredTestName, newTags)
    }

}
