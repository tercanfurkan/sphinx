
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
object listManholes extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template4[Component.Page,String,String,String,play.api.templates.Html] {

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
    routes.Application.listManholes(newPage, sortBy, order, currentFilter)
    
}};
Seq[Any](format.raw/*1.99*/("""

"""),format.raw/*5.42*/("""
"""),format.raw/*27.2*/("""

"""),format.raw/*31.37*/("""
"""),format.raw/*36.2*/("""

"""),_display_(Seq[Any](/*38.2*/main/*38.6*/ {_display_(Seq[Any](format.raw/*38.8*/("""
    <h1 id="homeTitle">"""),_display_(Seq[Any](/*39.25*/Messages("manhole.list.title", currentPage.getTotalRowCount))),format.raw/*39.85*/("""</h1>

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
                                """),_display_(Seq[Any](/*80.34*/component/*80.43*/.owner)),format.raw/*80.49*/("""
                        </td>
                        <td>
                            """),_display_(Seq[Any](/*83.30*/if(component.component_type == null)/*83.66*/ {_display_(Seq[Any](format.raw/*83.68*/("""
                                <em>-</em>
                            """)))}/*85.31*/else/*85.36*/{_display_(Seq[Any](format.raw/*85.37*/("""
                                """),_display_(Seq[Any](/*86.34*/component/*86.43*/.component_type.type_name)),format.raw/*86.68*/("""
                            """)))})),format.raw/*87.30*/("""
                        </td>
                    </tr>
                """)))})),format.raw/*90.18*/("""

            </tbody>
        </table>

        <div id="pagination" class="pagination">
            <ul>
                """),_display_(Seq[Any](/*97.18*/if(currentPage.hasPrev)/*97.41*/ {_display_(Seq[Any](format.raw/*97.43*/("""
                    <li class="prev">
                        <a href=""""),_display_(Seq[Any](/*99.35*/link(currentPage.getPageIndex - 1, null))),format.raw/*99.75*/("""">&larr; Previous</a>
                    </li>
                """)))}/*101.19*/else/*101.24*/{_display_(Seq[Any](format.raw/*101.25*/("""
                    <li class="prev disabled">
                        <a>&larr; Previous</a>
                    </li>
                """)))})),format.raw/*105.18*/("""
                <li class="current">
                    <a>Displaying """),_display_(Seq[Any](/*107.36*/currentPage/*107.47*/.getDisplayXtoYofZ)),format.raw/*107.65*/("""</a>
                </li>
                """),_display_(Seq[Any](/*109.18*/if(currentPage.hasNext)/*109.41*/ {_display_(Seq[Any](format.raw/*109.43*/("""
                    <li class="next">
                        <a href=""""),_display_(Seq[Any](/*111.35*/link(currentPage.getPageIndex + 1, null))),format.raw/*111.75*/("""">Next &rarr;</a>
                    </li>
                """)))}/*113.19*/else/*113.24*/{_display_(Seq[Any](format.raw/*113.25*/("""
                    <li class="next disabled">
                        <a>Next &rarr;</a>
                    </li>
                """)))})),format.raw/*117.18*/("""
            </ul>
        </div>
        
    """)))})),format.raw/*121.6*/("""
        
""")))})),format.raw/*123.2*/("""

            """))}
    }
    
    def render(currentPage:Component.Page,currentSortBy:String,currentOrder:String,currentFilter:String) = apply(currentPage,currentSortBy,currentOrder,currentFilter)
    
    def f:((Component.Page,String,String,String) => play.api.templates.Html) = (currentPage,currentSortBy,currentOrder,currentFilter) => apply(currentPage,currentSortBy,currentOrder,currentFilter)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 11:23:42 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/listManholes.scala.html
                    HASH: c96f5580392e9357bd0eda97fdc9a25127fb3f85
                    MATRIX: 791->1|949->883|963->889|1057->919|1111->937|1123->940|1162->957|1207->966|1241->992|1329->1056|1387->1078|1421->1090|1460->1093|1487->1098|1527->232|1538->236|2098->98|2129->229|2158->766|2190->880|2219->1116|2259->1121|2271->1125|2310->1127|2372->1153|2454->1213|2503->1227|2544->1259|2584->1261|2703->1344|2717->1349|2754->1364|2808->1387|2906->1449|2943->1464|3061->1546|3096->1559|3361->1788|3376->1794|3419->1815|3518->1879|3564->1916|3604->1918|3734->2031|3747->2036|3786->2037|3956->2171|4010->2203|4069->2226|4115->2250|4174->2273|4253->2330|4376->2417|4429->2454|4469->2456|4571->2522|4586->2528|4639->2559|4678->2562|4696->2571|4723->2576|4833->2650|4851->2659|4879->2665|5007->2757|5052->2793|5092->2795|5186->2871|5199->2876|5238->2877|5309->2912|5327->2921|5374->2946|5437->2977|5546->3054|5713->3185|5745->3208|5785->3210|5896->3285|5958->3325|6045->3393|6059->3398|6099->3399|6274->3541|6386->3616|6407->3627|6448->3645|6531->3691|6564->3714|6605->3716|6717->3791|6780->3831|6863->3895|6877->3900|6917->3901|7088->4039|7172->4091|7217->4104
                    LINES: 27->1|29->32|29->32|31->32|32->33|32->33|32->33|32->33|32->33|32->33|33->34|33->34|33->34|33->34|35->6|35->6|57->1|59->5|60->27|62->31|63->36|65->38|65->38|65->38|66->39|66->39|68->41|68->41|68->41|70->43|70->43|70->43|72->45|76->49|76->49|77->50|77->50|81->54|81->54|81->54|85->58|85->58|85->58|91->64|91->64|91->64|96->69|96->69|97->70|97->70|98->71|98->71|103->76|103->76|103->76|105->78|105->78|105->78|105->78|105->78|105->78|107->80|107->80|107->80|110->83|110->83|110->83|112->85|112->85|112->85|113->86|113->86|113->86|114->87|117->90|124->97|124->97|124->97|126->99|126->99|128->101|128->101|128->101|132->105|134->107|134->107|134->107|136->109|136->109|136->109|138->111|138->111|140->113|140->113|140->113|144->117|148->121|150->123
                    -- GENERATED --
                */
            