package controllers

import lib.AppController
import org.http4s.dsl._
import org.http4s.{HttpService, Service}
import views.HelloView

object HelloController extends AppController {
  def index(name: String): HttpService = Service.lift { req =>
    Ok(HelloView(name))
  }
}
