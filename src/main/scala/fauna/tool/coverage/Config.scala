package fauna.tool.coverage

case class CoverageConfig(
  val logFile: String = "",
  val liveTarget: Boolean = false,
  val showProgress: Boolean = true,
  val generateHTMLReport: Boolean = true,
  val verboseReport: Boolean = false,
  val htmlReportFile: String = "",
  val include: Seq[String] =
    Seq("javascript", "java", "scala", "go", "python", "ruby", "c#"),
  val exclude: Seq[String] = Seq()
)
