import cpp

from FunctionCall fc, FunctionCall clean_fc
where
  fc.getTarget().getName().matches("system") and
  not fc.getArgument(0).isConstant() and
  clean_fc.getTarget().getName().matches("clean_data") and
  not clean_fc.getEnclosingFunction() = fc.getEnclosingFunction()
select fc.getEnclosingFunction(), fc, fc.getArgument(0)
