
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
object psareas extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template8[Component.AreaMeterList,String,String,String,Int,Int,Int,Int,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(currentPage: Component.AreaMeterList, currentSortBy: String, currentOrder: String, currentFilter: String, wExtra: Int, wCCTV: Int, wOperational: Int, wSensitivity: Int):play.api.templates.Html = {
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
    routes.Application.psareas(newPage, sortBy, order, currentFilter, wExtra, wCCTV, wOperational, wSensitivity)
    
}};
Seq[Any](format.raw/*1.171*/("""

"""),format.raw/*5.42*/("""
"""),format.raw/*27.2*/("""

"""),format.raw/*31.37*/("""
"""),format.raw/*36.2*/("""

"""),_display_(Seq[Any](/*38.2*/main/*38.6*/ {_display_(Seq[Any](format.raw/*38.8*/("""

    """),_display_(Seq[Any](/*40.6*/if(flash.containsKey("success"))/*40.38*/ {_display_(Seq[Any](format.raw/*40.40*/("""
        <div class="alert-message warning">
            <strong>Done!</strong> """),_display_(Seq[Any](/*42.37*/flash/*42.42*/.get("success"))),format.raw/*42.57*/("""
        </div>
    """)))})),format.raw/*44.6*/("""
    
    
    <div id="adjustmentRow" class="row">
    	<div class="control-group">
    		<p class="pull-left">Adjust Meter Weights</p>
    		<div class="controls span8">
    		<form action=""""),_display_(Seq[Any](/*51.22*/link(0, "name"))),format.raw/*51.37*/("""" method="GET">
    			<div class="input-prepend">
				<span id="spanwidth" class="add-on">Extra Water (%)</span>
				<input class="span1" id="prependedInput" type="text" placeholder="Percentage" name="we" value=""""),_display_(Seq[Any](/*54.101*/wExtra)),format.raw/*54.107*/("""">
				</div>
    			<div class="input-prepend">
				<span id="spanwidth" class="add-on">CCTV Condition (%)</span>
				<input class="span1" id="prependedInput" type="text" placeholder="Percentage" name="wcctv" value=""""),_display_(Seq[Any](/*58.104*/wCCTV)),format.raw/*58.109*/("""">
				</div>
    			<div class="input-prepend">
				<span id="spanwidth" class="add-on">Operational Disturbance (%)</span>
				<input class="span1" id="prependedInput" type="text" placeholder="Percentage" name="wo" value=""""),_display_(Seq[Any](/*62.101*/wOperational)),format.raw/*62.113*/("""">
				</div>
    			<div class="input-prepend">
				<span id="spanwidth" class="add-on">Social Sensitivity (%)</span>
				<input class="span1" id="prependedInput" type="text" placeholder="Percentage" name="ws" value=""""),_display_(Seq[Any](/*66.101*/wSensitivity)),format.raw/*66.113*/("""">
				</div>
				<input type="submit" id="searchsubmit" value="Apply weights for PI" class="btn btn-primary">
			</form>
			</div>
		</div>
	</div>

	<div id="paddingToRow" class="row">
	</div>
	<h1 id="homeTitle">"""),_display_(Seq[Any](/*76.22*/Messages("meters.list.title", currentPage.getTotalRowCount))),format.raw/*76.81*/("""</h1>

    <div id="actions">
        
        <form action=""""),_display_(Seq[Any](/*80.24*/link(0, "name"))),format.raw/*80.39*/("""" method="GET">
            <input type="search" id="searchbox" name="f" value=""""),_display_(Seq[Any](/*81.66*/currentFilter)),format.raw/*81.79*/("""" placeholder="Filter by component name...">
            <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary">
        </form>
        
    </div>
    
    """),_display_(Seq[Any](/*87.6*/if(currentPage.getTotalRowCount == 0)/*87.43*/ {_display_(Seq[Any](format.raw/*87.45*/("""
        
        <div class="well">
            <em>Nothing to display</em>
        </div>
        
    """)))}/*93.7*/else/*93.12*/{_display_(Seq[Any](format.raw/*93.13*/("""
        
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    """),_display_(Seq[Any](/*98.22*/header("name", "Component name"))),format.raw/*98.54*/("""
                    """),_display_(Seq[Any](/*99.22*/header("extraWaterMeter", "Extra Water"))),format.raw/*99.62*/("""
                    """),_display_(Seq[Any](/*100.22*/header("conditionCCTVMeter", "CCTV Condition Meter"))),format.raw/*100.74*/("""
                    """),_display_(Seq[Any](/*101.22*/header("operationalDisturbanceMeter", "Operational Disturbance Meter"))),format.raw/*101.92*/("""
                    """),_display_(Seq[Any](/*102.22*/header("socialSensitivityMeter", "Social Sensitivity Meter"))),format.raw/*102.82*/("""
                    """),_display_(Seq[Any](/*103.22*/header("performanceIndex", "Performance Index"))),format.raw/*103.69*/("""
                </tr>
            </thead>
            <tbody>

                """),_display_(Seq[Any](/*108.18*/for(areaAndMeter <- currentPage.getList) yield /*108.58*/ {_display_(Seq[Any](format.raw/*108.60*/("""
                    <tr>
                        <td>"""),_display_(Seq[Any](/*110.30*/areaAndMeter/*110.42*/.component.name)),format.raw/*110.57*/("""</td>
                        """),_display_(Seq[Any](/*111.26*/if(areaAndMeter.extraWaterMeter == wExtra)/*111.68*/ {_display_(Seq[Any](format.raw/*111.70*/("""
                        <td class="successtd">
                        	"""),_display_(Seq[Any](/*113.27*/areaAndMeter/*113.39*/.component.componentDetail.extraWaterMeter)),format.raw/*113.81*/(""" | """),_display_(Seq[Any](/*113.85*/areaAndMeter/*113.97*/.extraWaterMeter)),format.raw/*113.113*/("""
                        </td>
                        """)))}/*115.27*/else/*115.32*/{_display_(Seq[Any](format.raw/*115.33*/("""
                        <td>
                        	"""),_display_(Seq[Any](/*117.27*/areaAndMeter/*117.39*/.component.componentDetail.extraWaterMeter)),format.raw/*117.81*/(""" | """),_display_(Seq[Any](/*117.85*/areaAndMeter/*117.97*/.extraWaterMeter)),format.raw/*117.113*/("""
                        </td>
                        """)))})),format.raw/*119.26*/("""
                        """),_display_(Seq[Any](/*120.26*/if(areaAndMeter.component.componentDetail.CCTVConditionMeter == 0)/*120.92*/ {_display_(Seq[Any](format.raw/*120.94*/("""
                        <td>
                            No Inspections
                        </td>
                        """)))}/*124.27*/else/*124.32*/{_display_(Seq[Any](format.raw/*124.33*/("""
                        """),_display_(Seq[Any](/*125.26*/if(areaAndMeter.conditionCCTVMeter == wCCTV)/*125.70*/ {_display_(Seq[Any](format.raw/*125.72*/("""
                        <td class="successtd">
                            """),_display_(Seq[Any](/*127.30*/areaAndMeter/*127.42*/.component.componentDetail.CCTVConditionMeter)),format.raw/*127.87*/(""" | """),_display_(Seq[Any](/*127.91*/areaAndMeter/*127.103*/.conditionCCTVMeter)),format.raw/*127.122*/("""
                        </td>
                        """)))}/*129.27*/else/*129.32*/{_display_(Seq[Any](format.raw/*129.33*/("""
                        <td>
                            """),_display_(Seq[Any](/*131.30*/areaAndMeter/*131.42*/.component.componentDetail.CCTVConditionMeter)),format.raw/*131.87*/(""" | """),_display_(Seq[Any](/*131.91*/areaAndMeter/*131.103*/.conditionCCTVMeter)),format.raw/*131.122*/("""
                        </td>
                        """)))})),format.raw/*133.26*/("""
                        """)))})),format.raw/*134.26*/("""
                        """),_display_(Seq[Any](/*135.26*/if(areaAndMeter.component.componentDetail.operationalDisturbance == 0)/*135.96*/ {_display_(Seq[Any](format.raw/*135.98*/("""
                        <td>
                            No Blockage & Flushing
                        </td>
                        """)))}/*139.27*/else/*139.32*/{_display_(Seq[Any](format.raw/*139.33*/("""
                        """),_display_(Seq[Any](/*140.26*/if(areaAndMeter.operationalDisturbanceMeter == wOperational)/*140.86*/ {_display_(Seq[Any](format.raw/*140.88*/("""
                        <td class="successtd">
                            """),_display_(Seq[Any](/*142.30*/areaAndMeter/*142.42*/.component.componentDetail.operationalDisturbance)),format.raw/*142.91*/(""" | """),_display_(Seq[Any](/*142.95*/areaAndMeter/*142.107*/.operationalDisturbanceMeter)),format.raw/*142.135*/("""
                        </td>
                        """)))}/*144.27*/else/*144.32*/{_display_(Seq[Any](format.raw/*144.33*/("""
                        <td>
                            """),_display_(Seq[Any](/*146.30*/areaAndMeter/*146.42*/.component.componentDetail.operationalDisturbance)),format.raw/*146.91*/(""" | """),_display_(Seq[Any](/*146.95*/areaAndMeter/*146.107*/.operationalDisturbanceMeter)),format.raw/*146.135*/("""
                        </td>                        
                        """)))})),format.raw/*148.26*/("""
                        """)))})),format.raw/*149.26*/("""
                        """),_display_(Seq[Any](/*150.26*/if(areaAndMeter.socialSensitivityMeter == wSensitivity)/*150.81*/ {_display_(Seq[Any](format.raw/*150.83*/("""
                        <td class="successtd">
                            """),_display_(Seq[Any](/*152.30*/areaAndMeter/*152.42*/.component.componentDetail.socialSensitivity)),format.raw/*152.86*/(""" | """),_display_(Seq[Any](/*152.90*/areaAndMeter/*152.102*/.socialSensitivityMeter)),format.raw/*152.125*/("""
                        </td>
                        """)))}/*154.27*/else/*154.32*/{_display_(Seq[Any](format.raw/*154.33*/("""
                        <td>
                            """),_display_(Seq[Any](/*156.30*/areaAndMeter/*156.42*/.component.componentDetail.socialSensitivity)),format.raw/*156.86*/(""" | """),_display_(Seq[Any](/*156.90*/areaAndMeter/*156.102*/.socialSensitivityMeter)),format.raw/*156.125*/("""
                        </td>                        
                        """)))})),format.raw/*158.26*/("""
                        <td>
                            """),_display_(Seq[Any](/*160.30*/areaAndMeter/*160.42*/.pi)),format.raw/*160.45*/("""
                        </td>
                    </tr>
                """)))})),format.raw/*163.18*/("""

            </tbody>
        </table>

        <div id="pagination" class="pagination">
            <ul>
                """),_display_(Seq[Any](/*170.18*/if(currentPage.hasPrev)/*170.41*/ {_display_(Seq[Any](format.raw/*170.43*/("""
                    <li class="prev">
                        <a href=""""),_display_(Seq[Any](/*172.35*/link(currentPage.getPageIndex - 1, null))),format.raw/*172.75*/("""">&larr; Previous</a>
                    </li>
                """)))}/*174.19*/else/*174.24*/{_display_(Seq[Any](format.raw/*174.25*/("""
                    <li class="prev disabled">
                        <a>&larr; Previous</a>
                    </li>
                """)))})),format.raw/*178.18*/("""
                <li class="current">
                    <a>Displaying """),_display_(Seq[Any](/*180.36*/currentPage/*180.47*/.getDisplayXtoYofZ)),format.raw/*180.65*/("""</a>
                </li>
                """),_display_(Seq[Any](/*182.18*/if(currentPage.hasNext)/*182.41*/ {_display_(Seq[Any](format.raw/*182.43*/("""
                    <li class="next">
                        <a href=""""),_display_(Seq[Any](/*184.35*/link(currentPage.getPageIndex + 1, null))),format.raw/*184.75*/("""">Next &rarr;</a>
                    </li>
                """)))}/*186.19*/else/*186.24*/{_display_(Seq[Any](format.raw/*186.25*/("""
                    <li class="next disabled">
                        <a>Next &rarr;</a>
                    </li>
                """)))})),format.raw/*190.18*/("""
            </ul>
        </div>
        
    """)))})),format.raw/*194.6*/("""
        
""")))})),format.raw/*196.2*/("""

            """))}
    }
    
    def render(currentPage:Component.AreaMeterList,currentSortBy:String,currentOrder:String,currentFilter:String,wExtra:Int,wCCTV:Int,wOperational:Int,wSensitivity:Int) = apply(currentPage,currentSortBy,currentOrder,currentFilter,wExtra,wCCTV,wOperational,wSensitivity)
    
    def f:((Component.AreaMeterList,String,String,String,Int,Int,Int,Int) => play.api.templates.Html) = (currentPage,currentSortBy,currentOrder,currentFilter,wExtra,wCCTV,wOperational,wSensitivity) => apply(currentPage,currentSortBy,currentOrder,currentFilter,wExtra,wCCTV,wOperational,wSensitivity)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 10:07:58 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/psareas.scala.html
                    HASH: 5a30f7f5bc8681637476316b6daebc521f3d5234
                    MATRIX: 811->1|1041->993|1055->999|1149->1029|1203->1047|1215->1050|1254->1067|1299->1076|1333->1102|1421->1166|1479->1188|1513->1200|1552->1203|1579->1208|1619->304|1630->308|2229->170|2260->301|2289->876|2321->990|2350->1226|2390->1231|2402->1235|2441->1237|2485->1246|2526->1278|2566->1280|2685->1363|2699->1368|2736->1383|2790->1406|3026->1606|3063->1621|3317->1838|3346->1844|3605->2066|3633->2071|3898->2299|3933->2311|4193->2534|4228->2546|4490->2772|4571->2831|4673->2897|4710->2912|4828->2994|4863->3007|5094->3203|5140->3240|5180->3242|5310->3355|5323->3360|5362->3361|5532->3495|5586->3527|5645->3550|5707->3590|5767->3613|5842->3665|5902->3688|5995->3758|6055->3781|6138->3841|6198->3864|6268->3911|6392->3998|6449->4038|6490->4040|6584->4097|6606->4109|6644->4124|6713->4156|6765->4198|6806->4200|6919->4276|6941->4288|7006->4330|7047->4334|7069->4346|7109->4362|7187->4421|7201->4426|7241->4427|7336->4485|7358->4497|7423->4539|7464->4543|7486->4555|7526->4571|7617->4629|7681->4656|7757->4722|7798->4724|7950->4857|7964->4862|8004->4863|8068->4890|8122->4934|8163->4936|8279->5015|8301->5027|8369->5072|8410->5076|8433->5088|8476->5107|8554->5166|8568->5171|8608->5172|8706->5233|8728->5245|8796->5290|8837->5294|8860->5306|8903->5325|8994->5383|9054->5410|9118->5437|9198->5507|9239->5509|9399->5650|9413->5655|9453->5656|9517->5683|9587->5743|9628->5745|9744->5824|9766->5836|9838->5885|9879->5889|9902->5901|9954->5929|10032->5988|10046->5993|10086->5994|10184->6055|10206->6067|10278->6116|10319->6120|10342->6132|10394->6160|10509->6242|10569->6269|10633->6296|10698->6351|10739->6353|10855->6432|10877->6444|10944->6488|10985->6492|11008->6504|11055->6527|11133->6586|11147->6591|11187->6592|11285->6653|11307->6665|11374->6709|11415->6713|11438->6725|11485->6748|11600->6830|11698->6891|11720->6903|11746->6906|11856->6983|12024->7114|12057->7137|12098->7139|12210->7214|12273->7254|12360->7322|12374->7327|12414->7328|12589->7470|12701->7545|12722->7556|12763->7574|12846->7620|12879->7643|12920->7645|13032->7720|13095->7760|13178->7824|13192->7829|13232->7830|13403->7968|13487->8020|13532->8033
                    LINES: 27->1|29->32|29->32|31->32|32->33|32->33|32->33|32->33|32->33|32->33|33->34|33->34|33->34|33->34|35->6|35->6|57->1|59->5|60->27|62->31|63->36|65->38|65->38|65->38|67->40|67->40|67->40|69->42|69->42|69->42|71->44|78->51|78->51|81->54|81->54|85->58|85->58|89->62|89->62|93->66|93->66|103->76|103->76|107->80|107->80|108->81|108->81|114->87|114->87|114->87|120->93|120->93|120->93|125->98|125->98|126->99|126->99|127->100|127->100|128->101|128->101|129->102|129->102|130->103|130->103|135->108|135->108|135->108|137->110|137->110|137->110|138->111|138->111|138->111|140->113|140->113|140->113|140->113|140->113|140->113|142->115|142->115|142->115|144->117|144->117|144->117|144->117|144->117|144->117|146->119|147->120|147->120|147->120|151->124|151->124|151->124|152->125|152->125|152->125|154->127|154->127|154->127|154->127|154->127|154->127|156->129|156->129|156->129|158->131|158->131|158->131|158->131|158->131|158->131|160->133|161->134|162->135|162->135|162->135|166->139|166->139|166->139|167->140|167->140|167->140|169->142|169->142|169->142|169->142|169->142|169->142|171->144|171->144|171->144|173->146|173->146|173->146|173->146|173->146|173->146|175->148|176->149|177->150|177->150|177->150|179->152|179->152|179->152|179->152|179->152|179->152|181->154|181->154|181->154|183->156|183->156|183->156|183->156|183->156|183->156|185->158|187->160|187->160|187->160|190->163|197->170|197->170|197->170|199->172|199->172|201->174|201->174|201->174|205->178|207->180|207->180|207->180|209->182|209->182|209->182|211->184|211->184|213->186|213->186|213->186|217->190|221->194|223->196
                    -- GENERATED --
                */
            