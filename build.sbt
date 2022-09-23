ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

val http4sVersion = "0.23.16"
val awsSdkVersion = "2.17.279"
val catsEffectVersion = "3.3.14"
val circeVersion = "0.14.2"
val scalaTestVersion = "3.2.13"
val enumeratumVersion = "1.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "simple-git-lfs-4s",
    assembly / mainClass := Some("simple.git.lfs4s.Main"),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "software.amazon.awssdk" % "s3" % awsSdkVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "com.beachape" %% "enumeratum-circe" % enumeratumVersion,
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
