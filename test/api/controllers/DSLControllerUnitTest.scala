package api.controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.Mode
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.ControllerComponents
import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach }
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

class DSLControllerUnitTest extends PlaySpec with GuiceOneAppPerSuite with BeforeAndAfterAll with BeforeAndAfterEach with Results {

  val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().in(Mode.Test)

  lazy val injector: Injector = appBuilder.injector()

  lazy val cc: ControllerComponents = injector.instanceOf[ControllerComponents]
  val actorSystem: ActorSystem = injector.instanceOf[ActorSystem]
  lazy implicit private val mat: Materializer = injector.instanceOf[Materializer]

  val dsl = new DSLController(cc, actorSystem)

  "DSLController #compute" should {
    "send a OK if JSON Body is correct" in {
      val controller = new DSLController(cc, actorSystem)
      val result = controller.compute.apply(FakeRequest(POST, "/compute")
        .withHeaders(CONTENT_TYPE -> JSON, HOST -> "localhost:9000")
        .withBody(Json.parse("""{
                                   "inType": {
                                       "categoryName": "Composed",
                                       "type": "List[Int]"
                                   },
                                   "outType": {
                                       "categoryName": "Simple",
                                       "type": "Int"
                                   },
                                   "transformations": [
                                       {
                                           "transformation": "filter",
                                           "predicate": "p=> p >5"
                                       },
                                       {
                                           "transformation": "size"
                                       }
                                   ]
                               }""")))

      status(result) mustBe OK
    }
  }

  "DSLController #compute" should {
    "send a BadRequest if JSON Body is incorrect" in {
      val controller = new DSLController(cc, actorSystem)
      val result = controller.compute.apply(FakeRequest(POST, "/compute").withHeaders(CONTENT_TYPE -> JSON, HOST -> "localhost:9000"))

      status(result) mustBe BAD_REQUEST
    }
  }

}