def getFlow() = {
    val src = cpg.call.name("get_user_input_str")
    val sink = cpg.call.name("system").argument.order(1)
    sink.reachableByFlows(src).p
}

@main def main() = {
    importCpg("cpg.bin")
    getFlow()
}