package fauna.tool.validator

trait ValidationResult

case class ValidationError(key: String, messages: Seq[String])
    extends ValidationResult

case object ValidationSuccess extends ValidationResult
