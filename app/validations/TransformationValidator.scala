package validations

import api.transformations.Transformation

class TransformationValidator[In, Out] extends Validator[Transformation[In, Out]] {
  def loopValidator = ()
}
