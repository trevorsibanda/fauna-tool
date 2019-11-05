package fauna.tool.ast

import fauna.tool.parser.ASTBuilder


//time and date

case class Time(time: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Time(bf.buildChild(value, "time"))
}

case class Now(now: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Now(bf.buildChild(value, "now"))
}

case class Epoch(epoch: Expr, unit: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Epoch(bf.buildChild(value, "epoch"), bf.buildChild(value, "unit"))
}

case class Date(date: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Date(bf.buildChild(value, "date"))
}

case class ToMicros(to_micros: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToMicros(bf.buildChild(value, "to_micros"))
}

case class ToMillis(to_millis: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToMillis(bf.buildChild(value, "to_millis"))
}

case class ToSeconds(to_seconds: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToSeconds(bf.buildChild(value, "to_seconds"))
}

case class Second(second: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Second(bf.buildChild(value, "second"))
}

case class Minute(minute: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Minute(bf.buildChild(value, "minute"))
}

case class Hour(hour: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Hour(bf.buildChild(value, "hour"))
}

case class DayOfMonth(day_of_month: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    DayOfMonth(bf.buildChild(value, "day_of_month"))
}

case class DayOfWeek(day_of_week: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    DayOfWeek(bf.buildChild(value, "day_of_week"))
}

case class DayOfYear(day_of_year: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    DayOfYear(bf.buildChild(value, "day_of_year"))
}

case class Month(month: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Month(bf.buildChild(value, "month"))
}

case class Year(year: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Year(bf.buildChild(value, "year"))
}

case class TimeAdd(time_add: Expr, offset: Expr, unit: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    TimeAdd(
      bf.buildChild(value, "time_add"),
      bf.buildChild(value, "offset"),
      bf.buildChild(value, "unit")
    )
}

case class TimeSubtract(time_subtract: Expr, offset: Expr, unit: Expr)
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    TimeSubtract(
      bf.buildChild(value, "time_subtract"),
      bf.buildChild(value, "offset"),
      bf.buildChild(value, "unit")
    )
}

case class TimeDiff(time_diff: Expr, other: Expr, unit: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    TimeDiff(
      bf.buildChild(value, "time_diff"),
      bf.buildChild(value, "other"),
      bf.buildChild(value, "unit")
    )
}
