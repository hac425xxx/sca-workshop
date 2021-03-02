import cpp
import semmle.code.cpp.dataflow.TaintTracking

class RefGetFunctionCall extends FunctionCall {
  RefGetFunctionCall() { this.getTarget().getName() = "ref_get" }
}

class RefPutFunctionCall extends FunctionCall {
  RefPutFunctionCall() { this.getTarget().getName() = "ref_put" }
}

class EvilIfStmt extends IfStmt {
  EvilIfStmt() {
    exists(RefPutFunctionCall rpfc |
      this.getAChild*() = rpfc and
      not exists(ReturnStmt rs | rpfc.getEnclosingBlock() = rs.getEnclosingBlock())
    )
  }
}

from RefGetFunctionCall rgfc, EvilIfStmt eifs
where eifs.getEnclosingFunction() = rgfc.getEnclosingFunction()
select eifs.getEnclosingFunction(), eifs
