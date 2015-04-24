package reactcss.client

import japgolly.scalajs.react.{React, ReactNode}

import scala.scalajs.js

object MyCustomCssTransitionGroup {
  def apply(transitionName: String, enter: js.UndefOr[Boolean] = js.undefined,
            leave: js.UndefOr[Boolean] = js.undefined, component: js.UndefOr[String] = js.undefined)
           (children: ReactNode*) = {

    val props = js.Dynamic.literal("transitionName" → transitionName)
    enter    .foreach(v => props.updateDynamic("transitionEnter")(v))
    leave    .foreach(v => props.updateDynamic("transitionLeave")(v))
    component.foreach(v => props.updateDynamic("component")(v))

    MyCustomReact.createElement(React.addons.CSSTransitionGroup, props, children)
  }
}
