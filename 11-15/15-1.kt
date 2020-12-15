import java.io.File

fun main() {
    var turn = 0
    val history = mutableMapOf<Int, Int>()
    var last_number = 0

    val start = File("15.in").readText().split(",").map { it.toInt() }

    for (n in start) {
        last_number = n
        if (turn < start.lastIndex) history[n] = turn
        ++turn
    }

    while (turn < 2020) {
        val next_number = (turn - 1) - (history[last_number] ?: (turn - 1))
        history[last_number] = turn - 1
        last_number = next_number
        ++turn
    }

    println("$last_number")
}