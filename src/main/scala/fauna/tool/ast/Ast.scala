//package fauna.tool.ast

import org.json4s.JsonAST.JString
import org.json4s.JsonAST.JArray
import java.lang.reflect.Field
import org.json4s.JsonAST.JLong
import org.json4s.JsonAST.JNull
import org.json4s.JsonAST.JInt
import org.json4s.JsonAST.JDouble
import org.json4s.JsonAST.JDecimal
import org.json4s.JsonAST.JBool
import org.json4s.JsonAST.JNothing
import org.json4s.JsonAST.JObject
import org.json4s.JsonAST.JValue
import com.fasterxml.jackson.module.scala.deser.`package`.overrides

//type JsonKey = String
case class JsonKey(key: String, optional: Boolean = false)
