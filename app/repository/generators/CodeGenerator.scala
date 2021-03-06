package repository.generators

import api.dtos.DSLDTO

import scala.concurrent.Future

trait CodeGenerator {
  def generate(dsl: DSLDTO, outputPath: String): Future[(String, String)]
}
