package fauna.tool.fuzzer

import fauna.tool.ast._
import scala.util.Random

class BasicRandGen(
  allExprs: Seq[Expr],
  maxKeys: Int = 16,
  maxArr: Int = 64,
  maxIdentifier: Int = 64,
  maxString: Int = 255
) extends RandomGenerator {

  val literals: Seq[Literal] = Seq(
    StringL(""),
    IntL(0),
    DoubleL(0),
    FloatL(0),
    TrueL,
    FalseL,
    NullL,
    DecimalL(0.00)
  )

  val effectMapped: Map[Effect, Seq[Expr]] = allExprs.groupBy(_.effect)

  private def randomItem[T](s: Seq[T]): T = s(Random.between(0, s.size))

  def randomLiteral: Literal = randomItem(literals) match {
    case _: StringL =>
      StringL(Random.alphanumeric.take(Random.between(0, maxString)).mkString)
    case _: IntL     => IntL(Random.nextInt)
    case _: BooleanL => BooleanL(Random.nextBoolean)
    case _: DecimalL => DecimalL(Random.nextDouble)
    case _: FloatL   => FloatL(Random.nextFloat)
    case _: DoubleL  => DoubleL(Random.nextDouble)
    case NullL       => NullL
  }

  def randomObjOrArr = Random.nextBoolean match {
    case true => ArrayL((0 to Random.between(1, maxArr)).map(_ => random).toList)
    case false =>
      ObjectL(
        (0 to Random.between(1, maxKeys)).map(_ => (randomIdentifier, random)).toMap
      )
  }

  def randomIdentifier: String =
    s""""${Random.alphanumeric.take(Random.between(0, maxString)).mkString}""""

  def randomPureFn: Expr = randomItem(effectMapped(Effect.Pure))

  def randomReadFn: Expr = randomItem(effectMapped(Effect.Read))

  def randomWriteFn: Expr = randomItem(effectMapped(Effect.Write))

  val items =
    Seq(() => randomLiteral, () => randomObjOrArr, () => randomItem(allExprs))

  def random: Expr = {
    randomItem(items)()
  }

  def withArity(arity: Arity): Seq[Expr] = allExprs collect {
    case e: Expr if e.arity == arity => e
  }
}
