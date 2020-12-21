import java.io.File

fun main() {
    val food_regex = """((\w+ )*)\(contains (\w+(, \w+)*)\)""".toRegex()
    val foods = File("21.in").readLines().map { food_regex.find(it)!!.groups } .map { Pair(it[1]!!.value.split(" "), it[3]!!.value.split(", ")) }
    val ingredients = mutableSetOf<String>()
    foods.forEach { ingredients.addAll(it.first) }
    
    val unknowns = mutableMapOf<String, Set<String>>()
    foods.forEach { it ->
        it.second.forEach { it2 ->
            unknowns[it2] = unknowns.getOrDefault(it2, ingredients.toSet()) intersect it.first.toSet()
        }
    }
    val contaminants = unknowns.values.fold(setOf<String>()) { acc, e -> acc union e }

    println("${foods.fold(0) { acc, e -> acc + e.first.count { it -> it !in contaminants } } }")
}