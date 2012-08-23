resolvers ++= Seq(
  Classpaths.typesafeSnapshots,
  "sbt-lab24-repo" at "http://lab-24.github.com/maven/"
)

libraryDependencies <+= sbtVersion("org.scala-sbt" %% "scripted-plugin" % _)
