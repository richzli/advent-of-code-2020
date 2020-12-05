import java.io.File

fun main() {
    val seats = File("5.in").readLines().map { it.replace("F", "0").replace("B", "1").replace("L", "0").replace("R", "1").toInt(2) } .toIntArray()
    seats.sort()
    for (i in 1..seats.lastIndex) {
        if (seats[i] - seats[i-1] > 1) println("${seats[i] - 1}")
    }
}