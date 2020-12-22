import java.io.File

fun main() {
    val (p1, p2) = File("22.in").readText().split("\n\n").map { it.split("\n").drop(1).mapNotNull { it2 -> it2.toIntOrNull() } .toMutableList() }

    while (!p1.isEmpty() and !p2.isEmpty()) {
        val first = p1.removeFirst()
        val second = p2.removeFirst()

        if (first > second) {
            p1.addAll(listOf(first, second))
        } else {
            p2.addAll(listOf(second, first))
        }
    }

    if (p1.isEmpty()) {
        println("$p2")
        println("${p2.foldIndexed(0L) { idx, acc, e -> acc + e * (p2.size - idx) } }")
    } else {
        println("$p1")
        println("${p1.foldIndexed(0L) { idx, acc, e -> acc + e * (p1.size - idx) } }")
    }
}