import cpp
import semmle.code.cpp.dataflow.TaintTracking
import semmle.code.cpp.valuenumbering.GlobalValueNumbering

class SystemCfg extends TaintTracking::Configuration {
  SystemCfg() { this = "SystemCfg" }

  override predicate isSource(DataFlow::Node source) {
    source.asExpr().(FunctionCall).getTarget().getName() = "get_user_input_str"
  }

  override predicate isSanitizer(DataFlow::Node nd) {
    exists(FunctionCall fc |
      fc.getTarget().getName() = "clean_data" and
      globalValueNumber(fc.getArgument(0)) = globalValueNumber(nd.asExpr())
    )
  }

  override predicate isSink(DataFlow::Node sink) {
    exists(FunctionCall call |
      sink.asExpr() = call.getArgument(0) and
      call.getTarget().getName() = "system"
    )
  }
}

from DataFlow::PathNode sink, DataFlow::PathNode source, SystemCfg cfg
where cfg.hasFlowPath(source, sink)
select source.getNode().asExpr().(FunctionCall).getEnclosingFunction(), source, sink
