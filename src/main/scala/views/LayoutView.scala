package views

import scalatags.Text.implicits._
import scalatags.Text.{Modifier, tags => H, tags2 => H2}

object LayoutView {
  def apply(pageTitle: String, bodyContent: Modifier): Modifier = {
    H.html(
      H.head(
        H2.title(pageTitle)
      ),
      H.body(
        bodyContent
      )
    )
  }
}
