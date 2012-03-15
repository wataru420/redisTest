//import AssemblyKeys._

name := "redisTest"                                                            

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "net.debasishg" % "redisclient_2.9.1" % "2.4.1",
  "org.slf4j" % "slf4j-log4j12" % "1.6.4",
  "log4j" % "log4j" % "1.2.16",
  "net.sf.opencsv" % "opencsv" % "2.3",
  "se.scalablesolutions.akka" % "akka-actor" % "1.2",
  "se.scalablesolutions.akka" % "akka-remote" % "1.2",
  "se.scalablesolutions.akka" % "akka-stm" % "1.2",
  "mysql" % "mysql-connector-java" % "5.1.18"
)

scalacOptions += "-deprecation"

seq(assemblySettings: _*)
