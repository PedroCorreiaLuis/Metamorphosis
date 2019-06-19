package docker

import scala.concurrent.{ ExecutionContext, Future }
import config.Config.SBT

object Docker {
  import ammonite.ops._

  def runDockerCommands: Future[CommandResult] = {
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

    //sbt docker:publishLocal
    %%(SBT, "docker:publishLocal")(Path("..\\Metamorphosis"))

    //docker run --rm metamorphosis:0.1
    Future(%%("docker", "run", "--rm", "metamorphosis:0.1")(Path("..\\Metamorphosis")))

  }
}
