package main.scala.api.dtos
import play.api.libs.json.{ Json, OFormat }

case class DSL(inType: String, outType: String, transformations: Seq[String], filePath: Option[String])

object DSL {
  implicit val dsl: OFormat[DSL] = Json.format[DSL]
}