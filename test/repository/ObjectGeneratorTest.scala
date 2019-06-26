package repository

import java.io.File

import api.dtos.{ DSLDTO, TransformationDTO, TypeDTO }
import org.scalatest._
import typeLoader.PathClassLoader
import validations.Errors.ClassNotFoundException
import validations.api.Transformations._

class ObjectGeneratorTest extends WordSpec with BeforeAndAfterAll with BeforeAndAfterEach with Matchers {

  private val inType = TypeDTO("Composed", Some("List[Int]"))
  private val outType = TypeDTO("Simple", Some("Int"))

  private val transformations = Seq(TransformationDTO(Map, Some("p => p + 2")), TransformationDTO(Size, None))

  private val dsl = DSLDTO(inType, outType, transformations)
  private val path = "..\\Metamorphosis\\test\\output"
  private val objectName = "GeneratedTest"

  override def afterAll(): Unit = {
    new File(path + "\\" + objectName + ".scala").delete()
  }

  "Object Generator" should {
    "correctly generate an Object in the target path" in {

      val objGen = new ObjectGenerator()
      val classLoader = new PathClassLoader(path)

      objGen.generate(dsl, path, objectName)

      def recursiveAssertion: Assertion = {
        classLoader.getclazz(objectName) match {
          case Right(_) => Succeeded
          case Left(ClassNotFoundException) => recursiveAssertion
          case _ => fail()
        }

        recursiveAssertion

      }
    }
  }
}