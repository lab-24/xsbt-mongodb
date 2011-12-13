package com.github.lab24.sbtmongodb

import sbt._
import Keys._
import Process._
import Project.Initialize
import com.mongodb.ServerAddress
import com.mongodb.Mongo
import com.mongodb.util.JSON
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import java.io.File
import scala.io.Source
import java.io.InputStream
import java.io.FileInputStream

object MongodbPlugin extends Plugin {

  val Mongodb = config("mongodb") extend (Runtime)
  val Test = config("test") extend (Runtime)
  val drop = TaskKey[Unit]("drop", "Drop Mongodb Database")
  val fixturesLoad = TaskKey[Unit]("fixtures-load", "Load Fixtures Data into Database")
  val mongoHost = SettingKey[String]("mongo-host")
  val mongoPort = SettingKey[Int]("mongo-port")
  val mongoDb = SettingKey[String]("mongo-db");
  val mongoUser = SettingKey[String]("mongo-db-user");
  val mongoPassword = SettingKey[String]("mongo-db-password");
  val mongoFixturesPath = SettingKey[String]("mongo-fixtures-path");

  lazy val mongodbSettings: Seq[Setting[_]] =
    inConfig(Mongodb)(Seq(
      drop <<= dropTask,
      fixturesLoad <<= fixturesLoadTask))

  private def dropTask: Initialize[Task[Unit]] =
    (state, mongoHost, mongoPort, mongoDb) map { (s, mHost, mPort, mDb) =>

      val log = s.log
      
      // connect to DB
      val mongo = new Mongo(mHost, mPort)
      val db = mongo.getDB(mDb)

      log.info("drop Database " + mDb )
      // drop the DB
      db.dropDatabase();
      s
    }

  private def fixturesLoadTask: Initialize[Task[Unit]] =
    (state, mongoHost, mongoPort, mongoDb, mongoFixturesPath) map { (s, mHost, mPort, mDb, mFixturesPath) =>
      val log = s.log

      // connect to DB
      val mongo = new Mongo(mHost, mPort)
      val db = mongo.getDB(mDb)
      
        
      for (collection <- recursiveListFiles(new File(mFixturesPath))) {
	      var docCounter = 0
	      var openCounter = 0
	      val buff = new StringBuffer()

	      val coll = db.getCollection(collection.getName())
          val fis = new FileInputStream(collection);
	      var c: Char = 0
	      while (fis.available() > 0) {
	        c = fis.read().asInstanceOf[Char]

	        
	        if (c == '{') {
	        	buff.append(c)
	        	openCounter = openCounter + 1
	        }
	        else if (c == '}') {
	        	buff.append(c)
	        	openCounter = openCounter - 1
		        if (openCounter == 0) {
					val data:DBObject = JSON.parse(buff.toString()) match {
					  case x:DBObject => x
					}
		      		coll.insert(data)
		      		docCounter = docCounter + 1
		        }
	        } else {
		        // do not add chunk to the buffer, if we are not in a document -> removes also the [ ]
		        if (openCounter != 0)
		        	buff.append(c)
	          
	        }	          
	      }
          log.info(docCounter + " documents inserted into collection " + collection.getName())

      }
      s
    }
  
  private def recursiveListFiles(f: File): Array[File] = {
	val these = f.listFiles
	these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }


}

