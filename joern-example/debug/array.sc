import scala.util.control._

val loop = new Breaks;

def getFunction(name: String): Method = {
  return cpg.method.name(name).head.asInstanceOf[Method]
}

def get_all_array() = {
    cpg.call.name("<operator>.indirectIndexAccess")
}



def getEnclosingFunction(nd: AstNode): Method = {
  var node: AstNode = nd
  loop.breakable {
    while (true) {
      if (node.isMethod) {
        loop.break
      }
      node = node.astParent
    }
  }
  return node.asInstanceOf[Method]
}


def read_byte_call_mapfunc(caller: Call) = {
  var f = getEnclosingFunction(caller.astNode)
  var src = f.local.referencingIdentifiers
  val sink = cpg.call.name("<operator>.indirectIndexAccess").argument
  sink.reachableByFlows(src).p
}


def local_var_to_index() = {
    cpg.call.name("read_byte").map(read_byte_call_mapfunc)
}
local_var_to_index

get_all_array.l
