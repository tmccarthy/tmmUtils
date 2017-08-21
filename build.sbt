import sbt.Keys._

scalaVersion in ThisBuild := "2.12.2"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

publishTo in ThisBuild := Some("Artifactory Realm" at "http://artifactory.ambitious.tools/artifactory/sbt-libs-release-local")
credentials in ThisBuild += Credentials(file("ambitiousTools.credentials"))

crossScalaVersions in ThisBuild := Seq("2.12.2")

lazy val root = Project("tmmUtils", file("."))
  .settings(publishArtifact := false)
  .aggregate(utils, testUtils)

lazy val utils = (project in file("utils"))
  .dependsOn(testUtils % "test->compile")
  .settings(
    name := "tmmUtils",
    organization := "au.id.tmm",
    git.baseVersion := "0.1"
  )
  .enablePlugins(GitVersioning)
  .settings(
    libraryDependencies += "com.google.guava" % "guava" % "19.0",
    libraryDependencies += "com.jsuereth" %% "scala-arm" % "2.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )

lazy val testUtils = (project in file("testUtils"))
  .settings(
    name := "tmmTestUtils",
    organization := "au.id.tmm",
    git.baseVersion := "0.1"
  )
  .enablePlugins(GitVersioning)
  .settings(
    isSnapshot := false,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1",
    libraryDependencies += "commons-io" % "commons-io" % "2.4"
  )