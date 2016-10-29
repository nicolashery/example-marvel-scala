name := "app"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Ywarn-unused-import",
  "-Ywarn-nullary-unit",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yinline-warnings",
  "-Ywarn-dead-code",
  "-Xfuture"
)
