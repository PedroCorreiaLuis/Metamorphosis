package repository
import api.dtos.DSLDTO

import scala.concurrent.Future

class TestGenerator extends CodeGenerator {
  override def generate(dsl: DSLDTO, outputPath: String, objectName: String): Future[String] = Future.successful("")
}
