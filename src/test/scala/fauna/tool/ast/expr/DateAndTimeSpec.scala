package fauna.tool.ast

import org.scalatest._
//time and date

class DateAndTimeFunctionsSpec extends ExprSuite {

  test("Time()") {
    val expr = Time(time = NullL)

    assertExpr(expr, "Time", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Now()") {
    val expr = Now(now = NullL)

    assertExpr(expr, "Now", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Epoch()") {
    val expr = Epoch(epoch = NullL, unit = NullL)

    assertExpr(expr, "Epoch", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Date()") {
    val expr = Date(date = NullL)

    assertExpr(expr, "Date", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToMicros()") {
    val expr = ToMicros(to_micros = NullL)

    assertExpr(expr, "ToMicros", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToMillis()") {
    val expr = ToMillis(to_millis = NullL)

    assertExpr(expr, "ToMillis", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToSeconds()") {
    val expr = ToSeconds(to_seconds = NullL)

    assertExpr(expr, "ToSeconds", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Second()") {
    val expr = Second(second = NullL)

    assertExpr(expr, "Second", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Minute()") {
    val expr = Minute(minute = NullL)

    assertExpr(expr, "Minute", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Hour()") {
    val expr = Hour(hour = NullL)

    assertExpr(expr, "Hour", 1, Arity.Exact(1), Effect.Pure)
  }

  test("DayOfMonth()") {
    val expr = DayOfMonth(day_of_month = NullL)

    assertExpr(expr, "DayOfMonth", 1, Arity.Exact(1), Effect.Pure)
  }

  test("DayOfWeek()") {
    val expr = DayOfWeek(day_of_week = NullL)

    assertExpr(expr, "DayOfWeek", 1, Arity.Exact(1), Effect.Pure)
  }

  test("DayOfYear()") {
    val expr = DayOfYear(day_of_year = NullL)

    assertExpr(expr, "DayOfYear", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Month()") {
    val expr = Month(month = NullL)

    assertExpr(expr, "Month", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Year()") {
    val expr = Year(year = NullL)

    assertExpr(expr, "Year", 1, Arity.Exact(1), Effect.Pure)
  }

  test("TimeAdd()") {
    val expr = TimeAdd(time_add = NullL, offset = NullL, unit = NullL)

    assertExpr(expr, "TimeAdd", 3, Arity.Exact(3), Effect.Pure)
  }

  test("TimeSubtract()") {
    val expr = TimeSubtract(time_subtract = NullL, offset = NullL, unit = NullL)

    assertExpr(expr, "TimeSubtract", 3, Arity.Exact(3), Effect.Pure)
  }

  test("TimeDiff()") {
    val expr = TimeDiff(time_diff = NullL, other = NullL, unit = NullL)

    assertExpr(expr, "TimeDiff", 3, Arity.Exact(3), Effect.Pure)
  }

}
