import DependencySettings._

val settingsHelper = ProjectSettingsHelper("au.id.tmm","tmm-utils")()

settingsHelper.settingsForBuild

lazy val root = project
  .in(file("."))
  .settings(settingsHelper.settingsForRootProject)
  .settings(console := (console in Compile in collection).value)
  .aggregate(
    collection,
    testing,
  )

lazy val collection = project
  .in(file("collection"))
  .settings(settingsHelper.settingsForSubprojectCalled("collection"))

lazy val testing = project
  .in(file("testing"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest"  % "3.0.8",
    libraryDependencies += "commons-io"    %  "commons-io" % "2.6",
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
