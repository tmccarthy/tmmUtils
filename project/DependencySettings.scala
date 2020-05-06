import sbt.Keys.libraryDependencies
import sbt._
import sbt.librarymanagement.{CrossVersion, ModuleID}

object DependencySettings {

  private val catsVersion = "2.1.1"

  val commonDependencies: Seq[Def.Setting[Seq[ModuleID]]] = Seq(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    libraryDependencies += "com.github.ghik" %% "silencer-lib" % "1.4.1" % Provided,
    libraryDependencies += compilerPlugin("com.github.ghik" %% "silencer-plugin" % "1.4.1"),
  )

  val catsDependency = libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.1"
  val catsEffectDependency = libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.1"

  val catsTestKitDependency = Seq(
    libraryDependencies += "org.typelevel"         %% "cats-testkit"                 % catsVersion        % Test,
    libraryDependencies += "org.typelevel"         %% "cats-testkit-scalatest"       % "1.0.1"            % Test,
  )

  val fs2Dependency = libraryDependencies += {
    CrossVersion.partialVersion(Keys.scalaVersion.value) match {
      case Some((2, 13))     => "co.fs2" %% "fs2-core" % "1.1.0-M1"
      case Some((2, 12)) | _ => "co.fs2" %% "fs2-core" % "1.0.5"
    }
  }

  val circeDependency = libraryDependencies += {
    CrossVersion.partialVersion(Keys.scalaVersion.value) match {
      case Some((2, 13))     => "io.circe" %% "circe-core" % "0.12.0-RC1"
      case Some((2, 12)) | _ => "io.circe" %% "circe-core" % "0.11.1"
    }
  }

  val spireDependency = libraryDependencies += {
    CrossVersion.partialVersion(Keys.scalaVersion.value) match {
      case Some((2, 13))     => "org.typelevel" %% "spire" % "0.17.0-M1"
      case Some((2, 12)) | _ => "org.typelevel" %% "spire" % "0.16.2"
    }
  }

}
