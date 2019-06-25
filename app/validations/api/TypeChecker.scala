package validations.api

object TypeChecker {

  val SimpleTypeList: List[String] = List("String", "Double", "Float", "Long", "Int", "Short", "Byte", "Unit", "Boolean", "Char", "Any", "AnyRef", "AnyVal")
  val ComposedTypeList: List[String] = List("List[", "Array[", "Vector[", "Seq[", "Iterator[", "Stream[", "Map[")
  val UDCTypeList: List[String] = List("UDC[")

  val SupportedTypeList: List[String] = SimpleTypeList ::: ComposedTypeList ::: UDCTypeList

}
