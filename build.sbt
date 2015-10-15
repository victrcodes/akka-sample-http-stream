name := "akka-sample-http-stream"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
	"com.typesafe.akka" %% "akka-http-experimental" % "1.0"
)