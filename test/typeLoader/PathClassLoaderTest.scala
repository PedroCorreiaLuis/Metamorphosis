package typeLoader

import java.lang.reflect.{ Constructor, Method }

import org.scalatest.{ AsyncWordSpec, BeforeAndAfterAll, BeforeAndAfterEach, Matchers }
import typeLoader.testJars.{ DummyComplexClass, DummySimpleClass }
import validations.Errors.ClassNotFoundException

class PathClassLoaderTest extends AsyncWordSpec with BeforeAndAfterAll with BeforeAndAfterEach with Matchers {

  private val simpleClassPath = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\test\\typeLoader\\testJars\\typeLoader.testJars.DummySimpleClass.scala"
  private val complexClassPath = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\test\\typeLoader\\testJars\\DummyComplexClass.scala"

  private val simplePathClassLoader: PathClassLoader = new PathClassLoader(simpleClassPath, List("typeLoader.testJars.DummySimpleClass"), Some(List("hello")))
  private val complexPathClassLoader: PathClassLoader = new PathClassLoader(complexClassPath, List("typeLoader.testJars.DummyComplexClass"), Some(List("catAge", "reverseName", "numberOfContacts")))

  private val dummySClass = new DummySimpleClass()
  private val dummyCClass = DummyComplexClass("name", 20, List("910000000"))

  "Class Loader " should {
    "be able to correctly load a class with a parameterless method" in {

      val dummySimpleClass: Class[_] = dummySClass.getClass
      val eitherClazz = simplePathClassLoader.getclazz("typeLoader.testJars.DummySimpleClass")

      eitherClazz shouldBe Right(dummySimpleClass)
      simplePathClassLoader.getMethod(eitherClazz.getOrElse(Class.forName("")), "hello") shouldBe Right(dummySimpleClass.getMethod("hello"))
    }
  }

  "Class Loader " should {
    "not be able to load a class that doesn´t exist" in {
      simplePathClassLoader.getclazz("FakeClass") shouldBe Left(ClassNotFoundException)
    }
  }

  "Class Loader " should {
    "be able to invoke loaded method" in {

      val result = for {
        clazz <- simplePathClassLoader.getclazz("typeLoader.testJars.DummySimpleClass")
        method <- simplePathClassLoader.getMethod(clazz, "hello")
      } yield simplePathClassLoader.callMethod(method, clazz, None) shouldBe Right(dummySClass.hello)

      result match {
        case Right(res) => res
        case _ => fail()
      }

    }
  }

  "Class Loader " should {
    "be able to correctly load a class with more than 1 method, with parameters" in {
      val dummyComplexClass: Class[_] = dummyCClass.getClass

      val eitherClazz = complexPathClassLoader.getclazz("typeLoader.testJars.DummyComplexClass")
      val clazzDefault = eitherClazz.getOrElse(Class.forName(""))

      eitherClazz shouldBe Right(dummyComplexClass)
      complexPathClassLoader.getMethod(clazzDefault, "reverseName") shouldBe Right(dummyComplexClass.getMethod("reverseName"))
      complexPathClassLoader.getMethod(clazzDefault, "numberOfContacts") shouldBe Right(dummyComplexClass.getMethod("numberOfContacts"))
      complexPathClassLoader.getMethod(clazzDefault, "catAge", Integer.TYPE) shouldBe Right(dummyComplexClass.getMethod("catAge", Integer.TYPE))

    }
  }

  "Class Loader " should {
    "be able to invoke more than 1 loaded parameter method, with a class that has arguments" in {

      val eitherClazz = complexPathClassLoader.getclazz("typeLoader.testJars.DummyComplexClass")

      val defaultClazz = eitherClazz.getOrElse(Class.forName(""))

      val eitherCatAgeMethod = complexPathClassLoader.getMethod(defaultClazz, "catAge", Integer.TYPE)
      val eitherReverseNameMethod = complexPathClassLoader.getMethod(defaultClazz, "reverseName")
      val eitherNumberOfContactsMethod = complexPathClassLoader.getMethod(defaultClazz, "numberOfContacts")

      val eitherInstance = complexPathClassLoader.instantiateClazz(defaultClazz, "name".asInstanceOf[AnyRef], 20.asInstanceOf[AnyRef], List("910000000").asInstanceOf[AnyRef])

      val invokeAgeCatMethod = eitherCatAgeMethod.flatMap(method =>
        complexPathClassLoader.callMethod(method, defaultClazz, Some(eitherInstance.getOrElse(AnyRef)), 2.asInstanceOf[AnyRef]))

      invokeAgeCatMethod match {
        case Right(res) => res shouldBe dummyCClass.catAge(2)
        case _ => fail()
      }

      val invokeReverseNameMethod = eitherReverseNameMethod.flatMap(method =>
        complexPathClassLoader.callMethod(method, defaultClazz, Some(eitherInstance.getOrElse(AnyRef))))

      invokeReverseNameMethod match {
        case Right(res) => res shouldBe dummyCClass.reverseName
        case _ => fail()
      }

      val invokeNumberOfContactsMethod = eitherNumberOfContactsMethod.flatMap(method =>
        complexPathClassLoader.callMethod(method, defaultClazz, Some(eitherInstance.getOrElse(AnyRef))))

      invokeNumberOfContactsMethod match {
        case Right(res) => res shouldBe dummyCClass.numberOfContacts
        case _ => fail()
      }
    }
  }

}

