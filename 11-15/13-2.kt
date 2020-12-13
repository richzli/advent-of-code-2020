import java.io.File

fun main() {
    val lines = File("13.in").readLines()
    val buslist = lines[1].split(",").mapIndexedNotNull { idx, it ->
        val id = it.toLongOrNull()
        if (id != null) Pair(id, pmod(id - idx.toLong(), id)) else null
    }
    var buses = ArrayDeque(buslist)

    while (buses.size > 1) {
        val a = buses.removeFirst()
        val b = buses.removeFirst()

        val prod = a.first * b.first
        val (_, x, y) = egcd(a.first, b.first).map { pmod(it, prod) }
        buses.add(Pair(prod, pmod(mulmod(a.first * b.second, x, prod) + mulmod(b.first * a.second, y, prod), prod)))
    }

    val ans = buses.removeFirst()
    println("${ans.second}")
}

fun mulmod(a: Long, b: Long, m: Long): Long {
    if ((a < (1L shl 31)) and (b < (1L shl 31))) {
        return pmod(a*b, m)
    } else {
        var prod = mulmod(a/2, b/2, m) * 4
        if (a % 2 == 1L) {
            prod += b
            if (b % 2 == 1L) {
                prod += a-1
            }
        } else if (b % 2 == 1L) {
            prod += a
        }
        return pmod(prod, m)
    }
}

fun pmod(a: Long, b: Long): Long {
    return ((a % b) + b) % b
}

fun egcd(an: Long, bn: Long): List<Long> {
    var a = an
    var b = bn
    var prevx = 1L
    var x = 0L
    var prevy = 0L
    var y = 1L
    while (b != 0L) {
        val q = a / b
        x = (prevx-q*x).also { prevx = x }
        y = (prevy-q*y).also { prevy = y }
        a = b.also { b = a % b }
    }
    return listOf(a, prevx, prevy)
}