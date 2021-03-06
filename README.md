MongoDB Tools plugin for sbt 0.11+
====================================

# Instructions for use:
### Step 1: Include the plugin in your build

Add the following to your `project/plugins.sbt`:

## sbt-0.11.0

	resolvers += "sbt-lab24-repo" at "http://lab-24.github.com/maven/"
	  
	addSbtPlugin("com.github.lab24" % "xsbt-mongodb" % "0.11.2-SNAPSHOT")

### Step 2: Add sbt-liquibase settings to your build

Add the following to your 'build.sbt' ( if you are using build.sbt )


	import com.github.lab24.sbtmongodb.MongodbPlugin._	
	
	seq(mongodbSettings: _*)
	
	mongoHost 		:= "127.0.0.1"
	
	mongoPort 		:= 27017
	
	mongoDb 		:= "test"
	
	mongoFixturesPath 	:= "fixtures/"


Or if you are using a build object extending from Build:

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
	    mongoDb := "test",
	    mongoFixturesPath := "fixtures/"
	  ) )
	}

## Settings

<table>
        <tr>
                <td> <b>mongoHost</b> </td>
                <td>Hostname of the MongoDB Server</td>
        </tr>
        <tr><td></td><td>

            mongoHost := "127.0.0.1"

        </td></tr>
        <tr>
                <td> <b>mongoPort</b> </td>
                <td>Port of the MongoDB Server</td>
        </tr>
        <tr><td></td><td>

            mongoPort := 27017

        </td></tr>
        <tr>
                <td> <b>mongoDb</b> </td>
                <td>Database name</td>
        </tr>
        <tr><td></td><td>

            mongoDb := "test"

        </td></tr>
        <tr>
                <td> <b>mongoFixturesPath</b> </td>
                <td>Path to the fixtures directory</td>
        </tr>
        <tr><td></td><td>

            mongoFixturesPath := "fixtures/"

        </td></tr>
</table>

## Tasks

<table>
        <tr>
                <td> <b>mongodb:drop</b> </td>
                <td>Drop the database</td>
        </tr>
        <tr>
                <td><b>mongodb:fixtures-load</b></td>
                <td>Load fixtures files into database</td>
        </tr>

</table>


Fixtures Files
--------------
The Plugin uses the filename as the collection name. Please use proper JSON Format in the fixture files


	[
	  {
	  	"_id": {$oid: "4ee5cfe75e927bb2c598f525"}, 
		"title": "Service",
		"links": [
			{"name": "Service 1", "link": "#" },
			{"name": "Service 2", "link": "#" }
		]
	  },
	  {
	    	"_id": {$oid: "4ee5d0295e927bb2c598f526"}, 
		"title": "Support",
		"links": [
			{"name": "Support 1", "link": "#" },
			{"name": "Support 2", "link": "#" }
		]
	  }
	]


Warnings and Notes
------------------
This plugin is still at the alpha stage of development. There is not much error handling. This is my first sbt plugin and I was focusing on functionality.

If any bugs are found or features wanted please file an issue in the github project. I will do my best to accommodate.


Acknoledgements
---------------
I used the following plugins as reference

 * sbt-liquibase plugin for sbt 0.10
 * sbteclipse plugin for sbt 0.10



