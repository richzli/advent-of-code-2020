import java.io.File

fun main() {
    val rules = File("7.in").readLines().map { it.split(" bags contain ", " bags, ", " bag, ", " bags.", " bag.") }
    val types = mutableSetOf<String>()

    for (rule in rules) {
        types.add(rule[0])
        for (i in 1..(rule.size - 1)) {
            if ((rule[i] != "") and (rule[i] != "no other")) {
                types.add(rule[i].substring(2))
            }
        }
    }

    val ids = types.toList()
    val shinygold = "shiny gold"
    val graph = ids.associate { it to mutableListOf<String>() } .toMutableMap()
    
    for (rule in rules) {
        for (i in 1..(rule.size - 1)) {
            if ((rule[i] != "") and (rule[i] != "no other")) {
                graph[rule[i].substring(2)]!!.add(rule[0])
            }
        }
    }

    val visited = ids.associate { it to false } .toMutableMap()
    dfs(graph, visited, shinygold)

    println("${visited.values.count { it } - 1}")
}

fun dfs(graph: MutableMap<String, MutableList<String>>, visited: MutableMap<String, Boolean>, curr: String) {
    if (visited[curr]!!) {
        return
    }

    visited[curr] = true

    for (next in graph[curr]!!) {
        dfs(graph, visited, next)
    }
}