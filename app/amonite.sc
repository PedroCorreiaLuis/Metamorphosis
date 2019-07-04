import ammonite.ops._

//sbt scripts.docker:publishLocal
%%("C:\\Program Files (x86)\\sbt\\bin\\sbt.bat", "scripts.docker:publishLocal")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))


//scripts.docker run --rm metamorphosis:0.1
%%("scripts/docker", "run", "--rm","metamorphosis:0.1")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))


//scripts.docker run -d -p 9000:9000 repo:tag
//%%("scripts.docker", "run", "-d","-p","9000:9000","metamorphosis:0.1")(Path("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis"))
