package api.dtos

import play.api.libs.json.{ Json, OFormat }

case class ComputeDTO(imageName: String, imageTag: String, DSLDTO: DSLDTO)

object ComputeDTO {
  implicit val emailDTO: OFormat[ComputeDTO] = Json.format[ComputeDTO]
}
