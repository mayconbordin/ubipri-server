name := """UbipriServer-New"""

version := "1.0-SNAPSHOT"
offline := true

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",
  "com.ganyo" % "gcm-server" % "1.0.2",
  "org.skyscreamer" % "jsonassert" % "1.2.3"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

//javaOptions in Test += "-Dlogger.file=conf/test-logger.xml"