name := "Lipsi"

//~ scalaVersion := "2.11.12"
//~ scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls")

scalaVersion := "2.12.8"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls", "-Xsource:2.11")

libraryDependencies += scalaVersion("org.scala-lang" % "scala-compiler" % _).value

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// here switch between Chisel 2 and 3

//libraryDependencies += "edu.berkeley.cs" %% "chisel" % "2.2.38"

libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.1.+"
libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.2.+"
