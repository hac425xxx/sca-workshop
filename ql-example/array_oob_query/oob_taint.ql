import cpp
import semmle.code.cpp.dataflow.TaintTracking

class ArrayOOBCfg extends TaintTracking::Configuration {
  ArrayOOBCfg() { this = "ArrayOOBCfg" }

  override predicate isSource(DataFlow::Node source) {
    source.asExpr().(FunctionCall).getTarget().getName() = "read_byte"
  }

  override predicate isSink(DataFlow::Node sink) {
    exists(ArrayExpr ae | sink.asExpr() = ae.getArrayOffset())
  }
}

from DataFlow::PathNode sink, DataFlow::PathNode source, ArrayOOBCfg cfg
where cfg.hasFlowPath(source, sink)
select source.getNode().asExpr().(FunctionCall).getEnclosingFunction(), source, sink
