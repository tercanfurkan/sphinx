
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
object createForm extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[Component],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(componentForm: Form[Component]):play.api.templates.Html = {
        _display_ {import helper._

implicit def /*5.2*/implicitFieldConstructor/*5.26*/ = {{ FieldConstructor(twitterBootstrapInput.render) }};
Seq[Any](format.raw/*1.34*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.80*/(""" 

"""),_display_(Seq[Any](/*7.2*/main/*7.6*/ {_display_(Seq[Any](format.raw/*7.8*/("""
    
    <h1>Add a component</h1>
    
    """),_display_(Seq[Any](/*11.6*/form(routes.Application.save())/*11.37*/ {_display_(Seq[Any](format.raw/*11.39*/("""
        
        <fieldset>
        
        <div class="controls span3">
            """),_display_(Seq[Any](/*16.14*/inputText(componentForm("name"), '_label -> "Component name"))),format.raw/*16.75*/("""
            """),_display_(Seq[Any](/*17.14*/inputText(componentForm("componentDetail.datasource_class_code"), '_label -> "Data source code"))),format.raw/*17.110*/("""
            """),_display_(Seq[Any](/*18.14*/inputText(componentForm("componentDetail.datasource_class_name"), '_label -> "Data source Class Name"))),format.raw/*18.116*/("""
            """),_display_(Seq[Any](/*19.14*/inputText(componentForm("owner"), '_label -> "Owner"))),format.raw/*19.67*/("""

            """),_display_(Seq[Any](/*21.14*/select(
                componentForm("component_type.id"), 
                options(ComponentType.options), 
                '_label -> "Component Type", '_default -> "-- Choose a component type --",
                '_showConstraints -> false
            ))),format.raw/*26.14*/("""
            """),_display_(Seq[Any](/*27.14*/inputText(componentForm("componentDetail.address"), '_label -> "Address"))),format.raw/*27.87*/("""
            """),_display_(Seq[Any](/*28.14*/inputText(componentForm("componentDetail.material"), '_label -> "Material"))),format.raw/*28.89*/("""
			"""),_display_(Seq[Any](/*29.5*/inputText(componentForm("componentDetail.length_3d"), '_label -> "Length"))),format.raw/*29.79*/("""
            """),_display_(Seq[Any](/*30.14*/inputText(componentForm("componentDetail.diameter"), '_label -> "Diameter"))),format.raw/*30.89*/("""            
            </div>
            <div class="controls span2">

            
           </div>
            <div class="controls span3">
            """),_display_(Seq[Any](/*37.14*/inputText(componentForm("componentDetail.installation_year"), '_label -> "Installation Year"))),format.raw/*37.107*/("""
            """),_display_(Seq[Any](/*38.14*/inputText(componentForm("componentDetail.x1"), '_label -> "X"))),format.raw/*38.76*/("""
            """),_display_(Seq[Any](/*39.14*/inputText(componentForm("componentDetail.y1"), '_label -> "Y"))),format.raw/*39.76*/("""
            """),_display_(Seq[Any](/*40.14*/inputText(componentForm("componentDetail.z1"), '_label -> "Z"))),format.raw/*40.76*/("""
            """),_display_(Seq[Any](/*41.14*/inputText(componentForm("componentDetail.extraWaterMeter"), '_label -> "extraWaterMeter"))),format.raw/*41.103*/("""
            """),_display_(Seq[Any](/*42.14*/inputText(componentForm("componentDetail.CCTVConditionMeter"), '_label -> "CCTVConditionMeter"))),format.raw/*42.109*/("""
            """),_display_(Seq[Any](/*43.14*/inputText(componentForm("componentDetail.operationalDisturbance"), '_label -> "operationalDisturbance"))),format.raw/*43.117*/("""
            """),_display_(Seq[Any](/*44.14*/inputText(componentForm("componentDetail.socialSensitivity"), '_label -> "socialSensitivity"))),format.raw/*44.107*/("""
            </div>
        </fieldset>
        
        <div class="actions">
            <input type="submit" value="Create this component" class="btn primary"> or 
            <a href=""""),_display_(Seq[Any](/*50.23*/routes/*50.29*/.Application.list())),format.raw/*50.48*/("""" class="btn">Cancel</a> 
        </div>
        
    """)))})),format.raw/*53.6*/("""
    
""")))})))}
    }
    
    def render(componentForm:Form[Component]) = apply(componentForm)
    
    def f:((Form[Component]) => play.api.templates.Html) = (componentForm) => apply(componentForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 11:15:18 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/createForm.scala.html
                    HASH: 78ce671353545bb66f410b9e131829aa3bead6ad
                    MATRIX: 769->1|887->58|919->82|1003->33|1033->55|1062->136|1102->142|1113->146|1151->148|1235->197|1275->228|1315->230|1444->323|1527->384|1578->399|1697->495|1748->510|1873->612|1924->627|1999->680|2052->697|2336->959|2387->974|2482->1047|2533->1062|2630->1137|2671->1143|2767->1217|2818->1232|2915->1307|3117->1473|3233->1566|3284->1581|3368->1643|3419->1658|3503->1720|3554->1735|3638->1797|3689->1812|3801->1901|3852->1916|3970->2011|4021->2026|4147->2129|4198->2144|4314->2237|4545->2432|4560->2438|4601->2457|4690->2515
                    LINES: 27->1|30->5|30->5|31->1|33->4|34->5|36->7|36->7|36->7|40->11|40->11|40->11|45->16|45->16|46->17|46->17|47->18|47->18|48->19|48->19|50->21|55->26|56->27|56->27|57->28|57->28|58->29|58->29|59->30|59->30|66->37|66->37|67->38|67->38|68->39|68->39|69->40|69->40|70->41|70->41|71->42|71->42|72->43|72->43|73->44|73->44|79->50|79->50|79->50|82->53
                    -- GENERATED --
                */
            