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
    val graph = ids.associate { it to mutableListOf<Pair<String, Int>>() } .toMutableMap()
    
    for (rule in rules) {
        for (i in 1..(rule.size - 1)) {
            if ((rule[i] != "") and (rule[i] != "no other")) {
                graph[rule[0]]!!.add(Pair(rule[i].substring(2), rule[i].substring(0, 1).toInt()))
            }
        }
    }

    val num_bags = ids.associate { it to -1 } .toMutableMap()
    dfs(graph, num_bags, shinygold)

    println("${num_bags[shinygold]!! - 1}")
}

fun dfs(graph: MutableMap<String, MutableList<Pair<String, Int>>>, num_bags: MutableMap<String, Int>, curr: String): Int {
    if (num_bags[curr]!! > -1) {
        return num_bags[curr]!!
    }

    num_bags[curr] = 1

    for ((next, count) in graph[curr]!!) {
        num_bags[curr] = num_bags[curr]!! + dfs(graph, num_bags, next) * count
    }

    return num_bags[curr]!!
}