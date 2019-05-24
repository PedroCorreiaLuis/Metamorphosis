package main.scala.validations

import main.scala.api.transformations.Transformation

class TransformationValidator[In, Out] extends Validator[Transformation[In, Out]] {
  def loopValidator = ()
}
