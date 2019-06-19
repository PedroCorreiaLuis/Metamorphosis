package api.controllers

import akka.actor.ActorSystem
import api.dtos.DSLDTO
import javax.inject.{ Inject, Singleton }
import play.api.inject.Module
import output.ObjectGenerator
import play.api.libs.json.{ JsError, JsValue, Json }
import play.api.mvc.{ AbstractController, Action, ControllerComponents }
import docker.Docker._

import scala.concurrent.{ ExecutionContext, Future }
@Singleton
class DSLController @Inject() (cc: ControllerComponents, actorSystem: ActorSystem) extends AbstractController(cc) {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def compute: Action[JsValue] = Action(parse.json).async { request =>

    val dslResult = request.body.validate[DSLDTO]

    val dummyData = List(1, 3, 4, 2, 5, 7, 2, 123, 654, 3, 35, 312, 64, 76, 9)

    dslResult.fold(
      errors => {
        print(errors)
        Future.successful { BadRequest(Json.obj("Invalid inputs" -> JsError.toJson(errors))) }
      },
      dsl => {
        val objGen = new ObjectGenerator[Int](dsl, dummyData)
        val path = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\app\\output"
        objGen.generate(path, "POC")
        runDockerCommands.map(_ => Ok(Json.toJson(dsl)))
      })
  }

}