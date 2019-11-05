package fauna.tool.codegen

import fauna.tool.ast._
import fauna.tool.parser.ASTBuilder

class JSCodeGenerator extends Generator {
  val name = "JsCodeGenerator"

  val allowUnknownExpr: Boolean = true

  override def exprToCode(expr: Expr): Code = expr match {
    case ObjectExpr(obj) => exprToCode(obj)
    case ap: PaginateAfter => {
      val params = Map(
        "after" -> ap.after,
        "ts" -> ap.ts,
        "size" -> ap.size,
        "events" -> ap.events,
        "sources" -> ap.sources
      ).collect {
        case (k: String, Some(v)) => (k, v)
      }.toMap
      val obj = ObjectL(params)
      s"Paginate(${exprToCode(ap.paginate)}, ${exprToCode(obj)})"
    }
    case ap: PaginateBefore => {
      val params = Map(
        "before" -> Some(ap.before),
        "ts" -> ap.ts,
        "size" -> ap.size,
        "events" -> ap.events,
        "sources" -> ap.sources
      ).collect {
        case (k: String, Some(v)) => (k, v)
      }.toMap
      val obj = ObjectL(params)
      s"Paginate(${exprToCode(ap.paginate)}, ${exprToCode(obj)})"
    }
    case ap: PaginateCursor => {
      val params = Map(
        "cursor" -> Some(ap.cursor),
        "ts" -> ap.ts,
        "size" -> ap.size,
        "events" -> ap.events,
        "sources" -> ap.sources
      ).collect {
        case (k: String, Some(v)) => (k, v)
      }.toMap
      val obj = ObjectL(params)
      s"Paginate(${exprToCode(ap.paginate)}, ${exprToCode(obj)})"
    }
    case _ => super.exprToCode(expr)
  }

  def fromFile(file: String): List[Expr] = ???

  def fromStream(s: Stream[String]): Expr = ???

  def code: Code = ???

  override implicit val builder: ASTBuilder[_] =
    new fauna.tool.parser.JsonASTBuilder()

}
