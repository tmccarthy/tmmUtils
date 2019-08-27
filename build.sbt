

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
    codec,
    testing,
  )

lazy val collection = project
  .in(file("collection"))
  .settings(settingsHelper.settingsForSubprojectCalled("collection"))

lazy val codec = project
  .in(file("codec"))
  .settings(settingsHelper.settingsForSubprojectCalled("codec"))
  .settings(
    libraryDependencies += "commons-codec" % "commons-codec" % "1.13",
  )
  .dependsOn(testing % "test->compile")

lazy val testing = project
  .in(file("testing"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest"  % "3.0.8",
    libraryDependencies += "commons-io"    %  "commons-io" % "2.6",
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
