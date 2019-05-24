package main.scala.typeLoader

import java.io.File
import java.lang.reflect.{ Constructor, Method }
import java.net.{ URL, URLClassLoader }

class JarClassLoaderSimplified(jarPath: String, classNames: List[String], methodNames: List[String]) {

  private val classLoader = new URLClassLoader(Array[URL](new File(jarPath).toURI.toURL))

  def clazz: Class[_] = classLoader.loadClass(classNames.head)

  def method: Method = {
    clazz.getMethod(methodNames.head)
  }

  def getConstructor(clazz: Class[_]): Constructor[_] = clazz.getConstructors()(0)

  def instantiateClazz(constructor: Constructor[_]): AnyRef = constructor.newInstance().asInstanceOf[AnyRef]

  def callMethod(method: Method, clazz: Class[_]): AnyRef = {
    method.invoke(clazz.newInstance())
  }

}
