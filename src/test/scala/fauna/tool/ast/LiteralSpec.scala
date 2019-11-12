package fauna.tool.ast

import org.scalatest._

import fauna.tool.ast.{ Effect, Expr, Literal, Type }
import fauna.tool.parser.JsonASTBuilder
import org.json4s.JsonAST.JNothing
import fauna.tool.parser.ASTBuilder
import org.json4s.JsonAST.JValue

class LiteralSpec extends FunSuite with Matchers {

  val literals = Seq(
    StringL("str"),
    IntL(Int.MaxValue),
    LongL(Long.MaxValue),
    FloatL(Float.MaxValue),
    DecimalL(BigDecimal(0)),
    TrueL,
    FalseL,
    DoubleL(0.98),
    NullL,
    ObjectL(Map.empty),
    ArrayL(List(NullL))
  )

  test("have no effect") {
    literals.collectFirst {
      case l: Literal if l.effect != Effect.Pure => l
    } shouldEqual None
  }

  test("have no children") {
    literals.collectFirst {
      case l: Literal if !l.children.isEmpty => l
    } shouldEqual None
  }

  test("not evaluate to a AnyT") {
    literals.collect {
      case l: Literal if l.evaluatesTo == AnyT => l
    } shouldEqual Nil
  }

  test("ArrayL should evaluate to proper type") {
    literals.init.collect {
      case lit: Literal if ArrayL(List(lit)).evaluatesTo == AnyT => lit
    } shouldEqual Nil
  }

  test("not have a builder") {
    import scala.util.Try

    implicit val bf: ASTBuilder[JValue] = new fauna.tool.parser.JsonASTBuilder()

    val v: JValue = JNothing

    literals
      .map { l =>
        Try(l.build(v)(bf)).isFailure
      }
      .contains(false) shouldEqual false

  }

  test("should be convertable to JSON") {
    literals.foreach { l =>
      Expr.literalToJson(l).isInstanceOf[JValue] shouldEqual true
    }
  }

}
