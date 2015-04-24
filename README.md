## ReactCssTransitionGroup issues

Run with `sbt run`; browse to `http://localhost:9000`

`ReactCssTransitionGroup(name)` makes React complain about calling a component directly, on 0.12.2 it still works, on 0.13.1 it blows up (switch commented-out dependency at top of build.sbt):

```
val jsCompileDependencies = Seq(
  "org.webjars" % "react" % "0.13.1" / "react-with-addons.js" commonJSName "React"
  //"org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React"
)
```

~~Passing an argument by name to `ReactCssTransitionGroup` or `MyCustomCssTransitionGroup` causes scalac to fail with~~

```
[error] /Users/steven/dev/reactcss/client/src/main/scala/reactcss/client/ReactTodoApp.scala:35: not found: value items
[error]       ReactCssTransitionGroup("example", component = "ol")(items: _*)
[error]                                                            ^
[error] one error found
[error] (client/compile:compileIncremental) Compilation failed
```

### The above is an IntelliJ IDEA bug

(Switch between the 4 commented lines in `ReactTodoApp.scala`):

```scala
      // This works!
      //MyCustomCssTransitionGroup("example", true, true, "ol")(items: _*)


      // Scala fails compling; "not found: value items"; happens when any parameter is passed by name
      //MyCustomCssTransitionGroup("example", component = "ol")(items: _*)


      // Unminified react complains in the console about this, on 0.12.2 it renders, on 0.13.1 it blows up with
      //   "Cannot read property '__reactAutoBindMap' of undefined"
      //ReactCssTransitionGroup("example", true, true, "ol")(items: _*)


      // Scala fails compiling; "not found: value items"; happens when any parameter is passed by name
      ReactCssTransitionGroup("example", component = "ol")(items: _*)

```
