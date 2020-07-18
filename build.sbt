lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.12.12",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum.kekinian" %% "library" % "0b83a7bcaf",
      "com.lihaoyi" %% "ammonite-ops" % "2.1.4" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "4.20200713.0b7fce8"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers ++= Seq(Resolver.sonatypeRepo("public"), "jitpack" at "https://jitpack.io"),
    retrieveManaged := true))
