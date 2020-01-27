package fauna.tool.ast

class StringFunctionsSpec extends ExprSuite {

//string
  test("Concat()") {
    val expr = Concat(concat = NullL, separator = Some(NullL))
    assertExpr(expr, "Concat", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("CaseFold()") {
    val expr = CaseFold(casefold = NullL, normalizer = Some(NullL))
    assertExpr(expr, "CaseFold", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("RegexEscape()") {
    val expr = RegexEscape(regexescape = NullL)
    assertExpr(expr, "RegexEscape", 1, Arity.Exact(1), Effect.Pure)
  }

  test("StartsWith()") {
    val expr = StartsWith(startswith = NullL, search = NullL)
    assertExpr(expr, "StartsWith", 2, Arity.Exact(2), Effect.Pure)
  }

  test("EndsWith()") {
    val expr = EndsWith(endswith = NullL, search = NullL)
    assertExpr(expr, "EndsWith", 2, Arity.Exact(2), Effect.Pure)
  }

  test("ContainsStr()") {
    val expr = ContainsStr(containsstr = NullL, search = NullL)
    assertExpr(expr, "ContainsStr", 2, Arity.Exact(2), Effect.Pure)
  }

  test("ContainsStrRegex()") {
    val expr = ContainsStrRegex(containsstrregex = NullL, pattern = NullL)
    assertExpr(expr, "ContainsStrRegex", 2, Arity.Exact(2), Effect.Pure)
  }

  test("FindStr()") {
    val expr = FindStr(findstr = NullL, find = NullL, start = Some(NullL))
    assertExpr(expr, "FindStr", 3, Arity.Between(2, 3), Effect.Pure)
  }

  test("FindStrRegex()") {
    val expr = FindStrRegex(
      findstrregex = NullL,
      pattern = NullL,
      start = Some(NullL),
      num_results = Some(NullL)
    )
    assertExpr(expr, "FindStrRegex", 4, Arity.Between(2, 4), Effect.Pure)
  }

  test("Length()") {
    val expr = Length(length = NullL)
    assertExpr(expr, "Length", 1, Arity.Exact(1), Effect.Pure)
  }

  test("LowerCase()") {
    val expr = LowerCase(lowercase = NullL)
    assertExpr(expr, "LowerCase", 1, Arity.Exact(1), Effect.Pure)
  }

  test("LTrim()") {
    val expr = LTrim(ltrim = NullL)
    assertExpr(expr, "LTrim", 1, Arity.Exact(1), Effect.Pure)
  }

  test("NGram()") {
    val expr = NGram(ngram = NullL, min = Some(NullL), max = Some(NullL))
    assertExpr(expr, "NGram", 3, Arity.Between(1, 3), Effect.Pure)
  }

  test("Repeat()") {
    val expr = Repeat(repeat = NullL, number = Some(NullL))
    assertExpr(expr, "Repeat", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("ReplaceStr()") {
    val expr = ReplaceStr(replacestr = NullL, find = NullL, replace = NullL)
    assertExpr(expr, "ReplaceStr", 3, Arity.Exact(3), Effect.Pure)
  }

  test("ReplaceStrRegex()") {
    val expr = ReplaceStrRegex(
      replacestrregex = NullL,
      pattern = NullL,
      replace = NullL,
      first = Some(NullL)
    )
    assertExpr(expr, "ReplaceStrRegex", 4, Arity.Between(3, 4), Effect.Pure)
  }

  test("Rtrim()") {
    val expr = Rtrim(rtrim = NullL)
    assertExpr(expr, "Rtrim", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Space()") {
    val expr = Space(space = NullL)
    assertExpr(expr, "Space", 1, Arity.Exact(1), Effect.Pure)
  }

  test("SubString()") {
    val expr = SubString(substring = NullL, start = NullL, length = Some(NullL))
    assertExpr(expr, "SubString", 3, Arity.Between(2, 3), Effect.Pure)
  }

  test("TitleCase()") {
    val expr = TitleCase(titlecase = NullL)
    assertExpr(expr, "TitleCase", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Trim()") {
    val expr = Trim(trim = NullL)
    assertExpr(expr, "Trim", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Uppercase()") {
    val expr = Uppercase(uppercase = NullL)
    assertExpr(expr, "Uppercase", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Format()") {
    val expr = Format(format = NullL, values = Some(NullL))
    assertExpr(expr, "Format", 2, Arity.Between(1, 2), Effect.Pure)
  }

}
