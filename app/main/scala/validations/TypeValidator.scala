package main.scala.validations

import main.scala.translater._
import main.scala.validations.TypeChecker._

class TypeValidator(input: String) extends Validator[DSLConverter] {

  def getTypeCategory: Type = {
    if(hasSupportedTypes){
      if (isSimpleType) SimpleType
      else if(isComposedType) ComposedType
      else UDCType
    }
    else InvalidType
  }

  private def hasSupportedTypes: Boolean = SupportedTypeList.exists(input.contains)
  private def isSimpleType: Boolean =SimpleTypeList.exists(input.equals)
  private def isComposedType: Boolean = ComposedTypeList.exists(input.startsWith)
  private def isUDCType: Boolean =UDCTypeList.exists(input.contains)

  def validateTypes: Boolean = hasSupportedTypes &&(isSimpleType|| isComposedType|| isUDCType)

}

object TypeValidator {
  def apply(input: String): Boolean = new TypeValidator(input).validateTypes
}
