package api.dtos

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{ verifying, _ }
import play.api.libs.json.{ JsonValidationError, _ }
import validations.api.TypeChecker._
import validations.api.CategoryNames._

case class TypeDTO(categoryName: String, `type`: Option[String])

object TypeDTO {

  private[dtos] def verifyType(jsPath: JsPath): Reads[Option[String]] = jsPath.readNullable[String](
    verifying(str =>
      SimpleTypeList.exists(str.equals)
        || ComposedTypeList.exists(str.startsWith)
        || UDCTypeList.exists(str.contains)))

  implicit val writeType: Writes[TypeDTO] = (
    (__ \ "categoryName").write[String] and
    (__ \ "type").write[Option[String]])(unlift(TypeDTO.unapply))

  implicit val readType: Reads[TypeDTO] = (
    (__ \ "categoryName").read[String](filter[String](JsonValidationError.apply("Invalid category name."))(categoryNames.contains)) and
    verifyType(__ \ "type"))(TypeDTO.apply _)
}