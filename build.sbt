lazy val `logika-examples` = (project in file(".")).
  settings(
    scalaVersion := "2.12.1",
    libraryDependencies += "org.sireum" %% "logika-runtime" % "3.0.0-9",
    incOptions := incOptions.value.withNameHashing(true),
    retrieveManaged := true)
