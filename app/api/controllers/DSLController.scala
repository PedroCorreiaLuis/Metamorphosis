package api.controllers

import akka.actor.ActorSystem
import api.dtos.DSLDTO
import javax.inject.Inject
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc.{ AbstractController, Action, ControllerComponents }

import scala.concurrent.{ ExecutionContext, Future }

class DSLController @Inject() (cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def compute: Action[JsValue] = Action(parse.json).async { request =>

    val dslResult = request.body.validate[DSLDTO]

    dslResult.fold(
      errors =>
        Future.successful { BadRequest("error") },
      dsl => {
        // parse class
        // try to run class?
        // run class through docker
        Future(Ok(Json.toJson(dsl)))
      })
  }

}