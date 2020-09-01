package fauna.tool.ast

import fauna.tool.ast.Builder

import collection.mutable

object Form {
  type KeyName = String
  type Keys = Set[KeyName]

  type FormBuilder[T] = (T, Builder[T]) => Expr

  private var routes: mutable.Map[Keys, FormBuilder[scala.Any]] = mutable.Map()
  //map function names to keys
  private var fnNameKeyMap: mutable.Map[String, Seq[Keys]] = mutable.Map()

  abstract class Key(val key: String, val optional: Boolean)
  case class Value[T](value: T)

  object Key {
    case class Optional(name: String) extends Key(name, true)
    case class Required(name: String) extends Key(name, false)

    def opt(name: String) = Key.Optional(name)

    def req(name: String) = Key.Required(name)
  }

  def add(name: String, builder: FormBuilder[scala.Any], keys: Form.Key*): Unit = {
    val keySet: List[Form.Key] = keys.toList
    val (required, opt) = keySet.partition {
      case Key.Required(_) => true
      case _               => false
    }
    if (opt.isEmpty) {
      val keys = keySet.map(_.key).toSet
      routes.put(keys, builder)
      fnNameKeyMap.put(
        name.toLowerCase,
        fnNameKeyMap.getOrElse(name, Nil) ++ Seq(keys)
      )
    } else {
      val keys = required.map(_.key).toSet
      routes.put(keys, builder)
      fnNameKeyMap.put(
        name.toLowerCase,
        fnNameKeyMap.getOrElse(name, Nil) ++ Seq(keys)
      )
      (1 to opt.length).foreach { i =>
        opt.combinations(i).foreach { c: List[Form.Key] =>
          val keys = (required ++ c).map(_.key).toSet
          routes.put(keys, builder)
          fnNameKeyMap
            .put(name.toLowerCase, fnNameKeyMap.getOrElse(name, Nil) ++ Seq(keys))
        }
      }
    }
  }

  def matches(keys: Set[String]): Option[FormBuilder[scala.Any]] = {
    routes.get(keys)
  }

  def functionKeys(name: String): Option[Seq[Keys]] =
    fnNameKeyMap.get(name.toLowerCase)

  def matchesArguments(
    fnName: String,
    args: Seq[Expr]
  ): Option[(FormBuilder[scala.Any], Set[(KeyName, Expr)])] =
    functionKeys(fnName) match {
      case None => None
      case Some(keys) =>
        val none: Option[(FormBuilder[scala.Any], Set[(KeyName, Expr)])] = None
        keys
          .find { key =>
            (key.size == args.size)
          }
          .fold(none)(
            x =>
              Some(
                (
                  matches(x).getOrElse(
                    throw new Exception("Keys defined but has no builder")
                  ),
                  x.zip(args)
                )
              )
          )
    }

}
