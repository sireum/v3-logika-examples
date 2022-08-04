lazy val logikaExamples = Project(
  id = "logika-examples",
  base = file(".")).
  settings(Seq(
    scalaVersion := "2.13.8",
    scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-nowarn",
      "-Ydelambdafy:method", "-feature", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies ++= Seq(
      "org.sireum.kekinian" %% "library" % "4.20220802.b3e076e",
      "com.lihaoyi" %% "ammonite-ops" % "2.4.1" % "test",
      "org.scalatest" %% "scalatest" % "3.2.3" % "test"
    ),
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % "always",
    addCompilerPlugin("org.sireum" %% "scalac-plugin" % "4.20220727.f62bed5"),
    Compile / unmanagedSourceDirectories += baseDirectory( _ / "src" / "propositional" ).value,
    resolvers ++= Seq(Resolver.sonatypeRepo("public"), "jitpack" at "https://jitpack.io"),
    retrieveManaged := true))
