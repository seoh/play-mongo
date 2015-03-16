package controllers

import play.api._
import play.api.mvc._

/**
 * withHeaders(tuple*) => Result
 * tuple = (HeaderName, value)
 * https://www.playframework.com/documentation/2.3.x/api/scala/index.html#play.api.http.HeaderNames
 * https://www.playframework.com/documentation/2.3.x/ScalaRouting
 */
object Application extends Controller {

  def clean = Action {
    Database.drop
    Redirect("/")
  }

  def list = Action { implicit request =>
    val (result, key) = 
      (request2flash.get("success"), request2flash.get("fail")) match {
        case (Some(key), _) => (true, key)
        case (_, Some(kv)) => (false, kv)
        case (None, None)  => (true, "") // index
      }

    val items = Database.list
    Ok(
      "<script src=\"" + routes.Assets.at("javascripts/hello.js") + "\"></script>" +
      "<h3>" + items.length + "items</h3>\n" +
      "<ol>\n" + 
        items.map { item =>
          "  <li>%s -> %s</li>".format(item.get("Key"), item.get("Value"))
        }.mkString("\n") + 
      "\n</ol>" +
      s"""<script>flash($result, "$key")</script>""").as("text/html")
  }

  def read(key: String) = Action {
    Redirect("/").flashing(
      Database.find(key) match {
        case Some(m) => "success" -> key
        case None    => "fail" -> "%s is not found".format(key)
      }
    )
  }

  def update(key: String, value: String) = Action {
    Redirect("/").flashing(
      Database.update(key, value) match {
        case 1 => "success" -> key
        case _ => "fail" -> "%s -> %s".format(key, value)
      }
    )
  }
}