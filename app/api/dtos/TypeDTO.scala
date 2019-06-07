package api.dtos

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.{ verifying, _ }
import play.api.libs.json._
import validations.TypeChecker._

//TODO categoryName can be deleted, no reason other than validations for this to exist
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
    (__ \ "categoryName").read[String] and
    verifyType(__ \ "type"))(TypeDTO.apply _)
}