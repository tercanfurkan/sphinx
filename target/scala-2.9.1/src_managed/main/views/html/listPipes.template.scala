
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
object listPipes extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[Component.Page,String,String,String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(currentPage: Component.Page, currentSortBy: String, currentOrder: String, currentFilter: String):play.api.templates.Html = {
        _display_ {
def /*32.2*/header/*32.8*/(key:String, title:String):play.api.templates.Html = {_display_(

Seq[Any](format.raw/*32.38*/("""
    <th class=""""),_display_(Seq[Any](/*33.17*/key/*33.20*/.replace(".","_"))),format.raw/*33.37*/(""" header """),_display_(Seq[Any](/*33.46*/if(currentSortBy == key){/*33.72*/{if(currentOrder == "asc") "headerSortDown" else "headerSortUp"}})),format.raw/*33.136*/("""">
        <a href=""""),_display_(Seq[Any](/*34.19*/link(0, key))),format.raw/*34.31*/("""">"""),_display_(Seq[Any](/*34.34*/title)),format.raw/*34.39*/("""</a>
    </th>
""")))};def /*6.2*/link/*6.6*/(newPage:Int, newSortBy:String) = {{
    
    var sortBy = currentSortBy
    var order = currentOrder
    
    if(newSortBy != null) {
        sortBy = newSortBy
        if(currentSortBy == newSortBy) {
            if(currentOrder == "asc") {
                order = "desc"
            } else {
                order = "asc"
            }
        } else {
            order = "asc"
        }
    }
    
    // Generate the link
    routes.Application.listPipes(newPage, sortBy, order, currentFilter)
    
}};
Seq[Any](format.raw/*1.99*/("""

"""),format.raw/*5.42*/("""
"""),format.raw/*27.2*/("""

"""),format.raw/*31.37*/("""
"""),format.raw/*36.2*/("""

"""),_display_(Seq[Any](/*38.2*/main/*38.6*/ {_display_(Seq[Any](format.raw/*38.8*/("""
    <h1 id="homeTitle">"""),_display_(Seq[Any](/*39.25*/Messages("pipe.list.title", currentPage.getTotalRowCount))),format.raw/*39.82*/("""</h1>

    """),_display_(Seq[Any](/*41.6*/if(flash.containsKey("success"))/*41.38*/ {_display_(Seq[Any](format.raw/*41.40*/("""
        <div class="alert-message warning">
            <strong>Done!</strong> """),_display_(Seq[Any](/*43.37*/flash/*43.42*/.get("success"))),format.raw/*43.57*/("""
        </div>
    """)))})),format.raw/*45.6*/(""" 

    <div id="actions">
        
        <form action=""""),_display_(Seq[Any](/*49.24*/link(0, "name"))),format.raw/*49.39*/("""" method="GET">
            <input type="search" id="searchbox" name="f" value=""""),_display_(Seq[Any](/*50.66*/currentFilter)),format.raw/*50.79*/("""" placeholder="Filter by component name...">
            <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary">
        </form>
        
<!--         <a class="btn btn-success" id="add" href=""""),_display_(Seq[Any](/*54.57*/routes/*54.63*/.Application.create())),format.raw/*54.84*/("""">Add a new component</a> -->
        
    </div>
    
    """),_display_(Seq[Any](/*58.6*/if(currentPage.getTotalRowCount == 0)/*58.43*/ {_display_(Seq[Any](format.raw/*58.45*/("""
        
        <div class="well">
            <em>Nothing to display</em>
        </div>
        
    """)))}/*64.7*/else/*64.12*/{_display_(Seq[Any](format.raw/*64.13*/("""
        
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    """),_display_(Seq[Any](/*69.22*/header("name", "Component name"))),format.raw/*69.54*/("""
                    """),_display_(Seq[Any](/*70.22*/header("owner", "Owner"))),format.raw/*70.46*/("""
                    """),_display_(Seq[Any](/*71.22*/header("component_type.type_name.name", "Component type"))),format.raw/*71.79*/("""
                </tr>
            </thead>
            <tbody>

                """),_display_(Seq[Any](/*76.18*/for(component <- currentPage.getList) yield /*76.55*/ {_display_(Seq[Any](format.raw/*76.57*/("""
                    <tr>
                        <td><a href=""""),_display_(Seq[Any](/*78.39*/routes/*78.45*/.Application.edit(component.id))),format.raw/*78.76*/("""">"""),_display_(Seq[Any](/*78.79*/component/*78.88*/.name)),format.raw/*78.93*/("""</a></td>
                        <td>
                            """),_display_(Seq[Any](/*80.30*/if(component.ownerComponent == null)/*80.66*/ {_display_(Seq[Any](format.raw/*80.68*/("""
                                <em>-</em>
                            """)))}/*82.31*/else/*82.36*/{_display_(Seq[Any](format.raw/*82.37*/("""
                                """),_display_(Seq[Any](/*83.34*/component/*83.43*/.ownerComponent.name)),format.raw/*83.63*/("""
                            """)))})),format.raw/*84.30*/("""
                        </td>
                        <td>
                            """),_display_(Seq[Any](/*87.30*/if(component.component_type == null)/*87.66*/ {_display_(Seq[Any](format.raw/*87.68*/("""
                                <em>-</em>
                            """)))}/*89.31*/else/*89.36*/{_display_(Seq[Any](format.raw/*89.37*/("""
                                """),_display_(Seq[Any](/*90.34*/component/*90.43*/.component_type.type_name)),format.raw/*90.68*/("""
                            """)))})),format.raw/*91.30*/("""
                        </td>
                    </tr>
                """)))})),format.raw/*94.18*/("""

            </tbody>
        </table>

        <div id="pagination" class="pagination">
            <ul>
                """),_display_(Seq[Any](/*101.18*/if(currentPage.hasPrev)/*101.41*/ {_display_(Seq[Any](format.raw/*101.43*/("""
                    <li class="prev">
                        <a href=""""),_display_(Seq[Any](/*103.35*/link(currentPage.getPageIndex - 1, null))),format.raw/*103.75*/("""">&larr; Previous</a>
                    </li>
                """)))}/*105.19*/else/*105.24*/{_display_(Seq[Any](format.raw/*105.25*/("""
                    <li class="prev disabled">
                        <a>&larr; Previous</a>
                    </li>
                """)))})),format.raw/*109.18*/("""
                <li class="current">
                    <a>Displaying """),_display_(Seq[Any](/*111.36*/currentPage/*111.47*/.getDisplayXtoYofZ)),format.raw/*111.65*/("""</a>
                </li>
                """),_display_(Seq[Any](/*113.18*/if(currentPage.hasNext)/*113.41*/ {_display_(Seq[Any](format.raw/*113.43*/("""
                    <li class="next">
                        <a href=""""),_display_(Seq[Any](/*115.35*/link(currentPage.getPageIndex + 1, null))),format.raw/*115.75*/("""">Next &rarr;</a>
                    </li>
                """)))}/*117.19*/else/*117.24*/{_display_(Seq[Any](format.raw/*117.25*/("""
                    <li class="next disabled">
                        <a>Next &rarr;</a>
                    </li>
                """)))})),format.raw/*121.18*/("""
            </ul>
        </div>
        
    """)))})),format.raw/*125.6*/("""
        
""")))})),format.raw/*127.2*/("""

            """))}
    }
    
    def render(currentPage:Component.Page,currentSortBy:String,currentOrder:String,currentFilter:String) = apply(currentPage,currentSortBy,currentOrder,currentFilter)
    
    def f:((Component.Page,String,String,String) => play.api.templates.Html) = (currentPage,currentSortBy,currentOrder,currentFilter) => apply(currentPage,currentSortBy,currentOrder,currentFilter)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 11:23:42 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/listPipes.scala.html
                    HASH: 8e652bd6cd2706a9ae3d30a8736f9f3d5f6a1ad0
                    MATRIX: 788->1|946->880|960->886|1054->916|1108->934|1120->937|1159->954|1204->963|1238->989|1326->1053|1384->1075|1418->1087|1457->1090|1484->1095|1524->232|1535->236|2092->98|2123->229|2152->763|2184->877|2213->1113|2253->1118|2265->1122|2304->1124|2366->1150|2445->1207|2494->1221|2535->1253|2575->1255|2694->1338|2708->1343|2745->1358|2799->1381|2897->1443|2934->1458|3052->1540|3087->1553|3352->1782|3367->1788|3410->1809|3509->1873|3555->1910|3595->1912|3725->2025|3738->2030|3777->2031|3947->2165|4001->2197|4060->2220|4106->2244|4165->2267|4244->2324|4367->2411|4420->2448|4460->2450|4562->2516|4577->2522|4630->2553|4669->2556|4687->2565|4714->2570|4820->2640|4865->2676|4905->2678|4999->2754|5012->2759|5051->2760|5122->2795|5140->2804|5182->2824|5245->2855|5373->2947|5418->2983|5458->2985|5552->3061|5565->3066|5604->3067|5675->3102|5693->3111|5740->3136|5803->3167|5912->3244|6080->3375|6113->3398|6154->3400|6266->3475|6329->3515|6416->3583|6430->3588|6470->3589|6645->3731|6757->3806|6778->3817|6819->3835|6902->3881|6935->3904|6976->3906|7088->3981|7151->4021|7234->4085|7248->4090|7288->4091|7459->4229|7543->4281|7588->4294
                    LINES: 27->1|29->32|29->32|31->32|32->33|32->33|32->33|32->33|32->33|32->33|33->34|33->34|33->34|33->34|35->6|35->6|57->1|59->5|60->27|62->31|63->36|65->38|65->38|65->38|66->39|66->39|68->41|68->41|68->41|70->43|70->43|70->43|72->45|76->49|76->49|77->50|77->50|81->54|81->54|81->54|85->58|85->58|85->58|91->64|91->64|91->64|96->69|96->69|97->70|97->70|98->71|98->71|103->76|103->76|103->76|105->78|105->78|105->78|105->78|105->78|105->78|107->80|107->80|107->80|109->82|109->82|109->82|110->83|110->83|110->83|111->84|114->87|114->87|114->87|116->89|116->89|116->89|117->90|117->90|117->90|118->91|121->94|128->101|128->101|128->101|130->103|130->103|132->105|132->105|132->105|136->109|138->111|138->111|138->111|140->113|140->113|140->113|142->115|142->115|144->117|144->117|144->117|148->121|152->125|154->127
                    -- GENERATED --
                */
            