package translater

import validations.TypeValidator

//To delete?
class DSLConverter(input: String) extends TypeValidator(input) {
  /*
  def extractType: Either[Throwable,Any] = {
  if(TypeValidator(input)) new TypeValidator(input).getTypeCategory match {
    case SimpleType => if(input.equals("String")) Right("") else Right("")
    case ComposedType => Right()
    case UDCType => Right()
    case InvalidType => Left(new Throwable("No Such type Exception"))
  }
  else Left(new Throwable("No Such type Exception"))
  }
// "Int", "Short", "Byte", "Unit", "Any", "AnyRef", "AnyVal")
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
      case _ if input.equals("Any") => 0.asInstanceOf[Any]
      case _ if input.equals("AnyRef") => 0.asInstanceOf[AnyRef]
      case _ if input.equals("AnyVal") => 0.asInstanceOf[AnyVal]
    }

  }*/

}

object DSLConverter