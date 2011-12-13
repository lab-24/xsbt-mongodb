import sbt._
import Keys._

object SbtMongoDbBuild extends Build {
lazy val xsbtMongoDb = Project("xsbt-mongodb", file("."), settings = mainSettings)

lazy val mainSettings: Seq[Project.Setting[_]] = Defaults.defaultSettings ++ Seq(
	sbtPlugin := true,
	organization := "com.github.lab24",
	name := "xsbt-mongodb",
	version := "0.11.2-SNAPSHOT",
	publishTo := Some(Resolver.file("Github Pages", Path.userHome / "git" / "lab-24.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern))),
	publishMavenStyle := true,
	resolvers += Classpaths.typesafeSnapshots,
	scalacOptions ++= Seq("-deprecation", "-unchecked"),
	libraryDependencies ++= helperDependencies
        
)

  private def helperDependencies = Seq(
    "commons-io" % "commons-io" % "2.0.1",
    "org.mongodb" % "mongo-java-driver" % "2.7.2"
  )

}


