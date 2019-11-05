package fauna.tool.logmonitor

import fauna.tool.ast.Effect

import fauna.tool.ast.Expr

trait Filter {
  def apply(entry: LogEntry, expr: Option[Expr]): Boolean
}

case class HttpResponseFilter(code: Int) extends Filter {

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean =
    entry.response.code == code
}

case class HttpErrorResponseFilter(hasError: Boolean) extends Filter {

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean =
    hasError && entry.response.errors.isDefined
}

case class TimeStampFilter(startTs: String, endTs: Option[String]) extends Filter {
  import java.time.OffsetDateTime

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean = {
    val start = OffsetDateTime.parse(startTs)

    if (entry.ts.isDefined) {
      val queryTs = OffsetDateTime.parse(entry.ts.get)
      val afterStart: Boolean = queryTs.isAfter(start)
      if (endTs.isDefined) {
        val end = OffsetDateTime.parse(endTs.get)
        afterStart && queryTs.isBefore(end)
      } else {
        afterStart
      }
    } else {
      false
    }
  }
}

case class QueryFilter(functionNames: Seq[String]) extends Filter {

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean = {
    val d: Boolean = false
    var b: Boolean = false
    expr.fold(d)((e: Expr) => {
      e.forEachChildren((child: Expr) => {
        if (functionNames.contains(child.name)) {
          b = true
        }
      })
      b
    })
  }

}

case class QueryRegexFilter(pattern: String) extends Filter {
  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean = ???
}

case class QueryDriverFilter(driverNames: Seq[String]) extends Filter {

  lazy val drivers: Seq[String] = driverNames.map(_.toLowerCase)

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean =
    if (drivers.isEmpty) true
    else entry.driver.fold(false)(d => drivers.contains(d.toLowerCase))
}

case class AuthFilter(key: Option[String], role: Option[String]) extends Filter {
  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean = ???
}

case class QueryEffectFilter(effect: Effect) extends Filter {

  override def apply(entry: LogEntry, expr: Option[Expr]): Boolean =
    expr.fold(false)(_.effect == effect)
}
