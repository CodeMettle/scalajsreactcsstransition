package reactcss.client

import japgolly.scalajs.react.Addons.ReactCssTransitionGroup
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

case class ReactTodoItem(itemText: String, index: Int)

object ReactTodoList {
  case class Props(items: Seq[ReactTodoItem] = Seq.empty, itemDeleted: (Int) ⇒ Unit)

  val component = ReactComponentB[Props]("ReactTodoList")
    .stateless
    .noBackend
    .render((P, _, _) ⇒ {
      def createItem(item: ReactTodoItem): ReactNode = {
        <.li(^.key := s"${item.index}")(
          <.button(^.onClick --> P.itemDeleted(item.index))("x"),
          <.span(" ", item.itemText)
        )
      }

      val items = P.items map createItem


      // This works!
      //MyCustomCssTransitionGroup("example", true, true, "ol")(items: _*)


      // Scala fails compling; "not found: value items"; happens when any parameter is passed by name
      //MyCustomCssTransitionGroup("example", component = "ol")(items: _*)


      // Unminified react complains in the console about this, on 0.12.2 it renders, on 0.13.1 it blows up with
      //   "Cannot read property '__reactAutoBindMap' of undefined"
      //ReactCssTransitionGroup("example", true, true, "ol")(items: _*)


      // Scala fails compiling; "not found: value items"; happens when any parameter is passed by name
      ReactCssTransitionGroup("example", component = "ol")(items: _*)
    })
    .build

  def apply(items: Seq[String], itemDeleted: (Int) ⇒ Unit) = {
    component(Props(items.zipWithIndex map ReactTodoItem.tupled, itemDeleted))
  }
}

object ReactTodoApp {
  case class State(items: Seq[String], text: String)

  class Backend(t: BackendScope[Unit, State]) {
    def onChange(e: ReactEventI) = {
      t.modState(_.copy(text = e.target.value))
    }

    def onDeleteClicked(idx: Int): Unit = {
      t.modState(s ⇒ {
        val (f, b) = s.items splitAt idx
        val newItems = if (b.size > 1)
          f ++ b.tail
        else
          f

        s.copy(items = newItems)
      })
    }

    def handleSubmit(e: ReactEvent) = {
      e.preventDefault()
      val nextItems = t.state.items :+ t.state.text.trim
      val nextText = ""
      t.modState(_.copy(items = nextItems, text = nextText))
    }

    def handleClick() = {
      val nextItems = t.state.items :+ t.state.text.trim
      val nextText = ""
      t.modState(_.copy(items = nextItems, text = nextText))
    }
  }

  val component = ReactComponentB[Unit]("ReactTodoApp")
    .initialState(State(Seq("item1", "item2"), ""))
    .backend(new Backend(_))
    .render((_, S, B) ⇒ {
      <.div(
        <.h3("TODO"),
        ReactTodoList(S.items, B.onDeleteClicked),
        <.form(^.onSubmit ==> B.handleSubmit)(
          <.input(^.onChange ==> B.onChange, ^.value := S.text),
          <.button(^.disabled := S.text.trim.isEmpty)(s"Add #${S.items.size + 1}")
        )
      )
    })
    .buildU

  def apply() = component()
}
