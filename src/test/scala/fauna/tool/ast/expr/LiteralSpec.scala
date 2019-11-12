package fauna.tool.ast

import org.scalatest._
import org.json4s.JsonAST.JDecimal

class LiteralExprSpec extends FunSuite with Matchers {

  test("ObjectExpr") {
    val l = ObjectExpr(NullL)
  }

  test("ObjectL") {
    val l = ObjectL(Map.empty)
  }

  test("StringL") {
    val l = StringL("a string")
  }

  test("NullL") {
    val l = NullL
  }

  test("DoubleL") {
    val l = DoubleL(6.67)
  }

  test("IntL") {
    val l = IntL(2021)
  }

  test("LongL") {
    val l = LongL(1278)
  }

  test("FloatL") {
    val l = FloatL(3.14f)
  }

  test("DecimalL") {
    val l = DecimalL(0.00)
    l.toJson shouldEqual JDecimal(0.00)
  }

  test("BooleanL") {
    BooleanL(true) shouldEqual TrueL
    BooleanL(false) shouldEqual FalseL
  }

  test("ArrayL") {
    val l = ArrayL(List.empty)
  }

}
