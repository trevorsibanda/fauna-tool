import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.fauna"
ThisBuild / organizationName := "fauna-tool"


lazy val ast = (project in file("ast")).settings(
  libraryDependencies ++= Seq("org.scalatest" %%% "scalatest" % "3.0.8" % "test")
).enablePlugins(ScalaJSPlugin)

lazy val common = (project in file("common")).settings(
    name := "common",
    libraryDependencies ++= Seq("org.scalatest" %%% "scalatest" % "3.0.8" % "test", json4sNative)
    ).dependsOn(ast).enablePlugins(ScalaJSPlugin)

lazy val codegen = (project in file("codegen"))
  .settings(
    name := "codegen"
    ).dependsOn(
    ast
  ).enablePlugins(ScalaJSPlugin)

lazy val sharedParsers = (project in file("parser/shared"))
  .settings(
    name := "parser/shared",
    libraryDependencies ++= Seq("com.lihaoyi" %%% "fastparse" % "2.1.3")
    ).dependsOn(
    ast
  ).enablePlugins(ScalaJSPlugin)

lazy val jvmParsers = (project in file("parser/jvm"))
  .settings(
    name := "parser/jvm",
    libraryDependencies ++= Seq(json4sNative)
    ).dependsOn(
    ast
  )

lazy val jsParsers = (project in file("parser/js")).enablePlugins(ScalaJSPlugin)
  .settings(
    name := "parser/js",
    libraryDependencies ++= Seq("com.lihaoyi" %%% "ujson" % "0.9.5")
    ).dependsOn(
    ast
  )

lazy val jsLib  = (project in file("js-lib")).enablePlugins(ScalaJSPlugin).settings(
    name := "js-lib",
    scalaJSUseMainModuleInitializer := false,
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    ).dependsOn(
    ast, jsParsers, sharedParsers, codegen
  )

lazy val tool = (project in file("tool")).settings(
    name := "tool",
    libraryDependencies ++= Seq(scalaTest % Test, json4sNative, fansi, apacheCommonsIO, scOpt, scalaLogging, fastParse, slf4jApi, slf4jSimple),
    addCompilerPlugin(scalafixSemanticdb), // enable SemanticDB
    assemblyMergeStrategy in assembly := {
      case PathList("JS_DEPENDENCIES", xs @ _*) => MergeStrategy.discard
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    scalacOptions ++= List(
    "-Yrangepos",          // required by SemanticDB compiler plugin
    "-Ywarn-unused" // required by `RemoveUnused` rule
   )
  ).dependsOn(ast, common, codegen, sharedParsers, jvmParsers)

lazy val allProjects = Seq(ast, codegen, common, sharedParsers, jvmParsers, jsParsers, jsLib, tool) map { p => LocalProject(p.id) }

lazy val root = (project in file("."))
  .aggregate(allProjects: _*)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
