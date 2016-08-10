import sbt.Keys._

scalaVersion in ThisBuild := "2.11.8"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

coverageEnabled in ThisBuild := true

lazy val tmmUtilitiesRoot = (project in file("."))
  .aggregate(utils, testUtils)

lazy val utils = (project in file("utils"))
  .dependsOn(testUtils % "test->compile")
  .settings(
    name := "tmmUtils",
    organization := "au.id.tmm",
    version := "1.0-SNAPSHOT"
  )
  .settings(
    libraryDependencies += "com.google.guava" % "guava" % "19.0",
    libraryDependencies += "com.jsuereth" %% "scala-arm" % "1.4",
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
  )

lazy val testUtils = (project in file("testUtils"))
  .settings(
    name := "tmmTestUtils",
    organization := "au.id.tmm",
    version := "1.0-SNAPSHOT"
  )
  .settings(
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1"
  )