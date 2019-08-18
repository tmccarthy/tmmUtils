import DependencySettings._

val settingsHelper = ProjectSettingsHelper("au.id.tmm","tmm-utils")()

settingsHelper.settingsForBuild

lazy val root = project
  .in(file("."))
  .settings(settingsHelper.settingsForRootProject)
  .settings(console := (console in Compile in collection).value)
  .aggregate(
    collection,
    hashing,
    encoding,
    testing,
  )

lazy val collection = project
  .in(file("collection"))
  .settings(settingsHelper.settingsForSubprojectCalled("collection"))

lazy val hashing = project
  .in(file("hashing"))
  .settings(settingsHelper.settingsForSubprojectCalled("hashing"))

lazy val encoding = project
  .in(file("encoding"))
  .settings(settingsHelper.settingsForSubprojectCalled("encoding"))

lazy val testing = project
  .in(file("testing"))
  .settings(settingsHelper.settingsForSubprojectCalled("testing"))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8",
  )

addCommandAlias("check", ";+test;scalafmtCheckAll")
