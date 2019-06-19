package config

import com.typesafe.config.ConfigFactory

/**
 * Loader from application.conf
 */
object Config {
  val SBT = ConfigFactory.load().getString("sbtPath")
  val OutputPath = ConfigFactory.load().getString("generatedClassPath")
}
