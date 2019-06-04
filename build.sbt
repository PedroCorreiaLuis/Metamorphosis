name := "Metamorphosis"

version := "0.1"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(JavaAppPackaging).enablePlugins(DockerPlugin).enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("output.POC")

dockerBaseImage := "openjdk:jre-alpine"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.scalatestplus.play"  %% "scalatestplus-play" % "3.1.2",
  "org.scala-lang" % "scala-compiler" % "2.12.8",
  "com.chuusai" %% "shapeless" % "2.3.3"
)