package fauna.tool.ast

import org.scalatest._

import fauna.tool.ast.Effect.{ Pure, Read, Write }

class EffectSpec extends FunSuite with Matchers {

  test("Effect should be commutative") {
    Write + Write shouldEqual Write
    Write + Read shouldEqual Write
    Write + Pure shouldEqual Write

    Read + Read shouldEqual Read
    Read + Write shouldEqual Write
    Read + Pure shouldEqual Read

    Pure + Pure shouldEqual Pure
    Pure + Read shouldEqual Read
    Pure + Write shouldEqual Write
  }

}
