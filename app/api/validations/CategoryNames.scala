package api.validations

object CategoryNames {

  val SimpleType = "Simple"
  val ComposedType = "Composed"
  val UserDefinedType = "UDC"

  val categoryNames: List[String] = SimpleType :: ComposedType :: UserDefinedType :: Nil

}
