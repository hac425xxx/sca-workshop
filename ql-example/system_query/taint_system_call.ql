import cpp
import semmle.code.cpp.dataflow.TaintTracking

from FunctionCall system_call, FunctionCall user_input, DataFlow::Node source, DataFlow::Node sink
where
  system_call.getTarget().getName().matches("system") and
  user_input.getTarget().getName().matches("get_user_input_str") and
  sink.asExpr() = system_call.getArgument(0) and
  source.asExpr() = user_input and
  TaintTracking::localTaint(source, sink)
select user_input, user_input.getEnclosingFunction()
