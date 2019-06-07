package output

import scala.concurrent.Future

trait CodeGenerator {
  def generate(outputPath: String, objectName: String): Future[Unit]
}
