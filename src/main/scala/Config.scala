import scala.util.Properties.envOrNone
import scalaz.concurrent.Task

case class Config(
    port: Int,
    marvelPublicKey: String,
    marvelPrivateKey: String
)

object Config {
  def fromEnv(): Task[Config] = {
    val port = envOrNone("PORT").map(_.toInt).getOrElse(3000)

    for {
      marvelPublicKey <- envOrFail("MARVEL_PUBLIC_KEY")
      marvelPrivateKey <- envOrFail("MARVEL_PRIVATE_KEY")
    } yield
      Config(
        port = port,
        marvelPublicKey = marvelPublicKey,
        marvelPrivateKey = marvelPrivateKey
      )
  }

  private def envOrFail(key: String): Task[String] = {
    envOrNone(key) match {
      case Some(value) => Task.now(value)
      case None =>
        Task.fail(new Exception(s"Environment variable not set $key"))
    }
  }
}
