package main.scala.validations

import java.io.File

import scala.util.{ Failure, Success, Try }

class PathValidator(path: String) extends Validator[File] {

  protected def createFile: Either[Throwable, File] = {
    Try(new File(path)) match {
      case Failure(exception) => Left(exception)
      case Success(value) => Right(value)
    }
  }
}

object PathValidator {
  def apply(path: String): Either[Throwable, File] = new PathValidator(path).createFile
}