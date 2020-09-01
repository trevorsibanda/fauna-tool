package fauna.tool.ast

//reads

case class Exists(
  exists: Expr,
  ts: Option[Expr]
) extends Expr {
  override val formKeys = Exists.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object Exists {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("exists"), Form.Key.Optional("ts"))
  Form.add("Exists", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Exists(bf.buildChild(value, "exists"), bf.buildChildOpt(value, "ts"))

}

case class Get(get: Expr, ts: Option[Expr]) extends Expr {
  override val formKeys = Get.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object Get {
  val formKeys: List[Form.Key] = List(Form.Key.req("get"), Form.Key.opt("ts"))
  Form.add("Get", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Get(bf.buildChild(value, "get"), bf.buildChildOpt(value, "ts"))

}

case class KeyFromSecret(key_from_secret: Expr) extends Expr {
  override val formKeys = KeyFromSecret.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object KeyFromSecret {
  val formKeys: List[Form.Key] = List("key_from_secret").map(Form.Key.Required(_))
  Form.add("KeyFromSecret", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    KeyFromSecret(bf.buildChild(value, "key_from_secret"))

}

case class Documents(documents: Expr) extends Expr {
  override val formKeys = Documents.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object Documents {
  val formKeys: List[Form.Key] = List("documents").map(Form.Key.Required(_))
  Form.add("Documents", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Documents(bf.buildChild(value, "documents"))

}

case class Paginate(
  val paginate: Expr,
  val before: Option[Expr],
  val after: Option[Expr],
  val cursor: Option[Expr],
  val ts: Option[Expr],
  val size: Option[Expr],
  val events: Option[Expr],
  val sources: Option[Expr]
) extends Expr {
  override def name = "Paginate"

  override def effect: Effect = effect(Effect.Read)

  override val formKeys = Paginate.formKeys
}

object Paginate {

  val formKeys: List[Form.Key] = List(Form.Key.Required("paginate")) ++ List(
    "before",
    "after",
    "cursor",
    "ts",
    "size",
    "events",
    "sources"
  ).map(Form.Key.Optional(_))
  Form.add("Paginate", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr = Paginate(
    bf.buildChild(value, "paginate"),
    bf.buildChildOpt(value, "before"),
    bf.buildChildOpt(value, "after"),
    bf.buildChildOpt(value, "cursor"),
    bf.buildChildOpt(value, "ts"),
    bf.buildChildOpt(value, "size"),
    bf.buildChildOpt(value, "events"),
    bf.buildChildOpt(value, "sources")
  )
}

case class Select(
  select: Expr,
  from: Expr,
  default: Option[Expr]
) extends Expr {
  override val formKeys = Select.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object Select {

  val formKeys
    : List[Form.Key] = List("select", "from").map(Form.Key.Required(_)) ++ List(
    Form.Key.opt("default")
  )
  Form.add("Select", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Select(
      bf.buildChild(value, "select"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

}

case class SelectAll(
  select_all: Expr,
  from: Expr,
  default: Option[Expr]
) extends Expr {
  override val formKeys = SelectAll.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object SelectAll {

  val formKeys
    : List[Form.Key] = List("select_all", "from").map(Form.Key.Required(_)) ++ List(
    Form.Key.opt("default")
  )
  Form.add("SelectAll", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    SelectAll(
      bf.buildChild(value, "select_all"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

}

case class SelectAsIndex(
  select: Expr,
  from: Expr,
  default: Option[Expr]
) extends Expr {
  override val formKeys = SelectAsIndex.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object SelectAsIndex {

  val formKeys: List[Form.Key] = List("select_as_index", "from").map(
    Form.Key.Required(_)
  ) ++ List(Form.Key.opt("default"))
  Form.add("SelectAsIndex", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    SelectAsIndex(
      bf.buildChild(value, "select_as_index"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

}
