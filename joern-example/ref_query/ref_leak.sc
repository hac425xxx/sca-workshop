def search() = {
    var func = cpg.method.name("ref_leak")
    var true_block = func.controlStructure.whenTrue.l
    var false_block = func.controlStructure.whenFalse.l

    var ref_count = 1
    
    if(true_block.size != 0)
    {
        var block = true_block(0)
        for (elem <- block.astChildren.l) {
            println(elem)
            if(elem.isCall && elem.asInstanceOf[Call].name == "ref_put")
            {
                println(elem.asInstanceOf[Call].code)
                ref_count -= 1
            }

            if(elem.isReturn)
            {
                println("detect return, current ref_count: " + ref_count)
            }
        }
    }

    
    // (true_block, false_block)
    true_block
}

search

