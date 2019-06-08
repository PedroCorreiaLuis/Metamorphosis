package api.dtos
import play.api.libs.functional.syntax._
import play.api.libs.json._

//TODO find a better name, other than "DSL"
case class DSLDTO(inType: TypeDTO, outType: TypeDTO, transformations: Seq[TransformationDTO])

object DSLDTO {
  implicit val writeDsl: Writes[DSLDTO] = (
    (__ \ "inType").write[TypeDTO] and
    (__ \ "outType").write[TypeDTO] and
    (__ \ "transformations").write[Seq[TransformationDTO]])(unlift(DSLDTO.unapply))

  implicit val readTransformation: Reads[DSLDTO] = (
    (__ \ "inType").read[TypeDTO] and
    (__ \ "outType").read[TypeDTO] and
    (__ \ "transformations").read[Seq[TransformationDTO]])(DSLDTO.apply _)

}

