package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//string

case class Concat(
  concat: Expr,
  separator: Option[Expr]
) extends Expr {
  override val formKeys = Concat.formKeys
}

object Concat {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("concat"), Form.Key.Optional("separator"))
  Form.add("Concat", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Concat(bf.buildChild(value, "concat"), bf.buildChildOpt(value, "separator"))
}

case class CaseFold(
  casefold: Expr,
  normalizer: Option[Expr]
) extends Expr {
  override val formKeys = CaseFold.formKeys
}

object CaseFold {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("casefold"), Form.Key.Optional("normalizer"))
  Form.add("CaseFold", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CaseFold(bf.buildChild(value, "casefold"), bf.buildChildOpt(value, "normalizer"))
}

case class RegexEscape(regexescape: Expr) extends Expr {
  override val formKeys = RegexEscape.formKeys
}

object RegexEscape {
  val formKeys: List[Form.Key] = List("regexescape").map(Form.Key.Required(_))
  Form.add("RegexEscape", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    RegexEscape(bf.buildChild(value, "regexescape"))
}

case class StartsWith(
  startswith: Expr,
  search: Expr
) extends Expr {
  override val formKeys = StartsWith.formKeys
}

object StartsWith {

  val formKeys: List[Form.Key] =
    List("startswith", "search").map(Form.Key.Required(_))
  Form.add("StartsWith", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    StartsWith(bf.buildChild(value, "startswith"), bf.buildChild(value, "search"))
}

case class EndsWith(
  endswith: Expr,
  search: Expr
) extends Expr {
  override val formKeys = EndsWith.formKeys
}

object EndsWith {
  val formKeys: List[Form.Key] = List("endswith", "search").map(Form.Key.Required(_))
  Form.add("EndsWith", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    EndsWith(bf.buildChild(value, "endswith"), bf.buildChild(value, "search"))
}

case class ContainsStr(
  containsstr: Expr,
  search: Expr
) extends Expr {
  override val formKeys = ContainsStr.formKeys
}

object ContainsStr {

  val formKeys: List[Form.Key] =
    List("containsstr", "search").map(Form.Key.Required(_))
  Form.add("ContainsStr", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ContainsStr(bf.buildChild(value, "containsstr"), bf.buildChild(value, "search"))
}

case class ContainsStrRegex(
  containsstrregex: Expr,
  pattern: Expr
) extends Expr {
  override val formKeys = ContainsStrRegex.formKeys
}

object ContainsStrRegex {

  val formKeys: List[Form.Key] =
    List("containsstrregex", "pattern").map(Form.Key.Required(_))
  Form.add("ContainsStrRegex", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ContainsStrRegex(
      bf.buildChild(value, "containsstrregex"),
      bf.buildChild(value, "pattern")
    )
}

case class FindStr(
  findstr: Expr,
  find: Expr,
  start: Option[Expr]
) extends Expr {
  override val formKeys = FindStr.formKeys
}

object FindStr {

  val formKeys: List[Form.Key] = List(
    Form.Key.Required("findstr"),
    Form.Key.Required("find"),
    Form.Key.Optional("start")
  )
  Form.add("FindStr", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
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
) extends Expr {
  override val formKeys = FindStrRegex.formKeys
}

object FindStrRegex {

  val formKeys = List("findstrregex", "pattern").map(Form.Key.req) ++ List(
    "start",
    "num_results"
  ).map(Form.Key.opt)
  Form.add("FindStrRegex", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    FindStrRegex(
      bf.buildChild(value, "findstrregex"),
      bf.buildChild(value, "pattern"),
      bf.buildChildOpt(value, "start"),
      bf.buildChildOpt(value, "num_results")
    )
}

case class Length(length: Expr) extends Expr {
  override val formKeys = Length.formKeys
}

object Length {
  val formKeys: List[Form.Key] = List("length").map(Form.Key.Required(_))
  Form.add("Length", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Length(bf.buildChild(value, "length"))
}

case class LowerCase(lowercase: Expr) extends Expr {
  override val formKeys = LowerCase.formKeys
}

object LowerCase {
  val formKeys: List[Form.Key] = List("lowercase").map(Form.Key.Required(_))
  Form.add("LowerCase", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    LowerCase(bf.buildChild(value, "lowercase"))
}

case class LTrim(ltrim: Expr) extends Expr {
  override val formKeys = LTrim.formKeys
}

object LTrim {
  val formKeys: List[Form.Key] = List("ltrim").map(Form.Key.Required(_))
  Form.add("LTrim", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    LTrim(bf.buildChild(value, "ltrim"))
}

case class NGram(
  ngram: Expr,
  min: Option[Expr],
  max: Option[Expr]
) extends Expr {
  override val formKeys = NGram.formKeys
}

object NGram {

  val formKeys: List[Form.Key] = List(Form.Key.Required("ngram")) ++ List(
    "min",
    "max"
  ).map(Form.Key.Optional(_))
  Form.add("NGram", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    NGram(
      bf.buildChild(value, "ngram"),
      bf.buildChildOpt(value, "min"),
      bf.buildChildOpt(value, "max")
    )
}

case class Repeat(
  repeat: Expr,
  number: Option[Expr]
) extends Expr {
  override val formKeys = Repeat.formKeys
}

object Repeat {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("repeat"), Form.Key.Optional("number"))
  Form.add("Repeat", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Repeat(bf.buildChild(value, "repeat"), bf.buildChildOpt(value, "number"))
}

case class ReplaceStr(
  replacestr: Expr,
  find: Expr,
  replace: Expr
) extends Expr {
  override val formKeys = ReplaceStr.formKeys
}

object ReplaceStr {

  val formKeys: List[Form.Key] =
    List("replacestr", "find", "replace").map(Form.Key.Required(_))
  Form.add("ReplaceStr", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
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
) extends Expr {
  override val formKeys = ReplaceStrRegex.formKeys
}

object ReplaceStrRegex {

  val formKeys: List[Form.Key] = List("replacestrregex", "pattern", "replace").map(
    Form.Key.req
  ) ++ List(Form.Key.opt("first"))
  Form.add("ReplaceStrRegex", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ReplaceStrRegex(
      bf.buildChild(value, "replacestrregex"),
      bf.buildChild(value, "pattern"),
      bf.buildChild(value, "replace"),
      bf.buildChildOpt(value, "first")
    )
}

case class Rtrim(rtrim: Expr) extends Expr {
  override val formKeys = Rtrim.formKeys
}

object Rtrim {
  val formKeys: List[Form.Key] = List("rtrim").map(Form.Key.Required(_))
  Form.add("Rtrim", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Rtrim(bf.buildChild(value, "rtrim"))
}

case class Space(space: Expr) extends Expr {
  override val formKeys = Space.formKeys
}

object Space {
  val formKeys: List[Form.Key] = List("space").map(Form.Key.Required(_))
  Form.add("Space", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Space(bf.buildChild(value, "space"))
}

case class SubString(
  substring: Expr,
  start: Expr,
  length: Option[Expr]
) extends Expr {
  override val formKeys = SubString.formKeys
}

object SubString {

  val formKeys
    : List[Form.Key] = List("substring", "start").map(Form.Key.req) ++ List(
    Form.Key.Optional("length")
  )
  Form.add("SubString", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    SubString(
      bf.buildChild(value, "substring"),
      bf.buildChild(value, "start"),
      bf.buildChildOpt(value, "length")
    )
}

case class TitleCase(titlecase: Expr) extends Expr {
  override val formKeys = TitleCase.formKeys
}

object TitleCase {
  val formKeys: List[Form.Key] = List("titlecase").map(Form.Key.Required(_))
  Form.add("TitleCase", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    TitleCase(bf.buildChild(value, "titlecase"))
}

case class Trim(trim: Expr) extends Expr {
  override val formKeys = Trim.formKeys
}

object Trim {
  val formKeys: List[Form.Key] = List("trim").map(Form.Key.Required(_))
  Form.add("Trim", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Trim(bf.buildChild(value, "trim"))
}

case class Uppercase(uppercase: Expr) extends Expr {
  override val formKeys = Uppercase.formKeys
}

object Uppercase {
  val formKeys: List[Form.Key] = List("uppercase").map(Form.Key.Required(_))
  Form.add("Uppercase", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Uppercase(bf.buildChild(value, "uppercase"))
}

case class Format(
  format: Expr,
  values: Option[Expr]
) extends Expr {
  override val formKeys = Format.formKeys
}

object Format {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("format"), Form.Key.Optional("values"))
  Form.add("Format", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Format(bf.buildChild(value, "format"), bf.buildChildOpt(value, "values"))
}
