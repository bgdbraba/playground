import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "playground"
  val appVersion      = "1.0-SNAPSHOT"
  val apache_poi      = "org.apache.poi" % "poi" % "3.8"
  val apache_poi_ooxml = "org.apache.poi" % "poi-ooxml" % "3.8"
  val postgres = "org.postgresql" % "postgresql" % "9.2-1002.jdbc4"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    apache_poi, apache_poi_ooxml
  )
  // https://mvnrepository.com/artifact/postgresql/postgresql
  libraryDependencies += postgres
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    templatesImport += "conf._"
  )

}
