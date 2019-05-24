package main.scala.typeLoader

import java.io.File
import java.lang.reflect.{ Constructor, Method }
import java.net.{ URL, URLClassLoader }

object fff {

  val jarPathName = "C:\\Users\\Pedro Luis\\IdeaProjects\\Metamorphosis\\src\\main\\scala\\bananas.jar"

  val classLoader = new URLClassLoader(Array[URL](new File(jarPathName).toURI.toURL))
  val clazz: Class[_] = classLoader.loadClass("POC")
  val method: Method = clazz.getMethod("ola")
  def callMethod(method: Method, clazz: Class[_]): AnyRef = {
    method.invoke(clazz)
  }

  val invokeMethod: Unit = method.invoke(clazz.newInstance())
  val ad: Constructor[_] = clazz.getConstructors()(0)
  val b = ad.newInstance().asInstanceOf[AnyRef]
  val result = println(method.invoke(b))
}
