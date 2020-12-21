import java.io.File

fun main() {
    val food_regex = """(\w+( \w+)*) \(contains (\w+(, \w+)*)\)""".toRegex()
    val foods = File("21.in").readLines().map { food_regex.find(it)!!.groups } .map { Pair(it[1]!!.value.split(" "), it[3]!!.value.split(", ")) }
    val ingredients = mutableSetOf<String>()
    foods.forEach { ingredients.addAll(it.first) }
    
    val unknowns = mutableMapOf<String, Set<String>>()
    foods.forEach { it ->
        it.second.forEach { it2 ->
            unknowns[it2] = unknowns.getOrDefault(it2, ingredients.toSet()) intersect it.first.toSet()
        }
    }

    val q = ArrayDeque<String>(0)
    for (i in unknowns.keys) {
        if (unknowns[i]!!.size == 1) {
            q.add(i)
        }
    }

    while (!q.isEmpty()) {
        val x = q.removeFirst()
        for (i in unknowns.keys) {
            if (unknowns[i]!!.size > 1) {
                unknowns[i] = unknowns[i]!! subtract unknowns[x]!!
                if (unknowns[i]!!.size == 1) {
                    q.add(i)
                }
            }
        }
    }

    println("${unknowns.toSortedMap().values.map { it.iterator().next() }.joinToString(",")}")
}