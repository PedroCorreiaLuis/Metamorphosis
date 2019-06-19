name := "Metamorphosis"

version := "0.1"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(JavaAppPackaging).enablePlugins(DockerPlugin).enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("POC")


dockerBaseImage := "openjdk:jre-alpine"
dockerExposedPorts := Seq(9000)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.scalatestplus.play"  %% "scalatestplus-play" % "3.1.2",
  "org.scala-lang" % "scala-compiler" % "2.12.8",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "com.lihaoyi" %% "ammonite-ops" % "1.6.8",
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)
