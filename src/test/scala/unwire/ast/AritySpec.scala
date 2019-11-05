package fauna.tool.ast

import org.scalatest._

import fauna.tool.ast.Arity.{VarArgs, Between, Exact}

class AritySpec extends FunSuite with Matchers {

    test("Arity"){
        VarArgs
        Between(1, 4)
        Exact(5)
    }

}
