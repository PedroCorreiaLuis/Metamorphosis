

val genericList = "List[_]"
val targetList = "List[Map[String,Int]]"
if(targetList.startsWith("List[")){}


val withoutList = targetList.substring(5,targetList.length-1)

val withoutMap = withoutList.substring(4,withoutList.length-1)

val auxArr = withoutMap.split(",")

val keysType = auxArr(0)
val valuesType = auxArr(1)

val u = ""
u.getClass.getName

def strToAny(input: String): Any = {
  input match {
    case _ if input.equals("String") => " "
    case _ if input.equals("Boolean") => true
    case _ if input.equals("Char") => ' '
    case _ if input.equals("Int") => 0
    case _ if input.equals("Double") => 0.toDouble
    case _ if input.equals("Float") => 0f
    case _ if input.equals("Long") => 0l
    case _ if input.equals("Short") => 0.toShort
    case _ if input.equals("Byte")=> 0.toByte
    case _ if input.equals("Unit") => ()
    case _ if input.equals("Any") => if(true)true.asInstanceOf[Any]
    case _ if input.equals("AnyRef") => 0.asInstanceOf[AnyRef]
    case _ if input.equals("AnyVal") => 0.asInstanceOf[AnyVal]
    case _ => new Throwable("No Such simple type Exception")
  }

}



val strType = strToAny("String").getClass
val boolType = strToAny("Boolean").getClass
strToAny("Char").getClass
strToAny("Int").getClass
strToAny("Double").getClass
strToAny("Float").getClass
strToAny("Long").getClass
strToAny("Short").getClass
strToAny("Byte").getClass
strToAny("Unit").getClass
strToAny("Any").getClass
strToAny("AnyRef").getClass
strToAny("AnyVal").getClass
strToAny("").getClass
AnyRef





import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

/*val m = runtimeMirror(getClass.getClassLoader)
val tb = m.mkToolBox()
val clazz = tb.compile(tb.parse("Int"))
val a : String
*/
typeOf[String]
import scala.reflect.runtime.currentMirror

def toType(expr: String) = {
  val tb = currentMirror.mkToolBox()
  val exp = tb.parse(expr.trim)
  tb.typecheck(exp).tpe

}

val t = toType("List")



