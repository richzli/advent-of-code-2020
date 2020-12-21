import java.io.File
import java.util.Collections

fun bit_flip(a: Int): Int {
    var c = a
    var b = 0
    for (i in 1..10) {
        b = b shl 1
        b += c and 1
        c = c shr 1
    }
    return b
}

val number_count = mutableMapOf<Int, Int>()

class Tile(val id: Int, val sides: List<Int>) {
    var rotation: Int = 0
    var used: Boolean = false

    fun get(side: Int): Int {
        var u = this.sides.toMutableList()
        if (this.rotation >= 4) {
            u = u.asReversed().toMutableList()
        }
        Collections.rotate(u, this.rotation % 4)

        val flipped = (this.rotation > 4) xor when (side) {
            0, 1 -> (this.rotation % 4) in 1..2
            2, 3 -> (this.rotation % 4) in 2..3
            else -> false
        }

        return if (flipped) bit_flip(u[side]) else u[side]
    }

    fun search(num: Int): Int {
        for (i in 0..7) {
            this.rotation = i
            for (j in 0..3) {
                if (this.get(j) == num) {
                    return j
                }
            }
        }
        return -1
    }

    fun update_count() {
        for (i in 0..3) {
            number_count[this.sides[i]] = number_count.getOrDefault(this.sides[i], 0) + 1
            number_count[bit_flip(this.sides[i])] = number_count.getOrDefault(bit_flip(this.sides[i]), 0) + 1
        }
    }
}

fun main() {
    val tiles = mutableListOf<Tile>()
    val inp = File("20.in").readText().split("\n\n").map { it.split("\n") }
    for (t in inp) {
        val id = """Tile (\d+):""".toRegex().find(t[0])!!.groups[1]!!.value.toInt()
        val top = t[1].replace(".", "0").replace("#", "1").toInt(2)
        val bottom = t[10].replace(".", "0").replace("#", "1").toInt(2)
        var left = 0
        var right = 0
        for (i in 1..10) {
            left = left shl 1
            right = right shl 1
            if (t[i][0] == '#')
                left += 1
            if (t[i][9] == '#')
                right += 1
        }

        // println("$id - ${listOf(top, bottom, left, right).map { it.toString(2).padStart(10, '0') } .joinToString(" ") }")

        tiles.add(Tile(id = id, sides = listOf(top, bottom, left, right)))
    }

    for (t in tiles) {
        t.update_count()
    }

    var ans = 1L
    for (t in tiles) {
        if (t.sides.map { number_count[it] == 1 } .count { it } == 2) {
            ans *= t.id
        }
    }
    println("$ans")
}