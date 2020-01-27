package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//time and date

case class Time(time: Expr) extends Expr {
  override val formKeys = Time.formKeys
}

object Time {
  val formKeys: List[Form.Key] = List("time").map(Form.Key.Required(_))
  Form.add("Time", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Time(bf.buildChild(value, "time"))
}

case class Now(now: Expr) extends Expr {
  override val formKeys = Now.formKeys
}

object Now {
  val formKeys: List[Form.Key] = List("now").map(Form.Key.Required(_))
  Form.add("Now", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Now(bf.buildChild(value, "now"))
}

case class Epoch(epoch: Expr, unit: Expr) extends Expr {
  override val formKeys = Epoch.formKeys
}

object Epoch {
  val formKeys: List[Form.Key] = List("epoch", "unit").map(Form.Key.Required(_))
  Form.add("Epoch", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Epoch(bf.buildChild(value, "epoch"), bf.buildChild(value, "unit"))
}

case class Date(date: Expr) extends Expr {
  override val formKeys = Date.formKeys
}

object Date {
  val formKeys: List[Form.Key] = List("date").map(Form.Key.Required(_))
  Form.add("Date", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Date(bf.buildChild(value, "date"))
}

case class ToMicros(to_micros: Expr) extends Expr {
  override val formKeys = ToMicros.formKeys
}

object ToMicros {
  val formKeys: List[Form.Key] = List("to_micros").map(Form.Key.Required(_))
  Form.add("ToMicros", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToMicros(bf.buildChild(value, "to_micros"))
}

case class ToMillis(to_millis: Expr) extends Expr {
  override val formKeys = ToMillis.formKeys
}

object ToMillis {
  val formKeys: List[Form.Key] = List("to_millis").map(Form.Key.Required(_))
  Form.add("ToMillis", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToMillis(bf.buildChild(value, "to_millis"))
}

case class ToSeconds(to_seconds: Expr) extends Expr {
  override val formKeys = ToSeconds.formKeys
}

object ToSeconds {
  val formKeys: List[Form.Key] = List("to_seconds").map(Form.Key.Required(_))
  Form.add("ToSeconds", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToSeconds(bf.buildChild(value, "to_seconds"))
}

case class Second(second: Expr) extends Expr {
  override val formKeys = Second.formKeys
}

object Second {
  val formKeys: List[Form.Key] = List("second").map(Form.Key.Required(_))
  Form.add("Second", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Second(bf.buildChild(value, "second"))
}

case class Minute(minute: Expr) extends Expr {
  override val formKeys = Minute.formKeys
}

object Minute {
  val formKeys: List[Form.Key] = List("minute").map(Form.Key.Required(_))
  Form.add("Minute", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Minute(bf.buildChild(value, "minute"))
}

case class Hour(hour: Expr) extends Expr {
  override val formKeys = Hour.formKeys
}

object Hour {
  val formKeys: List[Form.Key] = List("hour").map(Form.Key.Required(_))
  Form.add("Hour", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Hour(bf.buildChild(value, "hour"))
}

case class DayOfMonth(day_of_month: Expr) extends Expr {
  override val formKeys = DayOfMonth.formKeys
}

object DayOfMonth {
  val formKeys: List[Form.Key] = List("day_of_month").map(Form.Key.Required(_))
  Form.add("DayOfMonth", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    DayOfMonth(bf.buildChild(value, "day_of_month"))
}

case class DayOfWeek(day_of_week: Expr) extends Expr {
  override val formKeys = DayOfWeek.formKeys
}

object DayOfWeek {
  val formKeys: List[Form.Key] = List("day_of_week").map(Form.Key.Required(_))
  Form.add("DayOfWeek", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    DayOfWeek(bf.buildChild(value, "day_of_week"))
}

case class DayOfYear(day_of_year: Expr) extends Expr {
  override val formKeys = DayOfYear.formKeys
}

object DayOfYear {
  val formKeys: List[Form.Key] = List("day_of_year").map(Form.Key.Required(_))
  Form.add("DayOfYear", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    DayOfYear(bf.buildChild(value, "day_of_year"))
}

case class Month(month: Expr) extends Expr {
  override val formKeys = Month.formKeys
}

object Month {
  val formKeys: List[Form.Key] = List("month").map(Form.Key.Required(_))
  Form.add("Month", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Month(bf.buildChild(value, "month"))
}

case class Year(year: Expr) extends Expr {
  override val formKeys = Year.formKeys
}

object Year {
  val formKeys: List[Form.Key] = List("year").map(Form.Key.Required(_))
  Form.add("Year", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Year(bf.buildChild(value, "year"))
}

case class TimeAdd(
  time_add: Expr,
  offset: Expr,
  unit: Expr
) extends Expr {
  override val formKeys = TimeAdd.formKeys
}

object TimeAdd {

  val formKeys: List[Form.Key] =
    List("time_add", "offset", "unit").map(Form.Key.Required(_))
  Form.add("TimeAdd", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    TimeAdd(
      bf.buildChild(value, "time_add"),
      bf.buildChild(value, "offset"),
      bf.buildChild(value, "unit")
    )
}

case class TimeSubtract(
  time_subtract: Expr,
  offset: Expr,
  unit: Expr
) extends Expr {}

object TimeSubtract {

  val formKeys: List[Form.Key] =
    List("time_subtract", "offset", "unit").map(Form.Key.Required(_))
  Form.add("TimeSubtract", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    TimeSubtract(
      bf.buildChild(value, "time_subtract"),
      bf.buildChild(value, "offset"),
      bf.buildChild(value, "unit")
    )
}

case class TimeDiff(
  time_diff: Expr,
  other: Expr,
  unit: Expr
) extends Expr {
  override val formKeys = TimeDiff.formKeys
}

object TimeDiff {

  val formKeys: List[Form.Key] =
    List("time_diff", "other", "unit").map(Form.Key.Required(_))
  Form.add("TimeDiff", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    TimeDiff(
      bf.buildChild(value, "time_diff"),
      bf.buildChild(value, "other"),
      bf.buildChild(value, "unit")
    )
}
