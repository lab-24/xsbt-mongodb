package com.github.lab24.sbtmongodb

import sbt._
import Keys._
import Process._
import Project.Initialize


object MongodbPlugin extends Plugin {
	
  val Mongodb = config("mongodb") extend (Runtime)
  val Test = config("test") extend (Runtime)
  val drop = TaskKey[Unit]("drop", "Drop Mongodb MAIN Database")
  val testDrop = TaskKey[Unit]("test-drop", "Drop Mongodb TEST Database")
  val fixturesLoad = TaskKey[Unit]("fixtures-load", "Load Fixtures Data into MAIN Database")
  val testFixturesLoad = TaskKey[Unit]("test-fixtures-load", "Load Fixtures Data into TEST Database")
  
  lazy val mongodbSettings:  Seq[Setting[_]] = 
    inConfig(Mongodb)(Seq(
    		drop <<= dropTask,
    		fixturesLoad <<= fixturesLoadTask,
    		testDrop <<= testDropTask,
    		testFixturesLoad <<= testFixturesLoadTask
      )       
    )
  
 private def dropTask: Initialize[Task[Unit]] =
    (state) map { (s) =>
		  val log = s.log
		  log.info("drop main")
		  s
    }

 private def testDropTask: Initialize[Task[Unit]] =
    (state) map { (s) =>
		  val log = s.log
		  log.info("drop test")
		  s
    }
	
 private def fixturesLoadTask: Initialize[Task[Unit]] =
    (state) map { (s) =>
		  val log = s.log
		  log.info("fixtures load")
		  s
    }
	
 private def testFixturesLoadTask: Initialize[Task[Unit]] =
    (state) map { (s) =>
		  val log = s.log
		  log.info("fixtures load test")
		  s
    }

}



