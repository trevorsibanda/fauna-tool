package fauna.tool.logmonitor

case class LogsMonitorConfig(
  logFile: String = "",
  follow: Boolean = false,
  codegen: String = "js",
  followDelayMs: Int = 200,
  filterDrivers: Seq[String] = Nil,
  filterQueryContainsFns: Seq[String] = Nil,
  filterRegexQuery: String = "",
  filterQueryEffect: String = "",
  filterTimestampStart: String = "",
  filterTimestampEnd: String = "",
  filterErrorResponse: Boolean = false,
  filterResponseCode: Int = 0
)
