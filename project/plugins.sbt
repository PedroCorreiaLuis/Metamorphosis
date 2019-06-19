addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.20")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.1")
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.23")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.0-M7"