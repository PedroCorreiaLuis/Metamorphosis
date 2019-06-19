import ammonite.ops._

//sbt docker:publishLocal
%%("C:\\Program Files (x86)\\sbt\\bin\\sbt.bat", "docker:publishLocal")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))


//docker run --rm metamorphosis:0.1
%%("docker", "run", "--rm","metamorphosis:0.1")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))


//docker run -d -p 9000:9000 repo:tag
//%%("docker", "run", "-d","-p","9000:9000","metamorphosis:0.1")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))
