import java.io.File

fun main() {
    val (ranges, _, nearby) = File("16.in").readText().split("\n\n").map{ it.split("\n") }

    var fields = MutableList<Int>(1001) { 0 }
    for (line in ranges) {
        val (range1_1, range1_2, range2_1, range2_2) = line.split(": ", "-", " or ").mapNotNull { it.toIntOrNull() }
        ++fields[range1_1]
        ++fields[range2_1]
        --fields[range1_2+1]
        --fields[range2_2+1]
    }

    var sum = 0
    for (i in fields.indices) {
        sum += fields[i]
        fields[i] = sum
    }

    val validity = nearby.foldIndexed(0) { idx, acc, e -> if (idx > 0) acc + e.split(",").map { it.toInt() } .fold(0) { acc2, e2 -> if (fields[e2] == 0) acc2 + e2 else acc2 } else acc }
    println("$validity")
}