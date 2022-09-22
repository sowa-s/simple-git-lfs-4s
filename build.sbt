ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

val http4sVersion = "0.23.16"
val circeVersion = "0.14.2"

lazy val root = (project in file("."))
  .settings(
    name := "simple-git-lfs-4s",
    assembly / mainClass := Some("simple.git.lfs4s.Main"),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % http4sVersion,
      "org.http4s"      %% "http4s-circe"        % http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % http4sVersion,
      "io.circe"        %% "circe-generic"       % circeVersion,
    )
  )
