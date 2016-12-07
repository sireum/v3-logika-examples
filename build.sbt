scalaVersion := "2.12.1"

libraryDependencies += "org.sireum" %% "logika-runtime" % "3.0.0-8"

incOptions := incOptions.value.withNameHashing(true)

retrieveManaged := true
