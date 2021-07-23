ThisBuild / sonatypeProfile := "au.id.tmm"
ThisBuild / baseProjectName := "tmm-utils"
ThisBuild / githubProjectName := "tmmUtils"

lazy val root = project
  .in(file("."))
  .settings(settingsForRootProject)
  .settings(console := (syntax / Compile / console).value)
  .aggregate(
    errors,
    syntax,
    cats,
    circe,
    testingCore,
    testingScalatest,
    testingScalacheck,
    testingCats,
  )

val catsVersion       = "2.4.2"
val circeVersion      = "0.14.0-M1"
val scalacheckVersion = "1.15.2"
val scalatestVersion  = "3.2.0-M4"

lazy val errors = project
  .in(file("errors"))
  .settings(settingsForSubprojectCalled("errors"))
  .dependsOn(testingCore % "test->compile")

lazy val syntax = project
  .in(file("syntax"))
  .settings(settingsForSubprojectCalled("syntax"))

lazy val cats = project
  .in(file("cats"))
  .settings(settingsForSubprojectCalled("cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"        % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"     % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % "1.0.6"     % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val circe = project
  .in(file("circe"))
  .settings(settingsForSubprojectCalled("circe"))
  .settings(
    libraryDependencies += "io.circe"      %% "circe-core"       % circeVersion,
    libraryDependencies += "io.circe"      %% "circe-testing"    % circeVersion % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % "1.0.6"      % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val testingCore = project
  .in(file("testing/core"))
  .settings(settingsForSubprojectCalled("testing-core"))
  .settings(
    libraryDependencies += "commons-io"      % "commons-io"       % "2.6",
    libraryDependencies += "org.scalacheck" %% "scalacheck"       % scalacheckVersion                             % Test,
    libraryDependencies += "org.scalameta"  %% "munit-scalacheck" % au.id.tmm.sbt.DependencySettings.mUnitVersion % Test,
  )

lazy val testingScalatest = project
  .in(file("testing/scalatest"))
  .settings(settingsForSubprojectCalled("testing-scalatest"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion,
  )

lazy val testingScalacheck = project
  .in(file("testing/scalacheck"))
  .dependsOn(testingCore)
  .settings(settingsForSubprojectCalled("testing-scalacheck"))
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion,
  )

lazy val testingCats = project
  .in(file("testing/cats"))
  .dependsOn(testingCore, testingScalacheck % "test->compile")
  .settings(settingsForSubprojectCalled("testing-cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"        % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-laws"        % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"     % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "discipline-munit" % "1.0.6"     % Test,
  )
