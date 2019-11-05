package fauna.tool.ast

trait Effect {
  def +(e: Effect): Effect
}

object Effect {

  case object Pure extends Effect {
    override def +(e: Effect) = if (e == Pure) this else e
  }

  case object Read extends Effect {

    override def +(e: Effect) = e match {
      case Read | Pure => this
      case Write       => Write
    }
  }

  case object Write extends Effect {
    override def +(e: Effect) = this
  }
}
