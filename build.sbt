lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.13.6",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum.kekinian" %% "library" % "4.20210816.dd5f584",
      "com.lihaoyi" %% "ammonite-ops" % "2.4.0" % "test",
      "org.scalatest" %% "scalatest" % "3.2.3" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "4.20210812.d884dc2"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers ++= Seq(Resolver.sonatypeRepo("public"), "jitpack" at "https://jitpack.io"),
    retrieveManaged := true))
