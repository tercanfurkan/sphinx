
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
object editForm extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[Long,Form[Component],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(id: Long, componentForm: Form[Component]):play.api.templates.Html = {
        _display_ {import helper._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(twitterBootstrapInput.render) }};
Seq[Any](format.raw/*1.44*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.80*/(""" 

"""),_display_(Seq[Any](/*7.2*/main/*7.6*/ {_display_(Seq[Any](format.raw/*7.8*/("""
    
    <h2>Edit component</h2>
    
    """),_display_(Seq[Any](/*11.6*/form(routes.Application.update(id))/*11.41*/ {_display_(Seq[Any](format.raw/*11.43*/("""
        
        <fieldset>
        <div class="controls span3">
            """),_display_(Seq[Any](/*15.14*/inputText(componentForm("number"), '_label -> "Number"))),format.raw/*15.69*/("""
            """),_display_(Seq[Any](/*16.14*/inputText(componentForm("scope"), '_label -> "Scope"))),format.raw/*16.67*/("""
            """),_display_(Seq[Any](/*17.14*/inputText(componentForm("name"), '_label -> "Component name"))),format.raw/*17.75*/("""
            """),_display_(Seq[Any](/*18.14*/inputText(componentForm("componentDetail.datasource_class_code"), '_label -> "Data source code"))),format.raw/*18.110*/("""
            """),_display_(Seq[Any](/*19.14*/inputText(componentForm("componentDetail.datasource_class_name"), '_label -> "Data source Class Name"))),format.raw/*19.116*/("""
            """),_display_(Seq[Any](/*20.14*/inputText(componentForm("owner"), '_label -> "Owner"))),format.raw/*20.67*/("""

            """),_display_(Seq[Any](/*22.14*/select(
                componentForm("component_type.id"), 
                options(ComponentType.options), 
                '_label -> "Component Type", '_default -> "-- Choose a component type --",
                '_showConstraints -> false
            ))),format.raw/*27.14*/("""
            """),_display_(Seq[Any](/*28.14*/inputText(componentForm("componentDetail.id"), '_label -> "ID"))),format.raw/*28.77*/("""
            """),_display_(Seq[Any](/*29.14*/inputText(componentForm("componentDetail.address"), '_label -> "Address"))),format.raw/*29.87*/("""
            """),_display_(Seq[Any](/*30.14*/inputText(componentForm("componentDetail.material"), '_label -> "Material"))),format.raw/*30.89*/("""
            
            </div>
            <div class="controls span2">

            
           </div>
            <div class="controls span3">
			"""),_display_(Seq[Any](/*38.5*/inputText(componentForm("componentDetail.length_3d"), '_label -> "Length"))),format.raw/*38.79*/("""
            """),_display_(Seq[Any](/*39.14*/inputText(componentForm("componentDetail.diameter"), '_label -> "Diameter"))),format.raw/*39.89*/("""
            """),_display_(Seq[Any](/*40.14*/inputText(componentForm("componentDetail.installation_year"), '_label -> "Installation Year"))),format.raw/*40.107*/("""
            """),_display_(Seq[Any](/*41.14*/inputText(componentForm("componentDetail.x1"), '_label -> "X"))),format.raw/*41.76*/("""
            """),_display_(Seq[Any](/*42.14*/inputText(componentForm("componentDetail.y1"), '_label -> "Y"))),format.raw/*42.76*/("""
            """),_display_(Seq[Any](/*43.14*/inputText(componentForm("componentDetail.z1"), '_label -> "Z"))),format.raw/*43.76*/("""
            """),_display_(Seq[Any](/*44.14*/inputText(componentForm("componentDetail.extraWaterMeter"), '_label -> "extraWaterMeter"))),format.raw/*44.103*/("""
            """),_display_(Seq[Any](/*45.14*/inputText(componentForm("componentDetail.CCTVConditionMeter"), '_label -> "CCTVConditionMeter"))),format.raw/*45.109*/("""
            """),_display_(Seq[Any](/*46.14*/inputText(componentForm("componentDetail.operationalDisturbance"), '_label -> "operationalDisturbance"))),format.raw/*46.117*/("""
            """),_display_(Seq[Any](/*47.14*/inputText(componentForm("componentDetail.socialSensitivity"), '_label -> "socialSensitivity"))),format.raw/*47.107*/("""
            </div>
        </fieldset>
        
        <div class="actions">
            <a href=""""),_display_(Seq[Any](/*52.23*/routes/*52.29*/.Application.list())),format.raw/*52.48*/("""" class="btn">Return</a> 
        </div>
        
    """)))})),format.raw/*55.6*/("""
    
<!--     """),_display_(Seq[Any](/*57.11*/form(routes.Application.delete(id), 'class -> "topRight")/*57.68*/ {_display_(Seq[Any](format.raw/*57.70*/(""" -->
<!--     <input type="submit" value="List properties" class="btn"> -->
<!--     <input type="submit" value="Add property" class="btn"> -->
<!--         <input type="submit" value="Delete this component" class="btn danger"> -->
<!--     """)))})),format.raw/*61.11*/(""" -->
    
""")))})),format.raw/*63.2*/("""
"""))}
    }
    
    def render(id:Long,componentForm:Form[Component]) = apply(id,componentForm)
    
    def f:((Long,Form[Component]) => play.api.templates.Html) = (id,componentForm) => apply(id,componentForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 11:19:05 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/editForm.scala.html
                    HASH: 4b5723c0ac3451315e6047751291289bbf8b1b06
                    MATRIX: 772->1|900->68|932->92|1016->43|1046->65|1075->146|1115->152|1126->156|1164->158|1247->206|1291->241|1331->243|1450->326|1527->381|1578->396|1653->449|1704->464|1787->525|1838->540|1957->636|2008->651|2133->753|2184->768|2259->821|2312->838|2596->1100|2647->1115|2732->1178|2783->1193|2878->1266|2929->1281|3026->1356|3220->1515|3316->1589|3367->1604|3464->1679|3515->1694|3631->1787|3682->1802|3766->1864|3817->1879|3901->1941|3952->1956|4036->2018|4087->2033|4199->2122|4250->2137|4368->2232|4419->2247|4545->2350|4596->2365|4712->2458|4854->2564|4869->2570|4910->2589|4999->2647|5053->2665|5119->2722|5159->2724|5437->2970|5481->2983
                    LINES: 27->1|30->5|30->5|31->1|33->4|34->5|36->7|36->7|36->7|40->11|40->11|40->11|44->15|44->15|45->16|45->16|46->17|46->17|47->18|47->18|48->19|48->19|49->20|49->20|51->22|56->27|57->28|57->28|58->29|58->29|59->30|59->30|67->38|67->38|68->39|68->39|69->40|69->40|70->41|70->41|71->42|71->42|72->43|72->43|73->44|73->44|74->45|74->45|75->46|75->46|76->47|76->47|81->52|81->52|81->52|84->55|86->57|86->57|86->57|90->61|92->63
                    -- GENERATED --
                */
            