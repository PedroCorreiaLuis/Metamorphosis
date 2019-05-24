package main.scala.typeLoader

import java.lang.reflect.{ Constructor, Method }

trait ClassLoader0 {

  def clazzes: Either[Throwable, List[Class[_]]]
  def methods: Either[Throwable, List[Method]]
  def callMethod(method: Method, clazz: Class[_]): AnyRef
  def getConstructor(clazz: Class[_]): Constructor[_]
  def instantiateClazz(constructor: Constructor[_]): AnyRef

}
