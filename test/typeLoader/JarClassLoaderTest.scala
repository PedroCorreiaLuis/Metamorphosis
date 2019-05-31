package typeLoader

import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, BeforeAndAfterEach, Matchers}
import typeLoader.testJars.DummySimpleClass
import validations.Errors.classNotFoundException

class JarClassLoaderTest extends AsyncWordSpec with BeforeAndAfterAll with BeforeAndAfterEach with Matchers {

  val simpleClassPath = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\test\\typeLoader\\testJars\\typeLoader.testJars.DummySimpleClass.scala"
  val complexClassPath = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\test\\typeLoader\\testJars\\DummyComplexClass.scala"

  val simpleClassLoader: JarClassLoader = new JarClassLoader(simpleClassPath, List("typeLoader.testJars.DummySimpleClass"), Some(List("hello")))
  val dummySimpleClass: Class[_] = new DummySimpleClass().getClass.getClassLoader.loadClass("typeLoader.testJars.DummySimpleClass")

  "Class Loader " should {
    "be able to correctly load a class and their methods" in {
      assert(simpleClassLoader.clazzes.contains(Right(dummySimpleClass)))
      assert(simpleClassLoader.methods.contains(Right(dummySimpleClass.getMethod("hello"))))
    }
  }

  "Class Loader " should {
    "not be able to load a class that doesn´t exist" in {

      val simpleClassLoader: JarClassLoader = new JarClassLoader(simpleClassPath, List("FakeClass"))

      simpleClassLoader.clazzes.head match {
        case Left(exception) => exception shouldBe classNotFoundException
        case _ => fail()
      }
    }
  }

  "Class Loader " should {
    "be able to invoke loaded methods" in {

      val result = for {
        clazzes <- simpleClassLoader.clazzes.head
        methods <- simpleClassLoader.methods.head
      } yield simpleClassLoader.callMethod(methods, clazzes)

      result match {
        case Right(either) => either match {
          case Right(res) => res shouldBe new DummySimpleClass().hello()
          case _ => fail()
        }
        case _ => fail()
      }
    }
  }

}

