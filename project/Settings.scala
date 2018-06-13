import sbt._
import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object Settings {

  val withCommon: Seq[Setting[_]] = {
    Seq(
      organization := "com.infare.scheduler",
      scalaVersion := "2.11.12",
      //      resolvers += "Sonatype Nexus Repository Manager" at "http://nexus.infare.net:8000/repository/maven-releases",
      resolvers += "jitpack" at "https://jitpack.io",
      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
      //      runMain in Compile := Defaults.runMainTask(fullClasspath in Compile, runner in(Compile, run)),

      parallelExecution := false,
      javaOptions ++= Seq(
        "-Xms512M",
        "-Xmx2048M",
        "-XX:MaxPermSize=2048M",
        "-XX:+CMSClassUnloadingEnabled"
      ),
      scalacOptions := Seq("-feature",
        "-deprecation",
        "-Xfatal-warnings",
        "-Ypatmat-exhaust-depth", "off"
      )
    )
  }

  val withAssembly: Seq[Setting[_]] = {
    baseAssemblySettings ++ Seq(
      assemblyMergeStrategy in assembly := {
        case PathList("javax", "xml", xs@_*) => MergeStrategy.last
        case x =>
          val oldStrategy = (assemblyMergeStrategy in assembly).value
          oldStrategy(x)
      },
      assemblyShadeRules in assembly ++= Seq(),
      assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
      test in assembly := {}
    )
  }

  val withTesting: Seq[Setting[_]] = Seq(
    parallelExecution in Test := false,
    fork in Test := true,
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
  )

  val spark: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-core" % "2.3.0" % "provided", // spark runtime already provides jars
    "org.apache.spark" %% "spark-sql" % "2.3.0" % "provided"
  )

  val testing: Seq[ModuleID] = Seq(
    "org.scalactic" %% "scalactic" % "3.0.4" % "test",
    "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  )

  val logging: Seq[ModuleID] = Seq(
    "org.apache.kafka" % "kafka-log4j-appender" % "0.9.0.1",
    "org.apache.kafka" % "kafka-clients" % "0.9.0.1",
    "org.slf4j" % "slf4j-log4j12" % "1.7.25",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  )

  val configs: Seq[ModuleID] = Seq(
    "com.typesafe" % "config" % "1.2.1",
    "com.github.pureconfig" %% "pureconfig" % "0.8.0"
  )

  val databases: Seq[ModuleID] = Seq(
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.2.2",
    "org.scalikejdbc" %% "scalikejdbc" % "3.2.2",
    "com.memsql" %% "memsql-connector" % "2.0.4",
    "mysql" % "mysql-connector-java" % "5.1.42",
    "com.microsoft.sqlserver" % "mssql-jdbc" % "6.1.0.jre8"
  )

  val notebook: Seq[ModuleID] = Seq(
    "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.126",
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    "com.github.nscala-time" %% "nscala-time" % "2.20.0"
  )

  val frameless: Seq[ModuleID] = List(
    "org.typelevel" %% "frameless-dataset" % "0.4.1",
    "org.typelevel" %% "frameless-ml" % "0.4.1",
    "org.typelevel" %% "frameless-cats" % "0.4.1"
  )
}
