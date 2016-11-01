package lib

import org.http4s.headers.`Content-Type`
import org.http4s.{EntityEncoder, MediaType}

import scalatags.Text._

trait AppController {
  implicit val scalatagsHtmlEncoder: EntityEncoder[Modifier] =
    EntityEncoder[String]
      .contramap[Modifier] { htmlFrag =>
        htmlFrag.toString
      }
      .withContentType(`Content-Type`(MediaType.`text/html`))
}
