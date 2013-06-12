
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(content: Html):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""

<!DOCTYPE html>
<html>
<head>
<title>Sphinx</title>
<link rel="stylesheet" type="text/css" media="screen"
	href=""""),_display_(Seq[Any](/*8.9*/routes/*8.15*/.Assets.at("stylesheets/bootstrap.css"))),format.raw/*8.54*/("""">
<link rel="stylesheet" type="text/css" media="screen"
	href=""""),_display_(Seq[Any](/*10.9*/routes/*10.15*/.Assets.at("bootstrap-responsive.css"))),format.raw/*10.53*/("""">
<link rel="stylesheet" type="text/css" media="screen"
	href=""""),_display_(Seq[Any](/*12.9*/routes/*12.15*/.Assets.at("stylesheets/main.css"))),format.raw/*12.49*/("""">
<link rel="stylesheet" type="text/css" media="screen"
	href=""""),_display_(Seq[Any](/*14.9*/routes/*14.15*/.Assets.at("stylesheets/addon.css"))),format.raw/*14.50*/("""">
<style type="text/css">
body """),format.raw("""{"""),format.raw/*16.7*/("""
	padding-top: 60px;
	padding-bottom: 40px;
"""),format.raw("""}"""),format.raw/*19.2*/("""

.sidebar-nav """),format.raw("""{"""),format.raw/*21.15*/("""
	padding: 9px 0;
"""),format.raw("""}"""),format.raw/*23.2*/("""
</style>
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href=""""),_display_(Seq[Any](/*34.34*/routes/*34.40*/.Application.index())),format.raw/*34.60*/("""">Sphinx
					&mdash; EfeSus Database</a>
				<div class="nav-collapse collapse">
					<p class="navbar-text pull-right">
						login <a href="#" class="navbar-link"></a>
					</p>
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header">Sewer Components</li>
						<li><a href=""""),_display_(Seq[Any](/*57.21*/routes/*57.27*/.Application.index())),format.raw/*57.47*/("""">Pumping station</a></li>
						<li><a href=""""),_display_(Seq[Any](/*58.21*/routes/*58.27*/.Application.listPipes())),format.raw/*58.51*/("""#">Pipe</a></li>
						<li><a href=""""),_display_(Seq[Any](/*59.21*/routes/*59.27*/.Application.listManholes())),format.raw/*59.54*/("""#">Manhole</a></li>
						<li class="nav-header">Performance Meter</li>
						<li><a href=""""),_display_(Seq[Any](/*61.21*/routes/*61.27*/.Application.psareas())),format.raw/*61.49*/("""">Meter & PI Calculation</a></li>
					</ul>
				</div>
				<!--/.well -->
			</div>

			<!--/span-->
			<div class="span9">
				<section id="main">"""),_display_(Seq[Any](/*69.25*/content)),format.raw/*69.32*/("""</section>
			</div>
			<!--/span-->
		</div>
		<!--/row-->

		<hr>

		<footer>
			<p>&copy; EfeSus 2012</p>
		</footer>

	</div>
	<!--/.fluid-container-->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- 	<script src="../assets/js/jquery.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-transition.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-alert.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-modal.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-dropdown.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-scrollspy.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-tab.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-tooltip.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-popover.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-button.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-collapse.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-carousel.js"></script> -->
	<!-- 	<script src="../assets/js/bootstrap-typeahead.js"></script> -->


</body>
</html>
"""))}
    }
    
    def render(content:Html) = apply(content)
    
    def f:((Html) => play.api.templates.Html) = (content) => apply(content)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Dec 12 11:19:05 EET 2012
                    SOURCE: C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/app/views/main.scala.html
                    HASH: 45d0cc2a77fbf9553aec9f434e2cae25e125b28d
                    MATRIX: 752->1|844->16|1001->139|1015->145|1075->184|1177->251|1192->257|1252->295|1354->362|1369->368|1425->402|1527->469|1542->475|1599->510|1680->545|1774->593|1839->611|1906->632|2313->1003|2328->1009|2370->1029|3065->1688|3080->1694|3122->1714|3206->1762|3221->1768|3267->1792|3341->1830|3356->1836|3405->1863|3535->1957|3550->1963|3594->1985|3787->2142|3816->2149
                    LINES: 27->1|30->1|37->8|37->8|37->8|39->10|39->10|39->10|41->12|41->12|41->12|43->14|43->14|43->14|45->16|48->19|50->21|52->23|63->34|63->34|63->34|86->57|86->57|86->57|87->58|87->58|87->58|88->59|88->59|88->59|90->61|90->61|90->61|98->69|98->69
                    -- GENERATED --
                */
            