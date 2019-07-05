package repository.typeLoader

import java.lang.reflect.{ Constructor, Method }

trait ClassLoader0 {

  def getclazz(className: String): Either[Throwable, Class[_]]
  def getMethod(clazz: Class[_], methodName: String, parameterTypes: Class[_]*): Either[Throwable, Method]
  def callMethod(method: Method, clazz: Class[_], instance: Option[AnyRef], args: AnyRef*): Either[Throwable, AnyRef]
  def instantiateClazz(clazz: Class[_], classArgs: AnyRef*): AnyRef
}
