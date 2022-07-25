name := "tmm-utils"

ThisBuild / tlBaseVersion := "0.9"

Sonatype.SonatypeKeys.sonatypeProfileName := "au.id.tmm"
ThisBuild / organization := "au.id.tmm.tmm-utils"
ThisBuild / organizationName := "Timothy McCarthy"
ThisBuild / startYear := Some(2016)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  tlGitHubDev("tmccarthy", "Timothy McCarthy"),
)

val Scala213 = "2.13.8"
ThisBuild / scalaVersion := Scala213
ThisBuild / crossScalaVersions := Seq(
  Scala213,
  "3.1.1",
)

ThisBuild / githubWorkflowJavaVersions := List(
  JavaSpec.temurin("11"),
  JavaSpec.temurin("8"),
  JavaSpec.temurin("17"),
)

ThisBuild / tlCiHeaderCheck := false
ThisBuild / tlCiScalafmtCheck := true
ThisBuild / tlCiMimaBinaryIssueCheck := false
ThisBuild / tlFatalWarnings := true

lazy val root = tlCrossRootProject
  .aggregate(
    errors,
    syntax,
    cats,
    catsEffect,
    circe,
    testingCore,
    testingScalacheck,
    testingCats,
  )

val catsVersion            = "2.6.1"
val catsEffectVersion      = "3.2.1"
val circeVersion           = "0.14.1"
val scalacheckVersion      = "1.15.4"
val scalatestVersion       = "3.2.9"
val disciplineMunitVersion = "1.0.9"
val mUnitVersion           = "0.7.27"

lazy val errors = project
  .in(file("errors"))
  .settings(name := "tmm-utils-errors")
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit" % mUnitVersion % Test,
  )
  .dependsOn(testingCore % "test->compile")

lazy val syntax = project
  .in(file("syntax"))
  .settings(name := "tmm-utils-syntax")
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit" % mUnitVersion % Test,
  )

lazy val cats = project
  .in(file("cats/cats-core"))
  .settings(name := "tmm-utils-cats")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit"            % mUnitVersion           % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit"     % catsVersion            % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % disciplineMunitVersion % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val catsEffect = project
  .in(file("cats/cats-effect"))
  .settings(name := "tmm-utils-cats-effect")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-effect" % catsEffectVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit"               % mUnitVersion           % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit"        % catsVersion            % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit"    % disciplineMunitVersion % Test,
    libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.5"                % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val circe = project
  .in(file("circe"))
  .settings(name := "tmm-utils-circe")
  .settings(
    libraryDependencies += "io.circe" %% "circe-core" % circeVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit"            % mUnitVersion           % Test,
    libraryDependencies += "io.circe"      %% "circe-testing"    % circeVersion           % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % disciplineMunitVersion % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val testingCore = project
  .in(file("testing/core"))
  .settings(name := "tmm-utils-testing-core")
  .settings(
    libraryDependencies += "commons-io" % "commons-io" % "2.6",
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta"  %% "munit"            % mUnitVersion      % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck"       % scalacheckVersion % Test,
    libraryDependencies += "org.scalameta"  %% "munit-scalacheck" % mUnitVersion      % Test,
  )

lazy val testingScalacheck = project
  .in(file("testing/scalacheck"))
  .dependsOn(testingCore)
  .settings(name := "tmm-utils-testing-scalacheck")
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion,
  )

lazy val testingCats = project
  .in(file("testing/cats"))
  .dependsOn(testingCore, testingScalacheck % "test->compile")
  .settings(name := "tmm-utils-testing-cats")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-laws" % catsVersion,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit"            % mUnitVersion           % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit"     % catsVersion            % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % disciplineMunitVersion % Test,
  )

addCommandAlias("check", ";githubWorkflowCheck;scalafmtSbtCheck;+scalafmtCheckAll;+test")
addCommandAlias("fix", ";githubWorkflowGenerate;+scalafmtSbt;+scalafmtAll")
