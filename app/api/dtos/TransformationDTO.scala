package api.dtos

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import api.transformations.Transformations._

case class TransformationDTO(transformationName: Option[String], predicate: Option[String])

object TransformationDTO {

  private[dtos] def verifyTransformation(jsPath: JsPath): Reads[Option[String]] = jsPath.readNullable[String](
    verifying(possibleTransformations.contains(_)))

  implicit val writeTransformation: Writes[TransformationDTO] = (
    (__ \ "transformationName").write[Option[String]] and
    (__ \ "predicate").write[Option[String]])(unlift(TransformationDTO.unapply))

  implicit val readTransformation: Reads[TransformationDTO] = (
    verifyTransformation(__ \ "transformationName") and
    (__ \ "predicate").readNullable[String])(TransformationDTO.apply _)

}