lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.13.8",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum.kekinian" %% "library" % "83cbd934f8",
      "com.lihaoyi" %% "ammonite-ops" % "2.4.1" % "test",
      "org.scalatest" %% "scalatest" % "3.2.3" % "test"
    ),
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "4.20220803.e556f3e"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers ++= Resolver.sonatypeOssRepos("public") :+ ("jitpack" at "https://jitpack.io"),
    retrieveManaged := true))
