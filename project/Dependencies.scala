import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.13.0"
  lazy val json4sNative = "org.json4s" %% "json4s-native" % "3.6.7"
  lazy val scalaMeter = "com.storm-enroute" %% "scalameter" % "0.19"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  lazy val fansi = "com.lihaoyi" %% "fansi" % "0.2.7"
  lazy val scOpt = "com.github.scopt" %% "scopt" % "4.0.0-RC2"
  lazy val apacheCommonsIO = "commons-io" % "commons-io" % "2.5"
  lazy val fastParse = "com.lihaoyi" %% "fastparse" % "2.1.3"
  lazy val slf4jApi =  "org.slf4j" % "slf4j-api" % "1.7.5"
  lazy val slf4jSimple = "org.slf4j" % "slf4j-simple" % "1.6.4"
}
