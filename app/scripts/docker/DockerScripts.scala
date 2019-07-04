package scripts.docker

object DockerScripts {
  import ammonite.ops._

  def createDockerImage(className: String): Unit = {
    //docker build -t "kakoon:$className DockerfilePath"
    %("docker", "build", "-t", s"kakoon:$className", "target/docker/stage")(pwd)

  }
}