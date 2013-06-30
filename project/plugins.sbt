// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.1")

// Google Chrome automatic refresh
addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.3")

// addSbtPlugin("com.typesafe.startscript" % "xsbt-start-script-plugin" % "0.5.0")
