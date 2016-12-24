lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file("."),
  settings = Seq(
    scalaVersion := "2.12.1",
    libraryDependencies += "org.sireum" %% "logika-runtime" % "3.0.0-9",
    incOptions := incOptions.value.withNameHashing(true),
    retrieveManaged := true
  )
)
