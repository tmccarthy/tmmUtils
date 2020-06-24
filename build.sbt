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
    codec,
    syntax,
    valueClasses,
    cats,
    circe,
    testing,
  )

val catsVersion  = "2.2.0-M1"
val circeVersion = "0.14.0-M1"

lazy val errors = project
  .in(file("errors"))
  .settings(settingsHelper.settingsForSubprojectCalled("errors"))
  .dependsOn(testing % "test->compile")

lazy val codec = project
  .in(file("codec"))
  .settings(settingsHelper.settingsForSubprojectCalled("codec"))
  .settings(
    libraryDependencies += "commons-codec" % "commons-codec" % "1.13",
  )
  .dependsOn(testing % "test->compile")

lazy val syntax = project
  .in(file("syntax"))
  .settings(settingsHelper.settingsForSubprojectCalled("syntax"))
  .dependsOn(testing % "test->compile")

lazy val valueClasses = project
  .in(file("value-classes"))
  .settings(settingsHelper.settingsForSubprojectCalled("value-classes"))
  .dependsOn(testing % "test->compile")

lazy val cats = project
  .in(file("cats"))
  .settings(settingsHelper.settingsForSubprojectCalled("cats"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core"              % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-testkit"           % catsVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1" % Test,
  )
  .dependsOn(testing % "test->compile")

lazy val circe = project
  .in(file("circe"))
  .settings(settingsHelper.settingsForSubprojectCalled("circe"))
  .settings(
    libraryDependencies += "io.circe"      %% "circe-core"             % circeVersion,
    libraryDependencies += "io.circe"      %% "circe-testing"          % circeVersion % Test,
    libraryDependencies += "org.typelevel" %% "cats-testkit-scalatest" % "1.0.1" % Test,
  )
  .dependsOn(testing % "test->compile")

lazy val testing = project
  .in(file("testing"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8",
    libraryDependencies += "commons-io"    % "commons-io" % "2.6",
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
