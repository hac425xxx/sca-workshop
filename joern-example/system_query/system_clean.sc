def clean_data_filter(a: Any): Boolean =  {
    if(a.asInstanceOf[AstNode].astParent.isCall)
    {
        if(a.asInstanceOf[AstNode].astParent.asInstanceOf[Call].name == "clean_data")
            return true
    }
    return false
}

def filter_path_for_clean_data(a: Any) = a match {
    case a: Path => a.elements.l.filter(clean_data_filter).size > 0
    case _ => false
}

def getFlow() = {
    val src = cpg.call.name("get_user_input_str")
    val sink = cpg.call.name("system").argument.order(1)
    sink.reachableByFlows(src).filterNot(filter_path_for_clean_data)
}
