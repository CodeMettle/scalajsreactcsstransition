package reactcss.client

import japgolly.scalajs.react.{ReactComponentU_, ReactNode}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("React")
object MyCustomReact extends js.Object {
  def createElement(a: Any, props: js.Any, children: ReactNode*): ReactComponentU_ = js.native
}
