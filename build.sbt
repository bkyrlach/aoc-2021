name := "aoc-2021"

version := "0.1"

scalaVersion := "2.13.7"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.0"