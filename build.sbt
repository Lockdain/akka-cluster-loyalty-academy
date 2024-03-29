name := "loyalty"

organization := "reactivebbq"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.21"
lazy val akkaManagementVersion =  "0.20.0"
lazy val logbackVersion = "1.2.3"
lazy val scalaTestVersion = "3.0.1"

fork := true
parallelExecution in ThisBuild := false

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-cluster"         % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding"% akkaVersion,
  "com.lightbend.akka.management" %% "akka-management" % akkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % akkaManagementVersion,
  "com.lightbend.akka" %% "akka-diagnostics" % "1.1.7",
  "com.lightbend.akka" %% "akka-split-brain-resolver" % "1.1.7",

  //Logback
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,

  //Test dependencies
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % scalaTestVersion% Test,

  Cinnamon.library.cinnamonAkka,
  Cinnamon.library.cinnamonAkkaHttp,
  Cinnamon.library.cinnamonJvmMetricsProducer,
  Cinnamon.library.cinnamonPrometheus,
  Cinnamon.library.cinnamonPrometheusHttpServer,
)

enablePlugins(Cinnamon)

cinnamon in run := true
cinnamon in test := true
