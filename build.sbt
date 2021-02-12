val settingsHelper = ProjectSettingsHelper("au.id.tmm", "tmm-utils")(
  githubProjectName = "tmmUtils",
)

settingsHelper.settingsForBuild

lazy val root = project
  .in(file("."))
  .settings(settingsHelper.settingsForRootProject)
  .settings(console := (console in Compile in syntax).value)
  .aggregate(
    errors,
    syntax,
    cats,
    circe,
    testingCore,
    testingScalacheck,
    testingCats,
  )

val catsVersion  = "2.2.0-M1"
val circeVersion = "0.14.0-M1"
val scalacheckVersion = "1.15.2"

lazy val errors = project
  .in(file("errors"))
  .settings(settingsHelper.settingsForSubprojectCalled("errors"))
  .dependsOn(testingCore % "test->compile")

lazy val syntax = project
  .in(file("syntax"))
  .settings(settingsHelper.settingsForSubprojectCalled("syntax"))
  .dependsOn(testingCore % "test->compile")

lazy val cats = project
  .in(file("cats"))
  .settings(settingsHelper.settingsForSubprojectCalled("cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"              % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"           % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1"     % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val circe = project
  .in(file("circe"))
  .settings(settingsHelper.settingsForSubprojectCalled("circe"))
  .settings(
    libraryDependencies += "io.circe"      %% "circe-core"             % circeVersion,
    libraryDependencies += "io.circe"      %% "circe-testing"          % circeVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1"      % Test,
  )
  .dependsOn(testingCats % "test->compile", testingScalacheck % "test->compile")

lazy val testingCore = project
  .in(file("testing/core"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing-core"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest"  % DependencySettings.scalatestVersion,
    libraryDependencies += "commons-io"     % "commons-io" % "2.6",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test,
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % Test
  )

lazy val testingScalacheck = project
  .in(file("testing/scalacheck"))
  .dependsOn(testingCore)
  .settings(settingsHelper.settingsForSubprojectCalled("testing-scalacheck"))
  .settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion,
  )

lazy val testingCats = project
  .in(file("testing/cats"))
  .dependsOn(testingCore, testingScalacheck % "test->compile")
  .settings(settingsHelper.settingsForSubprojectCalled("testing-cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"              % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-laws"              % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"           % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1"     % Test,
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
