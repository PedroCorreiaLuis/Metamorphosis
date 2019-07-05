package api.controllers

import java.io.File

import akka.actor.ActorSystem
import api.dtos.{ ComputeDTO, DSLDTO }
import config.Config.OutputPath
import javax.inject.{ Inject, Singleton }
import play.api.libs.json.{ JsError, JsValue, Json }
import play.api.mvc.{ AbstractController, Action, ControllerComponents }
import repository.generators.CodeGenerator
import repository.generators.ScriptGenerator._
import scripts.docker.DockerScripts._
import scripts.sbt.SBTScript._

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
        codeGenerator.generate(dsl, OutputPath).flatMap {
          {
            case (className, path) =>

              compileTargetClass(className).map {
                {
                  case Success(_) =>
                    generate("C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\target\\docker\\stage\\opt\\docker\\bin\\metamorphosis", className)
                    createDockerImage(className)
                    //Delete class after build?
                    //new File(path).delete()
                    Ok(Json.toJson(ComputeDTO("kakoon", className, dsl)))

                  case Failure(exception) =>
                    new File(path).delete()
                    BadRequest(exception.toString)
                }
              }
          }
        }
      })
  }

}