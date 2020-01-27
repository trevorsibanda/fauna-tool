package fauna.tool.codegen

import fauna.tool.ast._

class JSCodeGenerator extends Generator {
  val name = "JsCodeGenerator"

  val allowUnknownExpr: Boolean = true

  override def exprToCode(expr: Expr): Code = expr match {
    case ObjectExpr(obj) => exprToCode(obj)
    case ap: Paginate => {
      val params: Map[String, Expr] = Map(
        "after" -> ap.after,
        "before" -> ap.before,
        "cursor" -> ap.cursor,
        "ts" -> ap.ts,
        "size" -> ap.size,
        "events" -> ap.events,
        "sources" -> ap.sources
      ).collect {
        case (k: String, Some(v)) => (k, v)
      }.toMap
      if (params.isEmpty)
        s"Paginate(${exprToCode(ap.paginate)})"
      else
        s"Paginate(${exprToCode(ap.paginate)}, ${exprToCode(ObjectL(params))})"
    }
    case _ => super.exprToCode(expr)
  }

  def fromFile(file: String): List[Expr] = ???

  def fromStream(s: Stream[String]): Expr = ???

  def code: Code = ???

}
