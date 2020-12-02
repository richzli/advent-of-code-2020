import java.io.File

fun main() {
    var num = 0

    File("2.in").forEachLine {
        num += solve(it)
    }

    println("${num}")
}

fun solve(s: String): Int {
    val args = s.split(" ")
    val bounds = args[0].split("-").map { it.toInt() }
    val letter: Char = args[1][0]
    val password = args[2]

    var count = 0
    for (c in password) {
        if (c == letter) {
            ++count
        }
    }

    return if (bounds[0] <= count && count <= bounds[1]) 1 else 0
}