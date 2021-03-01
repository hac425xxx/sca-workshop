import cpp
import semmle.code.cpp.dataflow.TaintTracking

class SystemCfg extends TaintTracking::Configuration {
  SystemCfg() { this = "SystemCfg" }

  override predicate isSource(DataFlow::Node source) {
    source.asExpr().(FunctionCall).getTarget().getName() = "get_user_input_str"
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
select source, sink
