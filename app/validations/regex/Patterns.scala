package validations.regex

import scala.util.matching.Regex

object Patterns {

  val MatchTypes = ".*(types\\[.+;.+\\]).*"
  val MatchTransformations = ".*(transformations\\{.+\\}).*"
  val MatchFile = ".*(.:.+\\.txt).*"

  val TypesExtractor: Regex = "\\[.+;.+\\]".r
  val TransformationsExtractor: Regex = "\\{.+\\}".r
  val FileExtractor: Regex = ".:.+\\.txt".r
}
