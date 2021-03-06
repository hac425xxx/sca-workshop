import scala.util.control._
val loop = new Breaks;

def ref_func_filter(caller: Call): Boolean =  {
    var node : AstNode = caller.astNode
    var block : Block = null
    var func : Method = null
    var ret : Boolean = false

    // println(node)

    loop.breakable{
        while(true)
        {
            if(node.isBlock)
            {
                block = node.asInstanceOf[Block]
            }

            if(node.isMethod)
            {
                func = node.asInstanceOf[Method]
                loop.break;
            }
            node = node.astParent
        }
    }

    // println(func)
    // println(block)


    var true_block = func.controlStructure.whenTrue.l
    var false_block = func.controlStructure.whenFalse.l
    var ref_count = 1

    if(true_block.size != 0)
    {
        var block = true_block(0)
        for (elem <- block.astChildren.l) {
            if(elem.isCall && elem.asInstanceOf[Call].name == "ref_put")
            {
                ref_count -= 1
            }

            if(elem.isReturn)
            {
                println("func_name: " + func.name + ", detect return expr, current ref_count: " + ref_count)
                if(ref_count != 0)
                {
                    ret = true
                }

            }
        }
    }

    return ret
}


def getEnclosingFunction(nd: AstNode): Method =  {
    var node : AstNode = nd
    loop.breakable{
        while(true)
        {
            if(node.isMethod)
            {
                loop.break
            }
            node = node.astParent
        }
    }
    return node.asInstanceOf[Method]
}

def search() = {
    var ref_get_callers = cpg.call.name("ref_get")
    ref_get_callers.filter(ref_func_filter).map(e => getEnclosingFunction(e.astNode))
}

search.l

