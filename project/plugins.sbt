// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"

// Sbt plugins
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.2")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")
