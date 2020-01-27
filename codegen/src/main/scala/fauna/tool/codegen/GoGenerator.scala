package fauna.tool.codegen

import fauna.tool.ast._

class GolangCodeGenerator extends JSCodeGenerator {
  override val name = "GoLangCodeGenerator"

  val packagePrefix: String = "f."

  override val allowUnknownExpr: Boolean = true

  override def fnName(fn: String): String = s"${packagePrefix}${fn}"

  override def exprToCode(expr: Expr): Code = expr match {
    case Let(bindings, in) => letBuilder(bindings, in)

    case FindStr(findstr, find, start) if start.isDefined =>
      s"${fnName("FindStr")}(${exprToCode(findstr)}, ${exprToCode(find)}, ${fnName("Start")}(${exprToCode(start.get)}))"

    case Concat(concat, separator) if separator.isDefined =>
      s"${fnName("Concat")}(${exprToCode(concat)}, ${fnName("Separator")}(${exprToCode(separator.get)}))"

    case CaseFold(casefold, normalizer) if normalizer.isDefined =>
      s"${fnName("Casefold")}(${exprToCode(casefold)}, ${fnName("Normalizer")}(${exprToCode(normalizer.get)}))"

    case SubString(substring, start, length) if length.isDefined =>
      s"${fnName("SubString")}(${exprToCode(substring)}, ${exprToCode(start)}, ${fnName("StrLength")}(${exprToCode(length.get)}))"

    case Merge(merge, wth, lambda) if lambda.isDefined =>
      s"${fnName("Merge")}(${exprToCode(merge)}, ${exprToCode(wth)}, ${fnName("ConflictResolver")}(${exprToCode(lambda.get)}))"

    case Round(round, precision) if precision.isDefined =>
      s"${fnName("Round")}(${exprToCode(round)}, ${fnName("Precision")}(${exprToCode(precision.get)}))"

    case Get(get, ts) if ts.isDefined =>
      s"${fnName("Get")}(${exprToCode(get)}, ${fnName("TS")}(${exprToCode(ts.get)}))"

    case Exists(exists, ts) if ts.isDefined =>
      s"${fnName("Get")}(${exprToCode(exists)}, ${fnName("TS")}(${exprToCode(ts.get)}))"

    case Paginate(paginate, before, after, cursor, ts, size, events, sources) => {
      val params = Seq(
        if (before.isDefined) Some(s"Before(${exprToCode(before.get)}})") else None,
        if (after.isDefined) Some(s"After(${exprToCode(after.get)}})") else None,
        if (sources.isDefined) Some(s"Sources(${exprToCode(sources.get)}})")
        else None,
        if (ts.isDefined) Some(s"TS(${exprToCode(ts.get)}})") else None,
        if (size.isDefined) Some(s"Size(${exprToCode(size.get)})") else None,
        if (events.isDefined) Some(s"EventsOpt(${exprToCode(events.get)}})")
        else None,
        if (cursor.isDefined) Some(s"Cursor(${exprToCode(cursor.get)}})") else None
      ).collect {
          case Some(s) => s"${packagePrefix}$s"
        }
        .mkString(", ")
      s"""${fnName("Paginate")}(${exprToCode(paginate)}${if (!params.isEmpty)
        s", $params"
      else ""})"""
    }

    case ReplaceStrRegex(replacestrregex, pattern, replace, first)
        if first.isDefined =>
      s"${fnName("ReplaceStrRegex")}(${exprToCode(replacestrregex)}, ${exprToCode(
        pattern
      )}, ${exprToCode(replace)}, ${fnName("OnlyFirst")}(${exprToCode(first.get)}))"

    case Select(select, from, default) if default.isDefined =>
      s"${fnName("Select")}(${exprToCode(select)}, ${exprToCode(from)}, ${fnName("Default")}(${exprToCode(default.get)}))"
    case l: Literal => literalToCode(l)
    case _          => super.exprToCode(expr)
  }

  override def literalToCode(l: Literal): String = l match {
    case NullL     => s"${fnName("Null")}()"
    case ArrayL(l) => l.map(exprToCode).mkString(s"${fnName("Arr")}{", ", ", "}")
    case ObjectL(m) =>
      m.map {
          case (k, v) => s""""$k": ${exprToCode(v)}"""
        }
        .mkString(s"${fnName("Obj")}{", ", ", "}")
    case _ => super.literalToCode(l)
  }

  def letBuilder(bindings: Expr, in: Expr): Code = {

    def buildBindings(e: Expr): Seq[Code] = e match {
      case ObjectL(m) =>
        m.map { case (k: String, v: Expr) => s"""Bind("${k}", ${exprToCode(v)})""" }.toSeq
      case ArrayL(l) => l.map(buildBindings).flatten
      case _         => Seq(s"Bind(${exprToCode(e)})")
    }

    s"""${fnName("Let")}().${buildBindings(bindings).mkString(".")}.In(${exprToCode(
      in
    )})"""

  }

  override def fromFile(file: String): List[Expr] = ???

  override def fromStream(s: Stream[String]): Expr = ???

  override def code: Code = ???

}
