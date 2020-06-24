package au.id.tmm.utilities.testing

import org.scalatest.FlatSpecLike

trait JreVersionDependentFlatSpec { this: FlatSpecLike =>

  def ignoreJavaVersions(ignoredFilter: String => Boolean)(testName: String)(testFun: => Any): Unit = {
    val isIgnoredJavaVersion = ignoredFilter(System.getProperty("java.specification.version"))

    if (isIgnoredJavaVersion) it should testName ignore testFun else it should testName in testFun
  }

  def ignoreJava8(testName: String)(testFun: => Any): Unit = ignoreJavaVersions(_ == "1.8")(testName)(testFun)

}
