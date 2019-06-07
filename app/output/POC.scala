package output

import scala.util.{ Failure, Success, Try }

//Docker "input"
class POC

object POC {
  def ola(): Unit = println("ola")

  def main(args: Array[String]): Unit = {
    Try(ola())
    Try(print(Int.getClass.cast(Seq))) match {
      case Success(res) => res
      case Failure(exception) => println(exception)
    }
  }
}