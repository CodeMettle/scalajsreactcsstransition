val jsCompileDependencies = Seq(
  "org.webjars" % "react" % "0.13.1" / "react-with-addons.js" commonJSName "React"
  //"org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React"
)

val commonSettings = Seq[Setting[_]](
  organization := "reactcss",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.6",
  scalacOptions := Seq("-Xlint", "-unchecked", "-deprecation", "-feature")
)

lazy val server = (project in file("server")).settings(commonSettings ++ Seq(
  name := "server",
  scalaJSProjects := Seq(client),
  pipelineStages := Seq(scalaJSProd, gzip),
  LessKeys.compress in Assets := true,
  includeFilter in (Assets, LessKeys.less) := "*.less",
  libraryDependencies ++= Seq(
    "org.webjars" %% "webjars-play" % "2.3.0-3",
    "com.vmunier" %% "play-scalajs-scripts" % "0.1.0"
  )): _*).
  enablePlugins(PlayScala).
  aggregate(client)

lazy val client = (project in file("client")).settings(commonSettings ++ Seq(
  name := "client",
  persistLauncher := true,
  persistLauncher in Test := false,
  unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0",
    "com.github.japgolly.scalajs-react" %%% "core" % "0.8.3",
    "com.github.japgolly.scalajs-react" %%% "extra" % "0.8.3"
  ),
  jsDependencies := jsCompileDependencies,
  skip in packageJSDependencies := false): _*).
  enablePlugins(ScalaJSPlugin, ScalaJSPlay)

// loads the jvm project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
