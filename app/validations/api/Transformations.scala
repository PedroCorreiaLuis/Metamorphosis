package validations.api

import play.api.libs.json.{ Json, Reads, Writes }

object Transformations {

  class Transformation(val transformationName: String)

  object Transformation {

    def apply(transformationName: String): Transformation = toTransformation(transformationName)
    def unapply(arg: Transformation): Option[String] = fromTransformation(arg)

    implicit val writeTransf: Writes[Transformation] = Json.writes[Transformation]
    implicit val readTransf: Reads[Transformation] = Json.reads[Transformation]
  }

  case object Filter extends Transformation("filter")
  case object Map extends Transformation("map")
  case object Sum extends Transformation("sum")
  case object Size extends Transformation("size")

  case class InvalidTransformation(errorMessage: String) extends Transformation("invalid")

  def toTransformation(name: String): Transformation = name match {
    case Filter.transformationName => Filter
    case Sum.transformationName => Sum
    case Map.transformationName => Map
    case Size.transformationName => Size
    case _ => InvalidTransformation(s"$name is not a valid Transformation type.")
  }

  def fromTransformation(transf: Transformation): Option[String] = transf match {
    case _: InvalidTransformation => None
    case _ => Some(transf.transformationName)
  }
}
