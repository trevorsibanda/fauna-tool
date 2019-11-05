package fauna.tool.coverage

case class CoverageConfig(
  val logFile: String = "",
  val liveTarget: Boolean = false,
  val showProgress: Boolean = true,
  val generateHTMLReport: Boolean = true,
  val verboseReport: Boolean = false,
  val htmlReportFile: String = "",
  val include: Seq[String] = Seq("go", "js", "scala", "java", "ruby", "python", "c#"),
  val exclude: Seq[String] = Seq()
)
