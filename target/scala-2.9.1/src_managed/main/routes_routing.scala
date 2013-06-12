// @SOURCE:C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/conf/routes
// @HASH:715643d7aadcc7e5e6f65b74eb4045a23ff71ca0
// @DATE:Wed Dec 12 10:41:18 EET 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:9
val controllers_Application_list1 = Route("GET", PathPattern(List(StaticPart("/pumpingstations"))))
                    

// @LINE:10
val controllers_Application_listPipes2 = Route("GET", PathPattern(List(StaticPart("/pipes"))))
                    

// @LINE:11
val controllers_Application_listManholes3 = Route("GET", PathPattern(List(StaticPart("/manholes"))))
                    

// @LINE:13
val controllers_Application_psareas4 = Route("GET", PathPattern(List(StaticPart("/psareas"))))
                    

// @LINE:16
val controllers_Application_create5 = Route("GET", PathPattern(List(StaticPart("/components/new"))))
                    

// @LINE:17
val controllers_Application_save6 = Route("POST", PathPattern(List(StaticPart("/components"))))
                    

// @LINE:20
val controllers_Application_edit7 = Route("GET", PathPattern(List(StaticPart("/components/"),DynamicPart("id", """[^/]+"""))))
                    

// @LINE:21
val controllers_Application_update8 = Route("POST", PathPattern(List(StaticPart("/components/"),DynamicPart("id", """[^/]+"""))))
                    

// @LINE:24
val controllers_Application_delete9 = Route("POST", PathPattern(List(StaticPart("/components/"),DynamicPart("id", """[^/]+"""),StaticPart("/delete"))))
                    

// @LINE:27
val controllers_Assets_at10 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index()"""),("""GET""","""/pumpingstations""","""controllers.Application.list(p:Int ?= 0, s:String ?= "name", o:String ?= "asc", f:String ?= "")"""),("""GET""","""/pipes""","""controllers.Application.listPipes(p:Int ?= 0, s:String ?= "name", o:String ?= "asc", f:String ?= "")"""),("""GET""","""/manholes""","""controllers.Application.listManholes(p:Int ?= 0, s:String ?= "name", o:String ?= "asc", f:String ?= "")"""),("""GET""","""/psareas""","""controllers.Application.psareas(p:Int ?= 0, s:String ?= "name", o:String ?= "asc", f:String ?= "", we:Int ?= 25, wcctv:Int ?= 25, wo:Int ?= 25, ws:Int ?= 25)"""),("""GET""","""/components/new""","""controllers.Application.create()"""),("""POST""","""/components""","""controllers.Application.save()"""),("""GET""","""/components/$id<[^/]+>""","""controllers.Application.edit(id:Long)"""),("""POST""","""/components/$id<[^/]+>""","""controllers.Application.update(id:Long)"""),("""POST""","""/components/$id<[^/]+>/delete""","""controllers.Application.delete(id:Long)"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:9
case controllers_Application_list1(params) => {
   call(params.fromQuery[Int]("p", Some(0)), params.fromQuery[String]("s", Some("name")), params.fromQuery[String]("o", Some("asc")), params.fromQuery[String]("f", Some(""))) { (p, s, o, f) =>
        invokeHandler(_root_.controllers.Application.list(p, s, o, f), HandlerDef(this, "controllers.Application", "list", Seq(classOf[Int], classOf[String], classOf[String], classOf[String])))
   }
}
                    

// @LINE:10
case controllers_Application_listPipes2(params) => {
   call(params.fromQuery[Int]("p", Some(0)), params.fromQuery[String]("s", Some("name")), params.fromQuery[String]("o", Some("asc")), params.fromQuery[String]("f", Some(""))) { (p, s, o, f) =>
        invokeHandler(_root_.controllers.Application.listPipes(p, s, o, f), HandlerDef(this, "controllers.Application", "listPipes", Seq(classOf[Int], classOf[String], classOf[String], classOf[String])))
   }
}
                    

// @LINE:11
case controllers_Application_listManholes3(params) => {
   call(params.fromQuery[Int]("p", Some(0)), params.fromQuery[String]("s", Some("name")), params.fromQuery[String]("o", Some("asc")), params.fromQuery[String]("f", Some(""))) { (p, s, o, f) =>
        invokeHandler(_root_.controllers.Application.listManholes(p, s, o, f), HandlerDef(this, "controllers.Application", "listManholes", Seq(classOf[Int], classOf[String], classOf[String], classOf[String])))
   }
}
                    

// @LINE:13
case controllers_Application_psareas4(params) => {
   call(params.fromQuery[Int]("p", Some(0)), params.fromQuery[String]("s", Some("name")), params.fromQuery[String]("o", Some("asc")), params.fromQuery[String]("f", Some("")), params.fromQuery[Int]("we", Some(25)), params.fromQuery[Int]("wcctv", Some(25)), params.fromQuery[Int]("wo", Some(25)), params.fromQuery[Int]("ws", Some(25))) { (p, s, o, f, we, wcctv, wo, ws) =>
        invokeHandler(_root_.controllers.Application.psareas(p, s, o, f, we, wcctv, wo, ws), HandlerDef(this, "controllers.Application", "psareas", Seq(classOf[Int], classOf[String], classOf[String], classOf[String], classOf[Int], classOf[Int], classOf[Int], classOf[Int])))
   }
}
                    

// @LINE:16
case controllers_Application_create5(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.create(), HandlerDef(this, "controllers.Application", "create", Nil))
   }
}
                    

// @LINE:17
case controllers_Application_save6(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.save(), HandlerDef(this, "controllers.Application", "save", Nil))
   }
}
                    

// @LINE:20
case controllers_Application_edit7(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Application.edit(id), HandlerDef(this, "controllers.Application", "edit", Seq(classOf[Long])))
   }
}
                    

// @LINE:21
case controllers_Application_update8(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Application.update(id), HandlerDef(this, "controllers.Application", "update", Seq(classOf[Long])))
   }
}
                    

// @LINE:24
case controllers_Application_delete9(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Application.delete(id), HandlerDef(this, "controllers.Application", "delete", Seq(classOf[Long])))
   }
}
                    

// @LINE:27
case controllers_Assets_at10(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                