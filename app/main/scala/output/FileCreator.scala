package main.scala.output

import java.io.{ File, PrintWriter }

import main.scala.validations.PathValidator

import scala.util.{ Failure, Success, Try }

class FileCreator(path: String) extends PathValidator(path) {

  override def createFile: Either[Throwable, File] = PathValidator(path)

  def writeToFile(file: File, inputs: String): Either[Throwable, Unit] = {

    val p = new PrintWriter(file.getAbsolutePath)

    Try(p.write(inputs)) match {
      case Failure(exception) =>
        p.close()
        Left(exception)
      case Success(value) =>
        p.close()
        Right(value)
    }
  }

}
