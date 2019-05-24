name := "Metamorphosis"

version := "0.1"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.scalatestplus.play"  %% "scalatestplus-play" % "3.1.2",
  "org.scala-lang" % "scala-compiler" % "2.12.8"
)