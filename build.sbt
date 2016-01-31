scalaVersion := "2.11.7"

libraryDependencies += "org.sireum" %% "logika-runtime" % "3.0.0-3"

incOptions := incOptions.value.withNameHashing(true)

retrieveManaged := true
