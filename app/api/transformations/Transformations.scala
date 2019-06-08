package api.transformations

object Transformations {

  sealed trait Transformation
  case object Filter extends Transformation
  case object Map extends Transformation
  case object Sum extends Transformation
  case object Size extends Transformation

  lazy val possibleTransformations: List[String] = List("filter", "map", "sum", "size")
}
