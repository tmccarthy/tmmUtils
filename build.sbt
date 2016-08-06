import sbt.Keys._

name := "tmmUtils"

version := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

coverageEnabled in ThisBuild := true

lazy val root = (project in file("."))
  .aggregate(utils, testUtils)

lazy val utils = (project in file("utils"))
  .dependsOn(testUtils % "test->compile")
  .settings(
    libraryDependencies += "com.google.guava" % "guava" % "19.0",
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
  )

lazy val testUtils = (project in file("testUtils"))
  .settings(
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1"
  )