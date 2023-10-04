val projectName = IO.readLines(new File("PROJECT_NAME")).head
val v = IO.readLines(new File("VERSION")).head

lazy val rootSettings = Seq(
  organization := "com.jobgun",
  scalaVersion := "2.13.10",
  version      := v
)

lazy val sparkVersion = "3.3.1"
lazy val zparkioVersion = {
  val raw = "2.4.8_1.1.0"
  val zparkioVersion = raw.split("_")(1)
  s"${sparkVersion}_${zparkioVersion}"
}

lazy val root = (project in file("."))
  .settings(
    name := projectName,
    rootSettings,
    libraryDependencies ++= Seq(
      // https://mvnrepository.com/artifact/org.apache.spark/spark-core
      "org.apache.spark" %% "spark-core" % sparkVersion % Provided ,
      // https://mvnrepository.com/artifact/org.apache.spark/spark-sql
      "org.apache.spark" %% "spark-sql"  % sparkVersion % Provided ,
      // https://github.com/leobenkel/ZparkIO
      "com.leobenkel"    %% "zparkio"      % zparkioVersion,
      "com.leobenkel" %% "zparkio-config-scallop" % zparkioVersion,
      "dev.zio" %% "zio" % "2.0.13",
      "dev.zio" %% "zio-openai" % "0.2.1",
      "dev.zio" %% "zio-json" % "0.6.0",
      "io.weaviate" % "client" % "4.3.0",
      "com.google.guava" % "guava" % "32.1.2-jre",
      "com.leobenkel"    %% "zparkio-test" % zparkioVersion    % Test,
      // https://www.scalatest.org/
      "org.scalatest"    %% "scalatest"    % "3.2.17" % Test
    )
  )
