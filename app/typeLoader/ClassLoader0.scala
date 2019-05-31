package typeLoader

import java.lang.reflect.{ Constructor, Method }

trait ClassLoader0 {

  def clazzes: List[Either[Throwable, Class[_]]]
  def methods: List[Either[Throwable, Method]]
  def callMethod(method: Method, clazz: Class[_]): AnyRef
  def getConstructor(clazz: Class[_]): Constructor[_]
  def instantiateClazz(constructor: Constructor[_]): AnyRef

}
