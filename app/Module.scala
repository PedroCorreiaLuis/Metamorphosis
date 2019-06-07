import com.typesafe.config.ConfigFactory

class Module {
  val generatedClassPath: String = ConfigFactory.load().getString("generatedClassPath")

}
