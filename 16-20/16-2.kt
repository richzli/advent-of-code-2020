import java.io.File

fun main() {
    val (ranges, your, nearby) = File("16.in").readText().split("\n\n").map{ it.split("\n") }

    var fields = MutableList<Int>(1001) { 0 }
    var departure = 0
    for (i in ranges.indices) {
        val (range1_1, range1_2, range2_1, range2_2) = ranges[i].split(": ", "-", " or ").mapNotNull { it.toIntOrNull() }
        fields[range1_1] += (1 shl i)
        fields[range2_1] += (1 shl i)
        fields[range1_2+1] -= (1 shl i)
        fields[range2_2+1] -= (1 shl i)

        if (ranges[i].startsWith("departure")) departure += (1 shl i)
    }

    var sum = 0
    for (i in fields.indices) {
        sum += fields[i]
        fields[i] = sum
    }

    val valid_tickets = nearby.drop(1).mapNotNull { e -> 
        if (e.split(",").map { it.toInt() } .all { v -> fields[v] > 0 }) e.split(",").map { it.toInt() } else null
    }

    val params = MutableList<Int>(ranges.size) { 0.inv() }
    valid_tickets.forEach {
        it.forEachIndexed {
            idx, e -> params[idx] = params[idx] and fields[e]
        }
    }

    val q = ArrayDeque<Int>()
    params.forEach {
        if (it.countOneBits() == 1) {
            q.add(it)
        }
    }

    while (!q.isEmpty()) {
        val n = q.removeFirst()
        params.forEachIndexed { idx, e ->
            if (params[idx].countOneBits() > 1) {
                params[idx] = e and n.inv()
                if (params[idx].countOneBits() == 1) {
                    q.add(params[idx])
                }
            }
        }
    }

    val ans = your[1].split(",").map { it.toInt() } .foldIndexed(1L) { idx, acc, e -> if ((params[idx] and departure) > 0) acc * e else acc } 
    println("$ans")
}