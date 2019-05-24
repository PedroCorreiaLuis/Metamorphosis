import main.scala.validations.TypeChecker
import main.scala.validations.TypeChecker._

import scala.util.Try

val input = "transformations{d;d} types[UD[String];Array[Map[Int,Int]]] C:f.txt  "

val typesExtractor = "\\[.+;.+\\]".r
val transformationsExtractor = "\\{.+\\}".r
val fileExtractor = ".:.+\\.txt".r

val types = typesExtractor.findAllIn(input).toList.head
input.matches(typesExtractor.regex)

transformationsExtractor.findAllIn(input).toList.head
input.matches(transformationsExtractor.regex)

fileExtractor.findAllIn(input).toList.head
input.matches(fileExtractor.regex)

//val dsl = new DSLValidator("transformations{d;d} types[g;e] C:f.txt ")

val auxArr = types.substring(1,types.length-1).split(";")

val typeIn = auxArr(0)
val typeOut = auxArr(1)

def validateString(input: String): Either[Throwable,Any] = {

  input match {
    case s if s.startsWith("String") => Right("".getClass)
    case _ => Left(new Throwable("No Such type Exception"))
  }

}

validateString("String")

def hasSupportedTypes(input: String): Boolean = {
  TypeChecker.SupportedTypeList.exists(input.contains(_))
}

def isSimpleType(input: String): Boolean = {
  SimpleTypeList.exists(input.equals)
}

def isComposedType(input: String): Boolean = {
  ComposedTypeList.exists(input.startsWith)
}

isComposedType("String")
isComposedType("Boolean")
isComposedType("List[Float]")
isComposedType("string")
isComposedType(" wefu  3w4efui")

