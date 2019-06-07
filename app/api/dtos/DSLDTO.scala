package api.dtos
import play.api.libs.json._

//TODO find a better name, other than "DSL"
case class DSLDTO(inType: TypeDTO, outType: TypeDTO, transformations: Seq[TransformationDTO])

object DSLDTO {
  implicit val dsl: OFormat[DSLDTO] = Json.format[DSLDTO]
}

