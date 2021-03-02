import cpp
import semmle.code.cpp.dataflow.TaintTracking

predicate setSystemSink(FunctionCall fc, Expr e) {
  fc.getTarget().getName().matches("system") and
  fc.getArgument(0) = e
}

predicate setWrapperSystemSink(FunctionCall fc, Expr e) {
  fc.getTarget().getName().matches("our_wrapper_system") and
  fc.getArgument(0) = e
}

from FunctionCall fc, FunctionCall user_input, DataFlow::Node source, DataFlow::Node sink
where
  (
    setWrapperSystemSink(fc, sink.asExpr()) or
    setSystemSink(fc, sink.asExpr())
  ) and
  user_input.getTarget().getName().matches("get_user_input_str") and
  sink.asExpr() = fc.getArgument(0) and
  source.asExpr() = user_input and
  TaintTracking::localTaint(source, sink)
select user_input, user_input.getEnclosingFunction()
