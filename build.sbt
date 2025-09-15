lazy val hangman = project.in(file("."))
  .settings(
    scalaVersion                           := "3.7.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test
  )
