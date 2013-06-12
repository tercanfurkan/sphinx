
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.api.templates.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import com.avaje.ebean._
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object twitterBootstrapInput extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[helper.FieldElements,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(elements: helper.FieldElements):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.34*/("""

"""),format.raw/*5.52*/("""
<div class="clearfix """),_display_(Seq[Any](/*6.23*/if(elements.hasErrors)/*6.45*/ {_display_(Seq[Any](format.raw/*6.47*/("""error""")))})),format.raw/*6.53*/("""">
    <label for=""""),_display_(Seq[Any](/*7.18*/elements/*7.26*/.id)),format.raw/*7.29*/("""">"""),_display_(Seq[Any](/*7.32*/elements/*7.40*/.label)),format.raw/*7.46*/("""</label>
    <div class="input">
        """),_display_(Seq[Any](/*9.10*/elements/*9.18*/.input)),format.raw/*9.24*/("""
        <span class="help-inline">"""),_display_(Seq[Any](/*10.36*/elements/*10.44*/.infos.mkString(", "))),format.raw/*10.65*/("""</span> 
    </div>
</div>
"""))}
    }
    
    def render(elements:helper.FieldElements) = apply(elements)
    
    def f:((helper.FieldElements) => play.api.templates.Html) = (elements) => apply(elements)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Dec 07 16:56:23 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/twitterBootstrapInput.scala.html
                    HASH: 3f1d21c813af9bc1467d0f6f9a7bcffcd6473bdf
                    MATRIX: 785->1|894->33|925->194|984->218|1014->240|1053->242|1090->248|1146->269|1162->277|1186->280|1224->283|1240->291|1267->297|1346->341|1362->349|1389->355|1462->392|1479->400|1522->421
                    LINES: 27->1|30->1|32->5|33->6|33->6|33->6|33->6|34->7|34->7|34->7|34->7|34->7|34->7|36->9|36->9|36->9|37->10|37->10|37->10
                    -- GENERATED --
                */
            