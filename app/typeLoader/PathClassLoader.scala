package typeLoader

import java.io.File
import java.lang.reflect.Method
import java.net.{URL, URLClassLoader}

import validations.Errors._

import scala.util.{Failure, Success, Try}

/**
 * This class loads classes from target path
 * @param path the path where the jar is located
 * @param classNames a list of all classes that the jar contains
 * @param methodNames a list of all the methods that a class contains
 */
class PathClassLoader(path: String, classNames: List[String], methodNames: Option[List[String]] = None) extends ClassLoader0 {

  /** Load the class in the target path **/
  val classLoader: URLClassLoader = new URLClassLoader(Array[URL](new File(path).toURI.toURL))

  /**
   * @param className class name
   * @return Gets either a specific class that the path contains,
   * or the respective error.
   */
  def getclazz(className: String): Either[Throwable, Class[_]] = {

    Try(classLoader.loadClass(className)) match {
      case Success(res) => Right(res)
      case _ => Left(ClassNotFoundException)
    }
  }

  /**
   *
   * @param clazz class that has the specific method
   * @param methodName name of the method
   * @param parameterTypes parameters of the method
   * @return Gets a specific method that the classes contain,
   * or the respective error .
   */
  def getMethod(clazz: Class[_], methodName: String, parameterTypes: Class[_]*): Either[Throwable, Method] = {
    Try(clazz.getMethod(methodName, parameterTypes: _*)) match {
      case Failure(_) => Left(MethodNotFoundException)
      case Success(value) => Right(value)
    }
  }

  /**
   * @param method target method
   * @param clazz target class
   * @return This function either returns the result of the method on the target class,
   * or the respective error why the method couldn´t be invoked.
   */
  def callMethod(method: Method, clazz: Class[_], instance: Option[AnyRef], args: AnyRef*): Either[Throwable, AnyRef] = {

    val methodInvocation = instance match {
      case Some(instantiation) => Try(method.invoke(instantiation, args: _*))
      case None => Try(method.invoke(clazz.newInstance(), args: _*))
    }
    methodInvocation match {
      case Failure(exception) => Left(exception)
      case Success(value) => Right(value)
    }

  }

  /**
   * Use this function when the class as arguments
   * @param constructor target constructor
   * @return The instance of target constructor
   */
  def instantiateClazz(clazz: Class[_], classArgs: AnyRef*): Either[Throwable, AnyRef] = {

    Try(clazz.getConstructors()(0).newInstance(classArgs: _*).asInstanceOf[AnyRef]) match {
      case Failure(exception) => Left(exception)
      case Success(res) => Right(res)
    }
  }

}
