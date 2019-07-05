package repository

import java.io.File

import api.dtos.{ DSLDTO, TransformationDTO, TypeDTO }
import org.scalatest._
import typeLoader.PathClassLoader
import Errors.ClassNotFoundException
import api.validations.Transformations._
import repository.generators.ObjectGenerator

import scala.concurrent.ExecutionContext

class ObjectGeneratorTest extends WordSpec with BeforeAndAfterAll with BeforeAndAfterEach with Matchers {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  private val inType = TypeDTO("Composed", Some("List[Int]"))
  private val outType = TypeDTO("Simple", Some("Int"))

  private val transformations = Seq(TransformationDTO(Map, 1, Some("p => p + 2")), TransformationDTO(Size, 2, None))

  private val dsl = DSLDTO(inType, outType, transformations)
  private val outputPath = "..\\Metamorphosis\\test\\output"

  "Object Generator" should {
    "correctly generate an Object in the target path" in {

      val objGen = new ObjectGenerator()
      val classLoader = new PathClassLoader(outputPath)

      objGen.generate(dsl, outputPath).map {
        {
          case (id, path) =>

            def recursiveAssertion: Assertion = {
              classLoader.getclazz(id.toString) match {
                case Right(_) => Succeeded
                case Left(ClassNotFoundException) => recursiveAssertion
                case _ =>
                  new File(path).delete()
                  fail()
              }

            }
            recursiveAssertion

        }
      }

    }
  }
}