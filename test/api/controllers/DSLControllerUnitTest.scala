package api.controllers

import java.io.File
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.util.Timeout
import play.api.Mode
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.ControllerComponents
import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach }
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import repository.TestGenerator
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

//TODO make fake docker scripts
class DSLControllerUnitTest extends PlaySpec with GuiceOneAppPerSuite with BeforeAndAfterAll with BeforeAndAfterEach with Results {

  val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().in(Mode.Test)

  lazy val injector: Injector = appBuilder.injector()

  lazy val cc: ControllerComponents = injector.instanceOf[ControllerComponents]
  val actorSystem: ActorSystem = injector.instanceOf[ActorSystem]
  lazy implicit private val mat: Materializer = injector.instanceOf[Materializer]

  val fakeGenerator = new TestGenerator

  "DSLController #compute" should {
    "send a OK if JSON Body is correct" in {
      val controller = new DSLController(fakeGenerator, cc, actorSystem)
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
                                           "transformation": "sum"
                                       }
                                   ]
                               }""")))

      status(result)(Timeout(120, TimeUnit.SECONDS)) mustBe OK
    }
  }

  "DSLController #compute" should {
    "send a BadRequest if JSON Body is incorrect" in {
      val controller = new DSLController(fakeGenerator, cc, actorSystem)
      val result = controller.compute.apply(FakeRequest(POST, "/compute").withHeaders(CONTENT_TYPE -> JSON, HOST -> "localhost:9000"))

      status(result) mustBe BAD_REQUEST
    }
  }

}