import java.io.File

fun main() {
    println("${File("2.in").readLines().map { solve(it) } .sum()}")
}

fun solve(s: String): Int {
    val args = s.split(" ")
    val bounds = args[0].split("-").map { it.toInt() }
    val letter: Char = args[1][0]
    val password = args[2]

    return if ((password[bounds[0]-1] == letter) xor (password[bounds[1]-1] == letter)) 1 else 0
}