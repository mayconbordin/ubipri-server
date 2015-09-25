name := """UbipriServer-New"""

version := "1.0-SNAPSHOT"
offline := true

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

resolvers += "custom maven releases" at "http://52north.org/maven/repo/releases/"
resolvers += "jitpack" at "https://jitpack.io"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.postgresql" % "postgresql" % "9.3-1103-jdbc41",
  "org.postgis" % "postgis-jdbc" % "2.1.3",
  "com.ganyo" % "gcm-server" % "1.0.2",
  "org.skyscreamer" % "jsonassert" % "1.2.3",
  "com.github.mayconbordin" % "postgis-geojson" % "1.0",
  "commons-io" % "commons-io" % "2.4",
  "org.apache.httpcomponents" % "httpclient" % "4.5",
  "com.github.mayconbordin" % "OAuth2Client" % "1.0.2",
  "org.mockito" % "mockito-all" % "1.10.19",

  // OAuth 2.0
  "com.nulab-inc" %% "play2-oauth2-provider" % "0.15.1",

  // BCrypt
  "org.mindrot" % "jbcrypt" % "0.3m",

  // Web UI
  "org.webjars" % "webjars-play_2.11" % "2.4.0-1",
  "com.adrianhurt" % "play-bootstrap3_2.11" % "0.4.4-P24",

  "org.webjars.bower" % "font-awesome" % "4.4.0",
  "org.webjars.bower" % "github-com-arthur-e-Wicket" % "1.2"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

//javaOptions in Test += "-Dlogger.file=conf/test-logger.xml"
