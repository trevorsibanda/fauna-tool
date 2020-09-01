package fauna.tool.js

import scala.collection.mutable

import fauna.tool.ast._
import scala.scalajs.js.annotation._
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import fauna.tool.codegen._
import scala.util.Try
import fauna.tool.parser.uJsonSerializer

object Library {

  val internalError =
    js.Dictionary[String](("error", "Internal error. view console.log"))
  val noPayloadError = js.Dictionary[String](("error", "Payload not specified."))

  case class JsErrorException(error: js.Dictionary[String]) extends Exception

  def getCodegen(targetLanguage: String) = targetLanguage.toLowerCase match {
    case "curl"   => new CurlGenerator(new fauna.tool.parser.uJsonSerializer())
    case "go"     => new GolangCodeGenerator()
    case "python" => new PythonCodeGenerator()
    case "js"     => new JSCodeGenerator()
  }

  @JSExportTopLevel("unwire")
  def unwire(params: js.Dictionary[String]): js.Dictionary[String] =
    Try {
      Expr.init
      val payload = params.get("wire")
      val format = params.getOrElse("format", "json")
      val targetLanguage = params.getOrElse("lang", "js")

      payload match {
        case None => noPayloadError
        case Some(input) =>
          implicit val builder = format.toLowerCase match {
            case "fql"  => new fauna.tool.parser.FQLBuilder()
            case "json" => new fauna.tool.parser.JsonBuilder()
          }

          val cgen = getCodegen(targetLanguage)
          val expr = builder.build(input)
          val serializer = new uJsonSerializer()
          val json = serializer.encodeString(expr)
          js.Dictionary("code" -> cgen.exprToCode(expr), "wire" -> json)
      }
    }.recover({
        case e: JsErrorException => e.error
        case _: Exception        => internalError
      })
      .getOrElse(noPayloadError)

  @JSExportTopLevel("highlightError")
  def highlightError(
    lang: String,
    method: String,
    wire: String,
    errors: js.Array[js.Dictionary[String]]
  ): js.Array[js.Dictionary[String]] =
    Try {
      errors.collect {
        case dict: js.Dictionary[String]
            if Seq("code", "description", "position")
              .filterNot(dict.isDefinedAt)
              .isEmpty =>
          val pos = (dict("position").asInstanceOf[js.Array[scala.Any]]).collect {
            case s: String => s
            case i: Int    => i.toString
          }
          highlightError(lang, method, wire, pos, dict("code"), dict("description"))
      }
    }.recover({
        case e: JsErrorException => js.Array(e.error)
        case _: Exception        => js.Array(internalError)
      })
      .getOrElse(js.Array())

  def highlightError(
    lang: String,
    method: String,
    wire: String,
    position: js.Array[String],
    code: String,
    description: String
  ): js.Dictionary[String] =
    Try {
      Expr.init
      (wire, position) match {
        case (null, _) => noPayloadError
        case (_, null) => js.Dictionary(("error", "Error has no position"))
        case (input, pos) => {
          val serializer = new fauna.tool.parser.uJsonSerializer()
          (
            serializer.readValuePath(
              serializer.valueFromString(input).get,
              position.toSeq
            ),
            serializer.readValuePath(
              serializer.valueFromString(input).get,
              if (position.isEmpty) position.toSeq else position.toSeq.init
            )
          ) match {
            case (Some(jValue), Some(highlight)) => {
              val builder = new fauna.tool.parser.JsonBuilder()
              val cgen = getCodegen(lang)
              val res = cgen.exprToCode(builder.build(jValue))
              val highlightedCode = cgen.exprToCode(builder.build(highlight))
              js.Dictionary(
                "pos" -> position.mkString("/"),
                "code" -> code,
                "description" -> description,
                "highlight" -> highlightedCode,
                "error" -> res,
                "wire" -> wire,
                "query" -> unwire(
                  js.Dictionary(
                    "wire" -> wire,
                    "format" -> "json",
                    "lang" -> lang
                  )
                ).getOrElse("code", null)
              )
            }
            case (_, _) =>
              js.Dictionary(
                "error" -> s"Position $position does not exist in query $wire"
              )
          }
        }
      }
    }.recover({
        case e: JsErrorException => e.error
        case _: Exception        => internalError
      })
      .getOrElse(internalError)

  @JSExportTopLevel("queryEffect")
  def effect(params: js.Dictionary[String]): js.Dictionary[String] =
    Try {
      Expr.init
      val wire: String = params("wire")
      val format: String = params.getOrElse("format", "fql")
      val targetLang: String = params.getOrElse("lang", "js")

      implicit val builder = format.toLowerCase match {
        case "fql"  => new fauna.tool.parser.FQLBuilder()
        case "json" => new fauna.tool.parser.JsonBuilder()
      }

      val cgen = getCodegen(targetLang)

      val expr: Expr = builder.build(wire)
      val code = cgen.exprToCode(expr)
      val eff = expr.effect.toString

      js.Dictionary("code" -> code, "effect" -> eff, "wire" -> wire)

    }.recover({
        case e: JsErrorException => e.error
        case _: Exception        => internalError
      })
      .getOrElse(noPayloadError)

  @JSExportTopLevel("optimizeQuery")
  def optimizeQuery(payload: String): js.Dictionary[String] = {
    Expr.init
    js.Dictionary("error" -> "Not yet implemented")
  }

  @JSExportTopLevel("usage")
  def usage(functionName: String): js.Dictionary[String] = {
    Expr.init
    js.Dictionary("error" -> "Not yet implemented")
  }

}
