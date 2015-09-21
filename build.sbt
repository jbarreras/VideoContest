name := """VideoContest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJpa,
  cache,
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "org.hibernate" % "hibernate-core" % "3.6.9.Final",
  "mysql" % "mysql-connector-java" % "5.1.26",
  "net.bramp.ffmpeg" % "ffmpeg" % "0.2",
  "javax.mail" % "mail" % "1.4.3"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
