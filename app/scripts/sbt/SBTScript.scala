package scripts.sbt

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try
import config.Config.SBT

object SBTScript {
  import ammonite.ops._
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def compileTargetClass(clazz: String): Future[Try[CommandResult]] = {
    //sbt compile
    Future(Try(%%(SBT, "compile")(pwd)))
  }

}
