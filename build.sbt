val sireumRuntimeVersion = "3.1"

lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file("."),
  settings = Seq(
    scalaVersion := "2.12.3",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    libraryDependencies ++= Seq(
      "org.sireum" %% "runtime" % sireumRuntimeVersion,
      "org.sireum" %% "prelude" % sireumRuntimeVersion,
      "com.lihaoyi" %% "ammonite-ops" % "1.0.0" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    ),
    incOptions := incOptions.value.withNameHashing(true),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "3.0.0-14"),
    unmanagedSourceDirectories in Compile += (baseDirectory( _ / "src" / "propositional" )).value,
    retrieveManaged := true
  )
)
