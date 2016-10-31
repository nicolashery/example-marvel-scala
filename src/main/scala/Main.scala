import org.http4s.{EntityEncoder, HttpService, MediaType, Service}
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.headers.`Content-Type`

import scala.util.Properties.envOrNone
import scalatags.Text.{Modifier, attrs => A, tags => H, tags2 => H2}
import scalatags.Text.implicits._
import scalaz.concurrent.Task

case class Config(
  port: Int,
  marvelPublicKey: String,
  marvelPrivateKey: String
)

object Config {
  def fromEnv(): Task[Config] = {
    val port = envOrNone("PORT")
      .map(_.toInt)
      .getOrElse(3000)

    for {
      marvelPublicKey <- envOrFail("MARVEL_PUBLIC_KEY")
      marvelPrivateKey <- envOrFail("MARVEL_PRIVATE_KEY")
    } yield Config(
      port = port,
      marvelPublicKey = marvelPublicKey,
      marvelPrivateKey = marvelPrivateKey
    )
  }

  private def envOrFail(key: String): Task[String] = {
    envOrNone(key) match {
      case Some(value) => Task.now(value)
      case None => Task.fail(new Exception(s"Environment variable not set $key"))
    }
  }
}

object Main extends ServerApp {
  implicit val scalatagsHtmlEncoder: EntityEncoder[Modifier] =
    EntityEncoder[String].contramap[Modifier] { htmlFrag =>
      htmlFrag.toString
    }.withContentType(`Content-Type`(MediaType.`text/html`))

  def layoutView(pageTitle: String, bodyContent: Modifier): Modifier = {
    H.html(
      H.head(
        H2.title(pageTitle)
      ),
      H.body(
        bodyContent
      )
    )
  }

  def helloView(name: String): Modifier = {
    layoutView(
      "Hello",
      H.p(A.`class` := "content")(s"Hello $name!")
    )
  }

  def helloService(name: String): HttpService = Service.lift { req =>
    Ok(helloView(name))
  }

  val service = HttpService {
    case r @ GET -> Root / "hello" / name => helloService(name)(r)
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
