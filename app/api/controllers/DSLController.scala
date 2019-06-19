package api.controllers

import akka.actor.ActorSystem
import api.dtos.DSLDTO
import docker.Docker._
import javax.inject.{ Inject, Singleton }
import output.ObjectGenerator
import play.api.libs.json.{ JsError, JsValue, Json }
import play.api.mvc.{ AbstractController, Action, ControllerComponents }
import config.Config.OutputPath

import scala.concurrent.{ ExecutionContext, Future }
@Singleton
class DSLController @Inject() (cc: ControllerComponents, actorSystem: ActorSystem) extends AbstractController(cc) {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def compute: Action[JsValue] = Action(parse.json).async { request =>

    val dslResult = request.body.validate[DSLDTO]

    dslResult.fold(
      errors => {
        print(errors)
        Future.successful { BadRequest(Json.obj("Invalid inputs" -> JsError.toJson(errors))) }
      },
      dsl => {
        val objGen = new ObjectGenerator(dsl)
        objGen.generate(OutputPath, "POC")
        runDockerCommands.map(_ => Ok(Json.toJson(dsl)))
      })
  }

}