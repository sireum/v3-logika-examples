val sireumRuntimeVersion = "3.1.4"

lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.12.4",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum" %% "macros" % sireumRuntimeVersion,
      "org.sireum" %% "library" % sireumRuntimeVersion,
      "com.lihaoyi" %% "ammonite-ops" % "1.0.3" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "3.1.2"),
    unmanagedSourceDirectories in Compile += baseDirectory(_ / "src" / "propositional").value,
    resolvers += Resolver.sonatypeRepo("public"),
    retrieveManaged := true
  ))
