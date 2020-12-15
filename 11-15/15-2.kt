import java.io.File

fun main() {
    var turn = 0
    val history = IntArray(30000000) { -1 }
    var last_number = 0

    val start = File("15.in").readText().split(",").map { it.toInt() }

    for (n in start) {
        last_number = n
        if (turn < start.lastIndex) history[n] = turn
        ++turn
    }

    while (turn < 30000000) {
        val next_number = (turn - 1) - (if (history[last_number] == -1) (turn - 1) else history[last_number])
        history[last_number] = turn - 1
        last_number = next_number
        ++turn
    }

    println("$last_number")
}