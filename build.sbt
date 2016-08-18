scalaVersion := "2.11.8"

libraryDependencies += "org.sireum" %% "logika-runtime" % "3.0.0-7"

incOptions := incOptions.value.withNameHashing(true)

retrieveManaged := true
