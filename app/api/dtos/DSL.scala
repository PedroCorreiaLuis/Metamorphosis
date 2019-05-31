package api.dtos
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import validations.TypeChecker._

case class DSL(inType: String, outType: String, transformations: Seq[String], filePath: String)

object DSL {

  private def verifyType(jsPath: JsPath): Reads[String] = jsPath.readNullable[String](
    verifying(str =>
      SimpleTypeList.exists(str.equals)
        || ComposedTypeList.exists(str.startsWith)
        || UDCTypeList.exists(str.contains))).map(_.getOrElse(""))

  implicit val dsl: OWrites[DSL] = Json.format[DSL]

  implicit val rd: Reads[DSL] = (
    verifyType(__ \ "inType") and
    verifyType(__ \ "outType") and
    (__ \ "transformations").read[Seq[String]] and
    (__ \ "filePath").read[String])(DSL.apply _)
}

