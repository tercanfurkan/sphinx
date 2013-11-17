import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "sphinx"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	javaCore, javaJdbc, javaJpa, filters, anorm, jdbc,
		"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
		"postgresql" % "postgresql" % "9.1-901.jdbc4",
		"net.sourceforge.jexcelapi" % "jxl" % "2.6.12",
		"org.apache.poi" % "poi" % "3.9"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
    )

}
