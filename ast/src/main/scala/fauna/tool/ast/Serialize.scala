package fauna.tool.ast

trait Encoder[T] {
  def encode(v: Expr): Option[T]
}

trait Decoder[T] {
  def decode(v: T): Option[Expr]
}

abstract class Serializer[T] extends Encoder[T] with Decoder[T] {}
