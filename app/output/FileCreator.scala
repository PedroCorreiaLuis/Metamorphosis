package output

import java.io.{ File, PrintWriter }

import api.dtos.DSL
import validations.PathValidator

class FileCreator(inputs: DSL) extends PathValidator(inputs.filePath) {

  override def createFile: Either[Throwable, File] = PathValidator(inputs.filePath)

  def writeToFile: Either[Throwable, Unit] = {
    val p = new PrintWriter(inputs.filePath)
    //p.write{""}
    //Right()
    ???
  }
}

