import java.io.File

fun main() {
    println("${File("5.in").readLines().map { it.replace("F", "0").replace("B", "1").replace("L", "0").replace("R", "1").toInt(2) } .max()}")
}