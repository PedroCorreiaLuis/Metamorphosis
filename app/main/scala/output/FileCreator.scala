package main.scala.output

import java.io.{File, PrintWriter}

import main.scala.api.dtos.DSL
import main.scala.validations.PathValidator

class FileCreator(inputs: DSL) extends PathValidator(inputs.filePath) {

  override def createFile: Either[Throwable, File] = PathValidator(inputs.filePath)

  def writeToFile(inputs: DSL): Either[Throwable, Unit] = {
        val p = new PrintWriter(inputs.filePath)
        //p.write{""}
        Right()
    }
}


