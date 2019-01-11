lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum" %% "library" % "3.3.1",
      "com.lihaoyi" %% "ammonite-ops" % "1.6.0" % "test",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "3.3.4"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers += Resolver.sonatypeRepo("public"),
    retrieveManaged := true))
