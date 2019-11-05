import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.fauna"
ThisBuild / organizationName := "fauna-tool"

lazy val root = (project in file("."))
  .settings(
    name := "fauna-tool",
    libraryDependencies ++= Seq(scalaTest % Test, scalaReflect, json4sNative, scalaMeter, fansi, apacheCommonsIO, scOpt, scalaLogging, fastParse, slf4jApi, slf4jSimple)
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
