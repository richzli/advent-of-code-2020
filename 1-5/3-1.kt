import java.io.File

fun main() {
    val map = File("3.in").readLines().map { it.toCharArray() }

    println("${map.foldIndexed(0) { idx, sum, element -> if (element[(idx * 3) % element.size] == '#') sum + 1 else sum }}")
}