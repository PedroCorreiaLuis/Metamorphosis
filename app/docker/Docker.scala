package docker

import scala.concurrent.{ ExecutionContext, Future }
import config.Config.SBT

import scala.util.Try

object Docker {
  import ammonite.ops._

  def runDockerCommands: Future[Try[CommandResult]] = {
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

    //sbt docker:publishLocal
    val cmd = Future(Try(%%(SBT, "docker:publishLocal")(pwd)))

    //docker run --rm metamorphosis:0.1
    Future(%%("docker", "run", "--rm", "metamorphosis:0.1")(pwd))
    cmd
  }
}