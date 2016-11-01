import controllers.HelloController
import org.http4s.HttpService
import org.http4s.dsl._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}
import org.log4s.getLogger

import scalaz.concurrent.Task

object Main extends ServerApp {
  private val logger = getLogger

  val routes = HttpService {
    case r @ GET -> Root / "hello" / name => HelloController.index(name)(r)
  }

  val service: HttpService = routes.local { req =>
    logger.info(s"${req.remoteAddr
      .getOrElse("null")} -> ${req.method}: ${req.uri.path} ${req.uri.query}")
    req
  }

  override def server(args: List[String]): Task[Server] = {
    for {
      config <- Config.fromEnv()
      server <- BlazeBuilder
        .bindHttp(config.port, "localhost")
        .mountService(service)
        .start
    } yield server
  }
}
