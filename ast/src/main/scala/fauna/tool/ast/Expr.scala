package fauna.tool.ast

//import fauna.tool.validator.{ Constraint, ValidationResult }

import scala.collection.mutable
import fauna.tool.ast.Form.Key

abstract class Expr extends Product {

  val formKeys: Seq[Form.Key] = Nil

  def name: String = this.productPrefix

  def arity: Arity = {
    var args, optional: Int = 0
    formKeys map {
      case Key.Optional(_) => optional += 1; args += 1
      case Key.Required(_) => args += 1
    }
    if (optional > 0) Arity.Between(args - optional, args) else Arity.Exact(args)
  }

  def children: Seq[Option[Expr]] = arity match {
    case Arity.Exact(e) =>
      for (i <- (0 until e); elem = this.productElement(i).asInstanceOf[Expr])
        yield Some(elem)
    case Arity.VarArgs =>
      this.productElement(0).asInstanceOf[Expr] match {
        case ArrayL(l) => l.map(Some(_))
        case x         => Seq(Some(ArrayL(x)))
      }
    case Arity.Between(min, max) => {
      val required: Seq[Option[Expr]] =
        for (i <- (0 until min); elem = this.productElement(i).asInstanceOf[Expr])
          yield Some(elem)
      val optional: Seq[Option[Expr]] =
        for (i <- (min until max);
             elem = this.productElement(i).asInstanceOf[Option[Expr]]) yield elem
      required.:++(optional)
    }
  }

  def forEachChildren[T](fn: Expr => T): Unit = {
    fn(this)
    this.children.collect {
      case Some(e) => e.forEachChildren(fn)
    }
  }

  def contains(e: Expr, pred: Expr => Boolean) = Expr.contains(this, e)(pred)

  /*
  def constraints: Map[String, Set[Constraint]] = Map()

  //Returns a new instance of self with random values
  def randomSelf(implicit r: fauna.tool.fuzzer.RandomGenerator): Expr = ???

//Returns a new expression with random children up to depth
  def random(depth: Int)(implicit r: fauna.tool.fuzzer.RandomGenerator): Expr = ???
   */
  def encode[T](implicit e: Encoder[T]): Option[T] = e.encode(this)
  /*
  def validate: Set[ValidationResult] = {

    classAccessors
      .zip(children)
      .collect {
        case (tpl, Some(expr)) if constraints.isDefinedAt(tpl._1) =>
          this.validate(expr, constraints(tpl._1))
      }
      .flatten
      .toSet
  }

  private def validate(
    expr: Expr,
    constraints: Set[Constraint]
  ): Set[ValidationResult] = constraints.map { c =>
    c.check(expr)
  }
   */
  def build[T](value: T)(implicit bf: Builder[T]): Expr = ???

  //override this
  def effect: Effect = effect(Effect.Pure)

  private[ast] def effect(effect_ : Effect): Effect = {
    children.toList
      .collect { case Some(c) => c }
      .foldLeft(effect_)((acc, child) => {
        child.effect + acc
      })
  }

  def evaluatesTo: Type = AnyT
}

object Expr {

  def build[T](value: T)(implicit bf: Builder[T]): Expr = bf.build(value)

  def buildOpt[T](value: T)(implicit bf: Builder[T]): Option[Expr] =
    bf.buildOpt(value)

  private val allowAllPred: Expr => Boolean = _ => true

  def contains(expr1: Expr, expr2: Expr)(
    pred: Expr => Boolean = allowAllPred
  ): Boolean = {
    val p = mutable.ArrayBuilder.make[Expr]
    val c = mutable.ArrayBuilder.make[Expr]
    expr1.forEachChildren(e => if (pred(e)) p.addOne(e))
    expr2.forEachChildren(e => if (pred(e)) c.addOne(e))

    p.result.containsSlice(c.result)
  }

  def init = {
    ()
  }

  //Nulled instances of all known Expr
  //scala Objects are lazy, function forms are registered
  private[tool] val knownExprs: Seq[Expr] = Seq(
    Var(NullL),
    Max(NullL),
    Min(NullL),
    QueryV(NullL),
    SetV(NullL),
    DateV(NullL),
    BytesV(NullL),
    TimestampV(NullL),
    Ref2(NullL, None, None),
    Ref(NullL),
    Let(NullL, NullL),
    ForEach(NullL, NullL),
    Merge(NullL, NullL, None),
    Lambda(NullL, NullL),
    Reduce(NullL, NullL, NullL),
    MapFn(NullL, NullL),
    Call(NullL, None),
    If(NullL, NullL, NullL),
    Filter(NullL, NullL),
    Do(NullL)
    //Comparison
    ,
    LT(NullL),
    LTE(NullL),
    GT(NullL),
    GTE(NullL)

    //collection
    ,
    Prepend(NullL, NullL),
    Append(NullL, NullL),
    Take(NullL, NullL),
    Drop(NullL, NullL),
    IsEmpty(NullL),
    IsNonEmpty(NullL)

    //string
    ,
    Concat(NullL, None),
    CaseFold(NullL, None),
    RegexEscape(NullL),
    StartsWith(NullL, NullL),
    EndsWith(NullL, NullL),
    ContainsStr(NullL, NullL),
    ContainsStrRegex(NullL, NullL),
    FindStr(NullL, NullL, None),
    FindStrRegex(NullL, NullL, None, None),
    Length(NullL),
    LowerCase(NullL),
    LTrim(NullL),
    NGram(NullL, None, None),
    Repeat(NullL, None),
    ReplaceStr(NullL, NullL, NullL),
    ReplaceStrRegex(NullL, NullL, NullL, None),
    Rtrim(NullL),
    Space(NullL),
    SubString(NullL, NullL, None),
    TitleCase(NullL),
    Trim(NullL),
    Uppercase(NullL),
    Format(NullL, None)

    //Numerics
    ,
    Abs(NullL),
    Add(NullL),
    BitAnd(NullL),
    BitNot(NullL),
    BitOr(NullL),
    BitXor(NullL),
    Ceil(NullL),
    Divide(NullL),
    Floor(NullL),
    Modulo(NullL),
    Multiply(NullL),
    Round(NullL, None),
    Subtract(NullL),
    Sign(NullL),
    Sqrt(NullL),
    Trunc(NullL, None),
    Mean(NullL),
    Count(NullL),
    Sum(NullL),
    Any(NullL),
    All(NullL)

    //trig
    ,
    ACos(NullL),
    ASin(NullL),
    ATan(NullL),
    Cos(NullL),
    Cosh(NullL),
    Degrees(NullL),
    Exp(NullL),
    Hypot(NullL, None),
    Ln(NullL),
    Log(NullL),
    Pow(NullL, None),
    Radians(NullL),
    Sin(NullL),
    Sinh(NullL),
    Tan(NullL),
    Tanh(NullL)

    //bool
    ,
    And(NullL),
    Or(NullL),
    Not(NullL)

    //refs

    // Native classref constructors
    ,
    Databases(NullL),
    Indexes(NullL),
    Classes(NullL),
    Collections(NullL),
    Keys(NullL),
    Tokens(NullL),
    Credentials(NullL),
    Functions(NullL),
    Roles(NullL)

    // Set ref constructors
    ,
    Singleton(NullL),
    Events(NullL),
    Index(NullL, None),
    Match(NullL, None),
    Union(NullL),
    Intersection(NullL),
    Difference(NullL),
    Distinct(NullL),
    Range(NullL, NullL, NullL)

    //reads
    ,
    Exists(NullL, None),
    Get(NullL, None),
    KeyFromSecret(NullL),
    Paginate(NullL, None, None, None, None, None, None, None),
    //writes
    Create(NullL, None),
    Update(NullL, None),
    Replace(NullL, None),
    Delete(NullL),
    CreateClass(NullL),
    CreateCollection(NullL),
    CreateDatabase(NullL),
    CreateIndex(NullL),
    CreateKey(NullL),
    CreateFunction(NullL),
    CreateRole(NullL),
    Insert(NullL, NullL, NullL, None),
    Remove(NullL, NullL, NullL),
    MoveDatabase(NullL, NullL)

    //auth
    ,
    Identity(NullL),
    HasIdentity(NullL),
    Identify(NullL, NullL),
    Login(NullL, NullL),
    Logout(NullL)

    //time and date
    ,
    Time(NullL),
    Now(NullL),
    Epoch(NullL, NullL),
    Date(NullL),
    ToMicros(NullL),
    ToMillis(NullL),
    ToSeconds(NullL),
    Second(NullL),
    Minute(NullL),
    Hour(NullL),
    DayOfMonth(NullL),
    DayOfWeek(NullL),
    DayOfYear(NullL),
    Month(NullL),
    Year(NullL),
    TimeAdd(NullL, NullL, NullL),
    TimeSubtract(NullL, NullL, NullL),
    TimeDiff(NullL, NullL, NullL)

    //conversion
    ,
    ToString(NullL),
    ToNumber(NullL),
    ToTime(NullL),
    ToDate(NullL),
    ToObject(NullL),
    ToArray(NullL)

    //select
    ,
    Abort(NullL),
    Equals(NullL),
    Contains(NullL, NullL),
    Select(NullL, NullL, None),
    SelectAll(NullL, NullL, None),
    SelectAsIndex(NullL, NullL, None),
    Class(NullL, None)
    //case class Ref(ref, id, scope: Option[Expr])
    ,
    Database(NullL, None),
    Collection(NullL, None),
    Function(NullL, None),
    Role(NullL, None)
    //deprecated, use new_id
    ,
    NextID(NullL),
    NewID(NullL),
    ObjectExpr(NullL)
  )

}
