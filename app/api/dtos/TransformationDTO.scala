package api.dtos

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import api.transformations.Transformations._

case class TransformationDTO(transformationName: Transformation, predicate: Option[String])

object TransformationDTO {
  import api.transformations.Transformations.Transformation._

  def applyWithOptional(transformationName: String, predicate: Option[String]): TransformationDTO =
    TransformationDTO(toTransformation(transformationName), predicate)

  //def unapplyWithOptional(arg: TransformationDTO): Option[(Transformation, Option[String])] = Some(arg.transformationName.transformationName, arg.predicate)

  implicit val writeTransformation: Writes[TransformationDTO] = (
    (__ \ "transformation").write[Transformation] and
    (__ \ "predicate").write[Option[String]])(unlift(TransformationDTO.unapply))

  implicit val readTransformation: Reads[TransformationDTO] = (
    (__ \ "transformation").read[String](filter[String](JsonValidationError.apply("Invalid transformation type."))(name => !toTransformation(name).isInstanceOf[InvalidTransformation])) and
    (__ \ "predicate").readNullable[String])(TransformationDTO.applyWithOptional _)
}

//