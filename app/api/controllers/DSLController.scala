package api.controllers

import java.io.File

import akka.actor.ActorSystem
import api.dtos.DSLDTO
import docker.Docker._
import javax.inject.{ Inject, Singleton }
import output.{ CodeGenerator, ObjectGenerator }
import play.api.libs.json.{ JsError, JsValue, Json }
import play.api.mvc.{ AbstractController, Action, ControllerComponents }
import config.Config.OutputPath
import ammonite.ops._

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }
@Singleton
class DSLController @Inject() (codeGenerator: CodeGenerator, cc: ControllerComponents, actorSystem: ActorSystem) extends AbstractController(cc) {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def compute: Action[JsValue] = Action(parse.json).async { request =>

    val dslResult = request.body.validate[DSLDTO]

    dslResult.fold(
      errors => {
        print(errors)
        Future.successful { BadRequest(Json.obj("Invalid inputs" -> JsError.toJson(errors))) }
      },
      dsl => {
        codeGenerator.generate(dsl, OutputPath, "POC").flatMap { path =>
          runDockerCommands match {
            case (cmd1, cmd2) => cmd1.flatMap({
              case Success(_) => cmd2.map({
                case Success(_) => Ok(Json.toJson(dsl))
                case Failure(exception) =>
                  new File(path).delete()
                  BadRequest(exception.toString)
              })
              case Failure(exception) =>
                new File(path).delete()
                Future.successful(BadRequest(exception.toString))
            })
          }
        }
      })
  }

}