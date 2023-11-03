lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.example",
      scalaVersion := "2.13.12",
      idePackagePrefix.withRank(KeyRanks.Invisible) := Some("com.example")
    )
  ),
  name := "Challenge"
)

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29"
