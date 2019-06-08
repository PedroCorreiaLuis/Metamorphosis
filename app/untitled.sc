import api.dtos.{DSLDTO, TransformationDTO, TypeDTO}
import play.api.libs.json.Json

val inTDTO = TypeDTO("Compose",Some("List[Int]"))
val outTDTO  = TypeDTO("Simple",Some("Int"))

val transDTO  = Seq(TransformationDTO(Some("filter"),Some("p=> p> 5")),TransformationDTO(Some("sum"),None))

val dslDTO = DSLDTO(inTDTO,outTDTO,transDTO)
Json.toJson(dslDTO)