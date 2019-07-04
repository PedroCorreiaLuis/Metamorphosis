package repository

import java.io.PrintWriter

object ScriptGenerator {

  def generate(outputPath: String, clazz: String): Unit = {

    new PrintWriter(outputPath) {

      write("""#!/bin/sh
              |
              |realpath () {
              |(
              |  TARGET_FILE="$1"
              |
              |  cd "$(dirname "$TARGET_FILE")"
              |  TARGET_FILE=$(basename "$TARGET_FILE")
              |
              |  COUNT=0
              |  while [ -L "$TARGET_FILE" -a $COUNT -lt 100 ]
              |  do
              |      TARGET_FILE=$(readlink "$TARGET_FILE")
              |      cd "$(dirname "$TARGET_FILE")"
              |      TARGET_FILE=$(basename "$TARGET_FILE")
              |      COUNT=$(($COUNT + 1))
              |  done
              |
              |  if [ "$TARGET_FILE" = "." -o "$TARGET_FILE" = ".." ]; then
              |    cd "$TARGET_FILE"
              |    TARGET_FILEPATH=
              |  else
              |    TARGET_FILEPATH=/$TARGET_FILE
              |  fi
              |
              |  echo "$(pwd -P)/$TARGET_FILE"
              |)
              |}
              |
              |# Allow user and template_declares (see below) to add java options.
              |addJava () {
              |  java_opts="$java_opts $1"
              |}
              |
              |# Allow user to specify java options. These get listed first per bash-template.
              |if [ -n "$JAVA_OPTS" ]
              |then
              |  addJava "$JAVA_OPTS"
              |fi
              |
              |# Loads a configuration file full of default command line options for this script.
              |loadConfigFile() {
              |  cat "$1" | sed '/^\#/d;s/\r$//' | sed 's/^-J-X/-X/' | tr '\r\n' ' '
              |}
              |
              |
              |real_script_path="$(realpath "$0")"
              |app_home="$(realpath "$(dirname "$real_script_path")")"
              |lib_dir="$(realpath "${app_home}/../lib")"
              |
              |""".stripMargin)
      write(s"app_mainclass=$clazz")
      write("""
              |
              |script_conf_file="${app_home}/../conf/application.ini"
              |app_classpath="$lib_dir/../conf/:$lib_dir/metamorphosis.metamorphosis-0.1-sans-externalized.jar:$lib_dir/org.scala-lang.scala-compiler-2.12.8.jar:$lib_dir/org.scala-lang.scala-library-2.12.8.jar:$lib_dir/org.scala-lang.scala-reflect-2.12.8.jar:$lib_dir/org.scala-lang.modules.scala-xml_2.12-1.0.6.jar:$lib_dir/com.typesafe.play.twirl-api_2.12-1.3.15.jar:$lib_dir/com.thesamet.scalapb.scalapb-runtime_2.12-0.9.0-M7.jar:$lib_dir/com.thesamet.scalapb.lenses_2.12-0.9.0-M7.jar:$lib_dir/com.lihaoyi.fastparse_2.12-2.1.3.jar:$lib_dir/com.lihaoyi.sourcecode_2.12-0.1.7.jar:$lib_dir/com.google.protobuf.protobuf-java-3.7.1.jar:$lib_dir/com.typesafe.play.play-server_2.12-2.6.20.jar:$lib_dir/com.typesafe.play.play_2.12-2.6.20.jar:$lib_dir/com.typesafe.play.build-link-2.6.20.jar:$lib_dir/com.typesafe.play.play-exceptions-2.6.20.jar:$lib_dir/com.typesafe.play.play-netty-utils-2.6.20.jar:$lib_dir/org.slf4j.slf4j-api-1.7.25.jar:$lib_dir/org.slf4j.jul-to-slf4j-1.7.25.jar:$lib_dir/org.slf4j.jcl-over-slf4j-1.7.25.jar:$lib_dir/com.typesafe.play.play-streams_2.12-2.6.20.jar:$lib_dir/org.reactivestreams.reactive-streams-1.0.2.jar:$lib_dir/com.typesafe.akka.akka-stream_2.12-2.5.17.jar:$lib_dir/com.typesafe.akka.akka-actor_2.12-2.5.17.jar:$lib_dir/com.typesafe.config-1.3.3.jar:$lib_dir/org.scala-lang.modules.scala-java8-compat_2.12-0.8.0.jar:$lib_dir/com.typesafe.akka.akka-protobuf_2.12-2.5.17.jar:$lib_dir/com.typesafe.ssl-config-core_2.12-0.2.4.jar:$lib_dir/org.scala-lang.modules.scala-parser-combinators_2.12-1.0.6.jar:$lib_dir/com.typesafe.akka.akka-slf4j_2.12-2.5.17.jar:$lib_dir/com.fasterxml.jackson.core.jackson-core-2.8.11.jar:$lib_dir/com.fasterxml.jackson.core.jackson-annotations-2.8.11.jar:$lib_dir/com.fasterxml.jackson.datatype.jackson-datatype-jdk8-2.8.11.jar:$lib_dir/com.fasterxml.jackson.core.jackson-databind-2.8.11.1.jar:$lib_dir/com.fasterxml.jackson.datatype.jackson-datatype-jsr310-2.8.11.jar:$lib_dir/commons-codec.commons-codec-1.10.jar:$lib_dir/com.typesafe.play.play-json_2.12-2.6.10.jar:$lib_dir/com.typesafe.play.play-functional_2.12-2.6.10.jar:$lib_dir/org.typelevel.macro-compat_2.12-1.1.1.jar:$lib_dir/joda-time.joda-time-2.9.9.jar:$lib_dir/com.google.errorprone.error_prone_annotations-2.0.18.jar:$lib_dir/com.google.j2objc.j2objc-annotations-1.1.jar:$lib_dir/org.codehaus.mojo.animal-sniffer-annotations-1.14.jar:$lib_dir/io.jsonwebtoken.jjwt-0.7.0.jar:$lib_dir/javax.xml.bind.jaxb-api-2.3.0.jar:$lib_dir/org.apache.commons.commons-lang3-3.6.jar:$lib_dir/javax.transaction.jta-1.1.jar:$lib_dir/javax.inject.javax.inject-1.jar:$lib_dir/com.typesafe.play.filters-helpers_2.12-2.6.20.jar:$lib_dir/com.typesafe.play.play-logback_2.12-2.6.20.jar:$lib_dir/ch.qos.logback.logback-classic-1.2.3.jar:$lib_dir/ch.qos.logback.logback-core-1.2.3.jar:$lib_dir/com.typesafe.play.play-akka-http-server_2.12-2.6.20.jar:$lib_dir/com.typesafe.akka.akka-http-core_2.12-10.0.14.jar:$lib_dir/com.typesafe.akka.akka-parsing_2.12-10.0.14.jar:$lib_dir/org.scalatestplus.play.scalatestplus-play_2.12-3.1.2.jar:$lib_dir/org.scalatest.scalatest_2.12-3.0.4.jar:$lib_dir/org.scalactic.scalactic_2.12-3.0.4.jar:$lib_dir/org.seleniumhq.selenium.selenium-java-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-api-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-chrome-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-remote-driver-3.5.3.jar:$lib_dir/cglib.cglib-nodep-3.2.4.jar:$lib_dir/org.apache.commons.commons-exec-1.3.jar:$lib_dir/commons-logging.commons-logging-1.2.jar:$lib_dir/com.google.code.gson.gson-2.8.0.jar:$lib_dir/com.google.guava.guava-23.0.jar:$lib_dir/org.apache.httpcomponents.httpclient-4.5.3.jar:$lib_dir/org.apache.httpcomponents.httpcore-4.4.6.jar:$lib_dir/net.java.dev.jna.jna-4.1.0.jar:$lib_dir/net.java.dev.jna.jna-platform-4.1.0.jar:$lib_dir/org.seleniumhq.selenium.selenium-edge-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-firefox-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-ie-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-opera-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-safari-driver-3.5.3.jar:$lib_dir/org.seleniumhq.selenium.selenium-support-3.5.3.jar:$lib_dir/org.hamcrest.hamcrest-core-1.3.jar:$lib_dir/junit.junit-4.12.jar:$lib_dir/commons-io.commons-io-2.5.jar:$lib_dir/org.w3c.css.sac-1.3.jar:$lib_dir/net.sourceforge.cssparser.cssparser-0.9.23.jar:$lib_dir/net.sourceforge.htmlunit.htmlunit-2.27.jar:$lib_dir/xalan.xalan-2.7.2.jar:$lib_dir/xalan.serializer-2.7.2.jar:$lib_dir/xml-apis.xml-apis-1.4.01.jar:$lib_dir/org.apache.httpcomponents.httpmime-4.5.3.jar:$lib_dir/net.sourceforge.htmlunit.htmlunit-core-js-2.27.jar:$lib_dir/net.sourceforge.htmlunit.neko-htmlunit-2.27.jar:$lib_dir/xerces.xercesImpl-2.11.0.jar:$lib_dir/org.eclipse.jetty.websocket.websocket-client-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.jetty-util-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.jetty-io-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.jetty-client-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.jetty-http-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.websocket.websocket-common-9.4.5.v20170502.jar:$lib_dir/org.eclipse.jetty.websocket.websocket-api-9.4.5.v20170502.jar:$lib_dir/org.seleniumhq.selenium.htmlunit-driver-2.27.jar:$lib_dir/javax.servlet.javax.servlet-api-3.1.0.jar:$lib_dir/com.codeborne.phantomjsdriver-1.4.3.jar:$lib_dir/com.typesafe.play.play-test_2.12-2.6.5.jar:$lib_dir/com.typesafe.play.play-guice_2.12-2.6.5.jar:$lib_dir/com.google.inject.guice-4.1.0.jar:$lib_dir/aopalliance.aopalliance-1.0.jar:$lib_dir/com.google.inject.extensions.guice-assistedinject-4.1.0.jar:$lib_dir/com.novocode.junit-interface-0.11.jar:$lib_dir/org.scala-sbt.test-interface-1.0.jar:$lib_dir/com.google.code.findbugs.jsr305-3.0.2.jar:$lib_dir/org.fluentlenium.fluentlenium-core-3.3.0.jar:$lib_dir/org.atteo.classindex.classindex-3.4.jar:$lib_dir/com.typesafe.play.play-ws_2.12-2.6.5.jar:$lib_dir/com.typesafe.play.play-ws-standalone_2.12-1.1.0.jar:$lib_dir/com.typesafe.play.play-ws-standalone-xml_2.12-1.1.0.jar:$lib_dir/com.typesafe.play.play-ws-standalone-json_2.12-1.1.0.jar:$lib_dir/com.typesafe.play.play-ahc-ws_2.12-2.6.5.jar:$lib_dir/com.typesafe.play.play-ahc-ws-standalone_2.12-1.1.0.jar:$lib_dir/com.typesafe.play.cachecontrol_2.12-1.1.2.jar:$lib_dir/org.joda.joda-convert-1.7.jar:$lib_dir/com.typesafe.play.shaded-asynchttpclient-1.1.0.jar:$lib_dir/com.typesafe.play.shaded-oauth-1.1.0.jar:$lib_dir/javax.cache.cache-api-1.0.0.jar:$lib_dir/com.chuusai.shapeless_2.12-2.3.3.jar:$lib_dir/com.lihaoyi.ammonite-ops_2.12-1.6.8.jar:$lib_dir/com.lihaoyi.os-lib_2.12-0.3.0.jar:$lib_dir/com.lihaoyi.geny_2.12-0.1.8.jar:$lib_dir/org.scala-lang.modules.scala-collection-compat_2.12-2.0.0.jar:$lib_dir/metamorphosis.metamorphosis-0.1-assets.jar"
              |
              |addJava "-Duser.dir=$(realpath "$(cd "${app_home}/.."; pwd -P)"  $(is_cygwin && echo "fix"))"
              |
              |
              |# If a configuration file exist, read the contents to $opts
              |[ -f "$script_conf_file" ] && opts=$(loadConfigFile "$script_conf_file")
              |
              |exec java $java_opts -classpath $app_classpath $opts $app_mainclass "$@"""".stripMargin)
      close()
    }
  }
}

