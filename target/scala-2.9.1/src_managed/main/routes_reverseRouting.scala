// @SOURCE:C:/Users/wa.tercano1/projects/workspace/sphinx/sphinx/conf/routes
// @HASH:715643d7aadcc7e5e6f65b74eb4045a23ff71ca0
// @DATE:Wed Dec 12 10:41:18 EET 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:27
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers {

// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:10
def listPipes(p:Int = 0, s:String = "name", o:String = "asc", f:String = "") = {
   Call("GET", "/pipes" + queryString(List(if(p == 0) None else Some(implicitly[QueryStringBindable[Int]].unbind("p", p)), if(s == "name") None else Some(implicitly[QueryStringBindable[String]].unbind("s", s)), if(o == "asc") None else Some(implicitly[QueryStringBindable[String]].unbind("o", o)), if(f == "") None else Some(implicitly[QueryStringBindable[String]].unbind("f", f)))))
}
                                                        
 
// @LINE:24
def delete(id:Long) = {
   Call("POST", "/components/" + implicitly[PathBindable[Long]].unbind("id", id) + "/delete")
}
                                                        
 
// @LINE:16
def create() = {
   Call("GET", "/components/new")
}
                                                        
 
// @LINE:9
def list(p:Int = 0, s:String = "name", o:String = "asc", f:String = "") = {
   Call("GET", "/pumpingstations" + queryString(List(if(p == 0) None else Some(implicitly[QueryStringBindable[Int]].unbind("p", p)), if(s == "name") None else Some(implicitly[QueryStringBindable[String]].unbind("s", s)), if(o == "asc") None else Some(implicitly[QueryStringBindable[String]].unbind("o", o)), if(f == "") None else Some(implicitly[QueryStringBindable[String]].unbind("f", f)))))
}
                                                        
 
// @LINE:13
def psareas(p:Int = 0, s:String = "name", o:String = "asc", f:String = "", we:Int = 25, wcctv:Int = 25, wo:Int = 25, ws:Int = 25) = {
   Call("GET", "/psareas" + queryString(List(if(p == 0) None else Some(implicitly[QueryStringBindable[Int]].unbind("p", p)), if(s == "name") None else Some(implicitly[QueryStringBindable[String]].unbind("s", s)), if(o == "asc") None else Some(implicitly[QueryStringBindable[String]].unbind("o", o)), if(f == "") None else Some(implicitly[QueryStringBindable[String]].unbind("f", f)), if(we == 25) None else Some(implicitly[QueryStringBindable[Int]].unbind("we", we)), if(wcctv == 25) None else Some(implicitly[QueryStringBindable[Int]].unbind("wcctv", wcctv)), if(wo == 25) None else Some(implicitly[QueryStringBindable[Int]].unbind("wo", wo)), if(ws == 25) None else Some(implicitly[QueryStringBindable[Int]].unbind("ws", ws)))))
}
                                                        
 
// @LINE:21
def update(id:Long) = {
   Call("POST", "/components/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                        
 
// @LINE:17
def save() = {
   Call("POST", "/components")
}
                                                        
 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        
 
// @LINE:11
def listManholes(p:Int = 0, s:String = "name", o:String = "asc", f:String = "") = {
   Call("GET", "/manholes" + queryString(List(if(p == 0) None else Some(implicitly[QueryStringBindable[Int]].unbind("p", p)), if(s == "name") None else Some(implicitly[QueryStringBindable[String]].unbind("s", s)), if(o == "asc") None else Some(implicitly[QueryStringBindable[String]].unbind("o", o)), if(f == "") None else Some(implicitly[QueryStringBindable[String]].unbind("f", f)))))
}
                                                        
 
// @LINE:20
def edit(id:Long) = {
   Call("GET", "/components/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                        

                      
    
}
                            

// @LINE:27
class ReverseAssets {
    


 
// @LINE:27
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            
}
                    


// @LINE:27
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:10
def listPipes = JavascriptReverseRoute(
   "controllers.Application.listPipes",
   """
      function(p,s,o,f) {
      return _wA({method:"GET", url:"/pipes" + _qS([(p == null ? """ +  implicitly[JavascriptLitteral[Int]].to(0) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("p", p)), (s == null ? """ +  implicitly[JavascriptLitteral[String]].to("name") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("s", s)), (o == null ? """ +  implicitly[JavascriptLitteral[String]].to("asc") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("o", o)), (f == null ? """ +  implicitly[JavascriptLitteral[String]].to("") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("f", f))])})
      }
   """
)
                                                        
 
// @LINE:24
def delete = JavascriptReverseRoute(
   "controllers.Application.delete",
   """
      function(id) {
      return _wA({method:"POST", url:"/components/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/delete"})
      }
   """
)
                                                        
 
// @LINE:16
def create = JavascriptReverseRoute(
   "controllers.Application.create",
   """
      function() {
      return _wA({method:"GET", url:"/components/new"})
      }
   """
)
                                                        
 
// @LINE:9
def list = JavascriptReverseRoute(
   "controllers.Application.list",
   """
      function(p,s,o,f) {
      return _wA({method:"GET", url:"/pumpingstations" + _qS([(p == null ? """ +  implicitly[JavascriptLitteral[Int]].to(0) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("p", p)), (s == null ? """ +  implicitly[JavascriptLitteral[String]].to("name") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("s", s)), (o == null ? """ +  implicitly[JavascriptLitteral[String]].to("asc") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("o", o)), (f == null ? """ +  implicitly[JavascriptLitteral[String]].to("") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("f", f))])})
      }
   """
)
                                                        
 
// @LINE:13
def psareas = JavascriptReverseRoute(
   "controllers.Application.psareas",
   """
      function(p,s,o,f,we,wcctv,wo,ws) {
      return _wA({method:"GET", url:"/psareas" + _qS([(p == null ? """ +  implicitly[JavascriptLitteral[Int]].to(0) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("p", p)), (s == null ? """ +  implicitly[JavascriptLitteral[String]].to("name") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("s", s)), (o == null ? """ +  implicitly[JavascriptLitteral[String]].to("asc") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("o", o)), (f == null ? """ +  implicitly[JavascriptLitteral[String]].to("") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("f", f)), (we == null ? """ +  implicitly[JavascriptLitteral[Int]].to(25) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("we", we)), (wcctv == null ? """ +  implicitly[JavascriptLitteral[Int]].to(25) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("wcctv", wcctv)), (wo == null ? """ +  implicitly[JavascriptLitteral[Int]].to(25) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("wo", wo)), (ws == null ? """ +  implicitly[JavascriptLitteral[Int]].to(25) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("ws", ws))])})
      }
   """
)
                                                        
 
// @LINE:21
def update = JavascriptReverseRoute(
   "controllers.Application.update",
   """
      function(id) {
      return _wA({method:"POST", url:"/components/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                                                        
 
// @LINE:17
def save = JavascriptReverseRoute(
   "controllers.Application.save",
   """
      function() {
      return _wA({method:"POST", url:"/components"})
      }
   """
)
                                                        
 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        
 
// @LINE:11
def listManholes = JavascriptReverseRoute(
   "controllers.Application.listManholes",
   """
      function(p,s,o,f) {
      return _wA({method:"GET", url:"/manholes" + _qS([(p == null ? """ +  implicitly[JavascriptLitteral[Int]].to(0) + """ : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("p", p)), (s == null ? """ +  implicitly[JavascriptLitteral[String]].to("name") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("s", s)), (o == null ? """ +  implicitly[JavascriptLitteral[String]].to("asc") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("o", o)), (f == null ? """ +  implicitly[JavascriptLitteral[String]].to("") + """ : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("f", f))])})
      }
   """
)
                                                        
 
// @LINE:20
def edit = JavascriptReverseRoute(
   "controllers.Application.edit",
   """
      function(id) {
      return _wA({method:"GET", url:"/components/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:27
class ReverseAssets {
    


 
// @LINE:27
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:27
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.ref {

// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:13
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    


 
// @LINE:10
def listPipes(p:Int, s:String, o:String, f:String) = new play.api.mvc.HandlerRef(
   controllers.Application.listPipes(p, s, o, f), HandlerDef(this, "controllers.Application", "listPipes", Seq(classOf[Int], classOf[String], classOf[String], classOf[String]))
)
                              
 
// @LINE:24
def delete(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Application.delete(id), HandlerDef(this, "controllers.Application", "delete", Seq(classOf[Long]))
)
                              
 
// @LINE:16
def create() = new play.api.mvc.HandlerRef(
   controllers.Application.create(), HandlerDef(this, "controllers.Application", "create", Seq())
)
                              
 
// @LINE:9
def list(p:Int, s:String, o:String, f:String) = new play.api.mvc.HandlerRef(
   controllers.Application.list(p, s, o, f), HandlerDef(this, "controllers.Application", "list", Seq(classOf[Int], classOf[String], classOf[String], classOf[String]))
)
                              
 
// @LINE:13
def psareas(p:Int, s:String, o:String, f:String, we:Int, wcctv:Int, wo:Int, ws:Int) = new play.api.mvc.HandlerRef(
   controllers.Application.psareas(p, s, o, f, we, wcctv, wo, ws), HandlerDef(this, "controllers.Application", "psareas", Seq(classOf[Int], classOf[String], classOf[String], classOf[String], classOf[Int], classOf[Int], classOf[Int], classOf[Int]))
)
                              
 
// @LINE:21
def update(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Application.update(id), HandlerDef(this, "controllers.Application", "update", Seq(classOf[Long]))
)
                              
 
// @LINE:17
def save() = new play.api.mvc.HandlerRef(
   controllers.Application.save(), HandlerDef(this, "controllers.Application", "save", Seq())
)
                              
 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              
 
// @LINE:11
def listManholes(p:Int, s:String, o:String, f:String) = new play.api.mvc.HandlerRef(
   controllers.Application.listManholes(p, s, o, f), HandlerDef(this, "controllers.Application", "listManholes", Seq(classOf[Int], classOf[String], classOf[String], classOf[String]))
)
                              
 
// @LINE:20
def edit(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Application.edit(id), HandlerDef(this, "controllers.Application", "edit", Seq(classOf[Long]))
)
                              

                      
    
}
                            

// @LINE:27
class ReverseAssets {
    


 
// @LINE:27
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            
}
                    
                