ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

val awsSdkVersion = "2.17.279"
val catsEffectVersion = "3.3.14"
val circeVersion = "0.14.2"
val scalaTestVersion = "3.2.13"

lazy val root = (project in file("."))
  .settings(
    name := "simple-git-lfs-4s",
    assembly / mainClass := Some("simple.git.lfs4s.Main"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "software.amazon.awssdk" % "s3" % awsSdkVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
      "org.scalatest" %% "scalatest-funsuite" % scalaTestVersion % "test"
    ),
    ThisBuild / assemblyMergeStrategy := {
      case "META-INF/io.netty.versions.properties" => MergeStrategy.first
      case x                                       =>
        val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
        oldStrategy(x)
    }
  )
