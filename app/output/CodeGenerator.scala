package output

import api.dtos.DSLDTO

import scala.concurrent.Future

trait CodeGenerator {
  def generate(dsl: DSLDTO, outputPath: String, objectName: String): Future[String]
}
