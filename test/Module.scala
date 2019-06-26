import com.google.inject.AbstractModule
import repository.{ CodeGenerator, TestGenerator }

import scala.concurrent.ExecutionContext

class Module extends AbstractModule {

  override def configure(): Unit = {

    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
    bind(classOf[CodeGenerator]).toInstance(new TestGenerator())

  }
}
