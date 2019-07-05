package api.dtos

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import api.validations.Transformations._

case class TransformationDTO(transformationName: Transformation, transformationOrder: Int, predicate: Option[String])

object TransformationDTO {
  import api.validations.Transformations.Transformation._

  def applyWithOptional(transformationName: String, transformationOrder: Int, predicate: Option[String]): TransformationDTO =
    TransformationDTO(toTransformation(transformationName), transformationOrder, predicate)

  implicit val writeTransformation: Writes[TransformationDTO] = (
    (__ \ "transformation").write[Transformation] and
    (__ \ "transformationOrder").write[Int] and
    (__ \ "predicate").write[Option[String]])(unlift(TransformationDTO.unapply))

  implicit val readTransformation: Reads[TransformationDTO] = (
    (__ \ "transformation").read[String](filter[String](JsonValidationError.apply("Invalid transformation type."))(name => !toTransformation(name).isInstanceOf[InvalidTransformation])) and
    (__ \ "transformationOrder").read[Int] and
    (__ \ "predicate").readNullable[String])(TransformationDTO.applyWithOptional _)
}

