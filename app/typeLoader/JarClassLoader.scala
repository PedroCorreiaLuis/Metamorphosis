package typeLoader

import java.io.File
import java.lang.reflect.{ Constructor, Method }
import java.net.{ URL, URLClassLoader }
import validations.Errors._

import scala.util.{ Failure, Success, Try }

/**
 * This class loads classes from target path
 * @param path the path where the jar is located
 * @param classNames a list of all classes that the jar contains
 * @param methodNames a list of all the methods that a class contains
 */
class JarClassLoader(path: String, classNames: List[String], methodNames: Option[List[String]] = None) extends ClassLoader0 {

  /** Tries to load the class in the target path **/
  val classLoader: URLClassLoader = new URLClassLoader(Array[URL](new File(path).toURI.toURL))

  /**
   * @return Gets either all the classes that the path contains,
   * or the respective error why the classes couldn´t be obtained
   */
  def clazzes: List[Either[Throwable, Class[_]]] = {
    classNames.map(clazz => Try(classLoader.loadClass(clazz)) match {
      case Success(res) => Right(res)
      case _ => Left(classNotFoundException)
    })
  }

  /**
   * @return Gets either all the methods that the classes contain,
   * or the respective error why the methods couldn´t be obtained.
   */
  def methods: List[Either[Throwable, Method]] = {
    methodNames match {
      case Some(methods) => methods.flatMap(method =>
        clazzes.map(
          {
            case Right(clazz) => Right(clazz.getMethod(method))
            case _ => Left(new NoSuchMethodException())
          }))
      case None => List(Left(new NoSuchMethodException()))
    }
  }

  /**
   * @param method target method
   * @param clazz target class
   * @return This function either returns the result of the method on the target class,
   * or the respective error why the method couldn´t be invoked.
   */
  def callMethod(method: Method, clazz: Class[_]): Either[Throwable, AnyRef] = {
    Try(method.invoke(clazz.newInstance())) match {
      case Failure(exception) => Left(exception)
      case Success(value) => Right(value)
    }
  }

  /**
   * @param clazz target class
   * @return The constructor of target class
   */
  def getConstructor(clazz: Class[_]): Constructor[_] = clazz.getConstructors()(0)

  /**
   * @param constructor target constructor
   * @return The instance of target constructor
   */
  def instantiateClazz(constructor: Constructor[_]): AnyRef = constructor.newInstance().asInstanceOf[AnyRef]

}
