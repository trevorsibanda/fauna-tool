package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//string
case class Concat(concat: Expr, separator: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Concat(bf.buildChild(value, "concat"), bf.buildChildOpt(value, "separator"))
}

case class CaseFold(casefold: Expr, normalizer: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CaseFold(bf.buildChild(value, "casefold"), bf.buildChildOpt(value, "normalizer"))
}

case class RegexEscape(regexescape: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    RegexEscape(bf.buildChild(value, "regexescape"))
}

case class StartsWith(startswith: Expr, search: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    StartsWith(bf.buildChild(value, "startswith"), bf.buildChild(value, "search"))
}

case class EndsWith(endswith: Expr, search: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    EndsWith(bf.buildChild(value, "endswith"), bf.buildChild(value, "search"))
}

case class ContainsStr(containsstr: Expr, search: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ContainsStr(bf.buildChild(value, "containsstr"), bf.buildChild(value, "search"))
}

case class ContainsStrRegex(containsstrregex: Expr, pattern: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ContainsStrRegex(
      bf.buildChild(value, "containsstrregex"),
      bf.buildChild(value, "pattern")
    )
}

case class FindStr(findstr: Expr, find: Expr, start: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    FindStr(
      bf.buildChild(value, "findstr"),
      bf.buildChild(value, "find"),
      bf.buildChildOpt(value, "start")
    )
}

case class FindStrRegex(
  findstrregex: Expr,
  pattern: Expr,
  start: Option[Expr],
  num_results: Option[Expr]
) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    FindStrRegex(
      bf.buildChild(value, "findstrregex"),
      bf.buildChild(value, "pattern"),
      bf.buildChildOpt(value, "start"),
      bf.buildChildOpt(value, "num_results")
    )
}

case class Length(length: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Length(bf.buildChild(value, "length"))
}

case class LowerCase(lowercase: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    LowerCase(bf.buildChild(value, "lowercase"))
}

case class LTrim(ltrim: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    LTrim(bf.buildChild(value, "ltrim"))
}

case class NGram(ngram: Expr, min: Option[Expr], max: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    NGram(
      bf.buildChild(value, "ngram"),
      bf.buildChildOpt(value, "min"),
      bf.buildChildOpt(value, "max")
    )
}

case class Repeat(repeat: Expr, number: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Repeat(bf.buildChild(value, "repeat"), bf.buildChildOpt(value, "number"))
}

case class ReplaceStr(replacestr: Expr, find: Expr, replace: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ReplaceStr(
      bf.buildChild(value, "replacestr"),
      bf.buildChild(value, "find"),
      bf.buildChild(value, "replace")
    )
}

case class ReplaceStrRegex(
  replacestrregex: Expr,
  pattern: Expr,
  replace: Expr,
  first: Option[Expr]
) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ReplaceStrRegex(
      bf.buildChild(value, "replacestrregex"),
      bf.buildChild(value, "pattern"),
      bf.buildChild(value, "replace"),
      bf.buildChildOpt(value, "first")
    )
}

case class Rtrim(rtrim: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Rtrim(bf.buildChild(value, "rtrim"))
}

case class Space(space: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Space(bf.buildChild(value, "space"))
}

case class SubString(substring: Expr, start: Expr, length: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SubString(
      bf.buildChild(value, "substring"),
      bf.buildChild(value, "start"),
      bf.buildChildOpt(value, "length")
    )
}

case class TitleCase(titlecase: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    TitleCase(bf.buildChild(value, "titlecase"))
}

case class Trim(trim: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Trim(bf.buildChild(value, "trim"))
}

case class Uppercase(uppercase: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Uppercase(bf.buildChild(value, "uppercase"))
}

case class Format(format: Expr, values: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Format(bf.buildChild(value, "format"), bf.buildChildOpt(value, "values"))
}
