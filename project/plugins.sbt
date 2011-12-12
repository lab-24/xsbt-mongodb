resolvers ++= Seq(
  Classpaths.typesafeSnapshots,
  "sbt-lab24-repo" at "http://lab24.github.com/maven/"
)

libraryDependencies <+= sbtVersion("org.scala-tools.sbt" %% "scripted-plugin" % _)
