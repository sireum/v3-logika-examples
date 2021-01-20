lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.13.3",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum.kekinian" %% "library" % "4.20210120.9bbeb24",
      "com.lihaoyi" %% "ammonite-ops" % "2.3.8" % "test",
      "org.scalatest" %% "scalatest" % "3.2.3" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "4.20210120.3a0e50b"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers ++= Seq(Resolver.sonatypeRepo("public"), "jitpack" at "https://jitpack.io"),
    retrieveManaged := true))
