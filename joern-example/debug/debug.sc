import scala.util.control._

val loop = new Breaks;

def getFunction(name: String): Method = {
  return cpg.method.name(name).head.asInstanceOf[Method]
}
getFunction("ref_leak").dotAst.l

def parseControl(control: ControlStructure, ref_count: Int) : Int = {

  println("In parseControl")

  var true_block = control.whenTrue.l
  var false_block = control.whenFalse.l
//   var ref_count : Int = 0

  if (true_block.size != 0) {
    var block = true_block(0)
    parseBlock(block.asInstanceOf[Block])
  }

  if (false_block.size != 0) {
    var block = false_block(0)
    parseBlock(block.asInstanceOf[Block])
  }

  return 1
}


def parseBlock(block: Block) : Int = {
    var node: AstNode = block.astNode
    var refcount : Int = 0;

    for(child <- node.astChildren) {
        println(child)
        if(child.isBlock) {
            parseBlock(child.asInstanceOf[Block])
        }

        if(child.isControlStructure) {
            parseControl(child.asInstanceOf[ControlStructure], refcount)
        }

        if (child.isCall) {
            
            println("call: " + child.asInstanceOf[Call].name)
            if (child.asInstanceOf[Call].name == "ref_put") 
                refcount -= 1

            if (child.asInstanceOf[Call].name == "ref_put") 
                refcount += 1
        }

        if (child.isReturn) {
            println("detect return expr, current refcount: " + refcount)
        }


        if(child.isReturn) {
            println("refcount: " + refcount)
        }

    }

    return refcount
}


def parseFunction(func: Method) : Int = {
    var refcount : Int = 0;
    for(child <- func.astChildren) {
        if(child.isBlock) {
            parseBlock(child.asInstanceOf[Block])
        }

        if(child.isReturn) {
            println("refcount: " + refcount)
        }

    }

    return refcount;
}


parseFunction(f)