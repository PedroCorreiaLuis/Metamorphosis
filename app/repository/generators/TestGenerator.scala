package repository.generators

import java.util.UUID

import api.dtos.DSLDTO

import scala.concurrent.Future

class TestGenerator extends CodeGenerator {
  override def generate(dsl: DSLDTO, outputPath: String): Future[(String, String)] = Future.successful(UUID.randomUUID().toString, "")
}
