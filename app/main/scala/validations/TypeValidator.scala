package main.scala.validations

import main.scala.translater._
import main.scala.validations.TypeChecker._

class TypeValidator(input: String) extends Validator[DSLConverter] {

  def hasSupportedTypes(input:String)(String: => Boolean): Boolean = SupportedTypeList.exists(input.contains)
  def isSimpleType(input:String)(String: => Boolean): Boolean = SimpleTypeList.exists(input.equals)
  def isComposedType(input:String)(String: => Boolean): Boolean = ComposedTypeList.exists(input.startsWith)
  def isUDCType(input:String)(String: => Boolean): Boolean = UDCTypeList.exists(input.contains)

}

