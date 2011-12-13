import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "com.github.lab24"
  val buildScalaVersion = "2.9.1"
  val buildVersion      = "0.1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion
  )
}

object MyBuild extends Build {
  import BuildSettings._
  import com.github.lab24.sbtmongodb.MongodbPlugin._

  lazy val myProject = Project("mongo", file("."), settings = buildSettings ++ mongodbSettings ++ Seq (
    mongoHost := "127.0.0.1",
    mongoPort := 27017,
    mongoDb := "sbttest",
    mongoFixturesPath := "fixtures/"
  ) )
}
