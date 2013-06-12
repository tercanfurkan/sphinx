import sbt._
import Keys._
import play.Project_
import com.github.play2war.plugin._

object ApplicationBuild extends Build {

    val appName         = "sphinx"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    
      "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "net.sourceforge.jexcelapi" % "jxl" % "2.6.12",
      "org.apache.poi" % "poi" % "3.9"
    )

    val main = play.Project(appName, appVersion, appDependencies, mainLang = JAVA).settings( 
      ebeanEnabled := false
    )

}
