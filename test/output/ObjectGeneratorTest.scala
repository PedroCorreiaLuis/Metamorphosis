package output

import java.io.File

import api.dtos.{ DSLDTO, TransformationDTO, TypeDTO }
import org.scalatest._
import typeLoader.PathClassLoader
import validations.Errors.ClassNotFoundException

class ObjectGeneratorTest extends WordSpec with BeforeAndAfterAll with BeforeAndAfterEach with Matchers {

  private val inType = TypeDTO("Composed", Some("List[Int]"))
  private val outType = TypeDTO("Simple", Some("Int"))

  private val transformations = Seq(TransformationDTO(Some("map"), Some("p => p + 2")), TransformationDTO(Some("size"), None))

  private val dsl = DSLDTO(inType, outType, transformations)
  private val path = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\test\\output"
  private val objectName = "GeneratedTest"

  override def afterAll(): Unit = {
    new File(path + "\\" + objectName + ".scala").delete()
  }

  "Object Generator" should {
    "correctly generate an Object in the target path" in {

      val data: Seq[Int] = Seq(1, 2, 3)

      val objGen = new ObjectGenerator[Int](dsl, data)
      val classLoader = new PathClassLoader(path)

      objGen.generate(path, objectName)

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