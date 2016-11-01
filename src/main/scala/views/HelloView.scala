package views

import scalatags.Text.implicits._
import scalatags.Text.{Modifier, attrs => A, tags => H}

object HelloView {
  def apply(name: String): Modifier = {
    LayoutView(
      "Hello",
      H.p(A.`class` := "content")(s"Hello $name!")
    )
  }
}
