lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file("."),
  settings = Seq(
    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "org.sireum" %% "logika-runtime" % "3.0.0-10",
      "com.lihaoyi" %% "ammonite-ops" % "0.8.1" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    ),
    incOptions := incOptions.value.withNameHashing(true),
    retrieveManaged := true
  )
)
