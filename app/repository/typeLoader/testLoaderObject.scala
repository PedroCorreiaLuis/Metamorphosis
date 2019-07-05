package repository.typeLoader

import java.io.File
import java.lang.reflect.{ Constructor, Method }
import java.net.{ URL, URLClassLoader }

//To delete
object testLoaderObject extends App {

  val jarPathName = "..\\Metamorphosis\\app\\output\\DummySimpleClass.scala"

  val classLoader = new URLClassLoader(Array[URL](new File(jarPathName).toURI.toURL))

  val clazz: Class[_] = classLoader.loadClass("repository.typeLoader.testJars.DummySimpleClass")
  val method: Method = clazz.getMethod("hello")
  def callMethod(method: Method, clazz: Class[_]): AnyRef = {
    method.invoke(clazz)
  }

  //val invokeMethod: Unit = method.invoke(clazz.newInstance())
  val ad: Constructor[_] = clazz.getConstructors()(0)
  val b = ad.newInstance().asInstanceOf[AnyRef]
  method.invoke(b)
}
