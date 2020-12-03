import java.io.File

fun main() {
    val map = File("3.in").readLines().map { it.toCharArray() }

    fun solve(r: Int, c: Int): Long = map.foldIndexed(0) { 
        idx, sum, element -> if ((idx % r == 0) and (element[(idx / r * c) % element.size] == '#')) sum + 1 else sum 
    }

    println("${solve(1, 1) * solve(1, 3) * solve(1, 5) * solve (1, 7) * solve(2, 1)}")
}