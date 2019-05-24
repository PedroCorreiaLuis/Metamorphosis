package main.scala.typeLoader

import java.io.File
import java.lang.reflect.{ Constructor, Method }
import java.net.{ URL, URLClassLoader }

import scala.util.{ Failure, Success, Try }

/**
 * This class loads classes from target path
 * @param jarPath the path where the jar is located
 * @param classNames a list of all classes that the jar contains
 * @param methodNames a list of all the methods that a class contains
 */
class JarClassLoader(jarPath: String, classNames: List[String], methodNames: Option[List[String]] = None) extends ClassLoader0 {

  /** Tries to load the class in the target jar **/
  private val maybeClassLoader: Try[URLClassLoader] = Try(new URLClassLoader(Array[URL](new File(jarPath).toURI.toURL)))

  /**
   * @return Gets either all the classes that the jar contains,
   * or the respective error why the classes couldn´t be obtained
   */
  def clazzes: Either[Throwable, List[Class[_]]] = {
    maybeClassLoader match {
      case Failure(exception) => Left(exception)
      case Success(clazz) => Right(classNames.map(clazz.loadClass))
    }
  }

  /**
   * @return Gets either all the methods that the classes contain,
   * or the respective error why the methods couldn´t be obtained.
   */
  def methods: Either[Throwable, List[Method]] = {

    methodNames.map { methods =>
      clazzes match {
        case Left(error) => Left(error)
        case Right(clazzes) =>
          Right(for {
            method <- methods
            clazz <- clazzes
          } yield clazz.getMethod(method))
      }
    }.getOrElse(Left(new NoSuchMethodException))

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
