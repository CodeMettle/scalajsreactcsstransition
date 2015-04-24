package reactcss.client

import japgolly.scalajs.react.React
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object ReactCssClient extends JSApp  {

  @JSExport
  override def main(): Unit = {
    React.render(ReactTodoApp(), dom.document.body)
  }
}
