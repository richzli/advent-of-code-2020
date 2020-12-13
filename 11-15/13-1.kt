import java.io.File

fun main() {
    var earliest = 100000
    var id = -1

    val lines = File("13.in").readLines()
    val timestamp = lines[0].toInt()
    val buses = lines[1].split(",").mapNotNull { it.toIntOrNull() }

    for (bus in buses) {
        if ((bus - (timestamp % bus)) < earliest) {
            earliest = bus - (timestamp % bus)
            id = bus
        }
    }

    println("${id * earliest}")
}