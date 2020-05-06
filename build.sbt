

val settingsHelper = ProjectSettingsHelper("au.id.tmm","tmm-utils")(
  githubProjectName = "tmmUtils",
)

settingsHelper.settingsForBuild

lazy val root = project
  .in(file("."))
  .settings(settingsHelper.settingsForRootProject)
  .settings(console := (console in Compile in collection).value)
  .aggregate(
    collection,
    errors,
    codec,
    syntax,
    valueClasses,
    testing,
  )

lazy val collection = project
  .in(file("collection"))
  .settings(settingsHelper.settingsForSubprojectCalled("collection"))
  .dependsOn(testing % "test->compile")

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

// TODO remove this if there's nothing in it
lazy val cats = project
  .in(file("cats"))
  .settings(settingsHelper.settingsForSubprojectCalled("cats"))
  .settings(DependencySettings.catsDependency)
  .settings(DependencySettings.catsTestKitDependency)
  .dependsOn(testing % "test->compile")

lazy val testing = project
  .in(file("testing"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest"  % "3.0.8",
    libraryDependencies += "commons-io"    %  "commons-io" % "2.6",
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
