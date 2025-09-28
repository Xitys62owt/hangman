import coursierapi.MavenRepository

ThisBuild / organization     := "t-academy"
ThisBuild / organizationName := "T-Bank"
ThisBuild / version          := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion                                  := "3.3.1"
ThisBuild / scalafixDependencies += "org.typelevel"       %% "typelevel-scalafix" % "0.5.0"
ThisBuild / scalafixDependencies += "com.github.vovapolu" %% "scaluzzi"           % "0.1.23"
ThisBuild / semanticdbEnabled                             := true

lazy val root = project.in(file("."))
  .settings(
    name         := "hw1-hangman",
    scalaVersion := "3.3.1",
    libraryDependencies ++= List(
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
