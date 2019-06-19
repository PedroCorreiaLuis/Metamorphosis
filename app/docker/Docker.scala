package docker

import scala.concurrent.{ ExecutionContext, Future }

object Docker {
  import ammonite.ops._

  def runDockerCommands: Future[CommandResult] = {
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

    //sbt docker:publishLocal
    %%("C:\\Program Files (x86)\\sbt\\bin\\sbt.bat", "docker:publishLocal")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))

    //docker run --rm metamorphosis:0.1
    Future(%%("docker", "run", "--rm", "metamorphosis:0.1")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis")))

  }
}
