import java.io.File

val SEA_MONSTER = """                  # 
                    |#    ##    ##    ###
                    | #  #  #  #  #  #   """.trimMargin().split("\n").map { it.toCharArray().toList() }

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

fun permute(l: MutableList<Int>, p: List<Int>): MutableList<Int> {
    var r = mutableListOf<Int>()
    for (x in p) {
        r.add(l[x])
    }
    return r
}

val number_count = mutableMapOf<Int, Int>()

class Tile(val id: Int, val sides: List<Int>, val image: List<String>) {
    var rotation: Int = 0
    var used: Boolean = false

    fun get(side: Int): Int {
        var u = this.sides.toMutableList()
        if (this.rotation >= 4) {
            u = u.asReversed().toMutableList()
        }
        u = when (this.rotation % 4) {
            1 -> permute(u, listOf(2, 3, 1, 0))
            2 -> permute(u, listOf(1, 0, 3, 2))
            3 -> permute(u, listOf(3, 2, 0, 1))
            else -> u
        }

        var flipped = (this.rotation >= 4) xor (when (side) {
            0, 1 -> (this.rotation % 4) in 1..2
            2, 3 -> (this.rotation % 4) in 2..3
            else -> false
        })

        return if (flipped) bit_flip(u[side]) else u[side]
    }

    fun search(num: Int, side: Int): Boolean {
        for (i in 0..7) {
            this.rotation = i
            if (this.get(side) == num) {
                return true
            }
        }
        return false
    }

    fun update_count() {
        for (i in 0..3) {
            number_count[this.sides[i]] = number_count.getOrDefault(this.sides[i], 0) + 1
            number_count[bit_flip(this.sides[i])] = number_count.getOrDefault(bit_flip(this.sides[i]), 0) + 1
        }
    }
}

fun rotate(i: Int, j: Int, size: Int, r: Int): List<Int> {
    val m = size - 1
    var nx = i
    var ny = j
    if (r >= 4) {
        nx = m-j
        ny = m-i
    }
    when (r % 4) {
        1 -> { nx = ny.also { ny = m-nx } }
        2 -> { nx = m-nx.also { ny = m-ny } }
        3 -> { nx = m-ny.also { ny = nx } }
    }
    return listOf(nx, ny)
}

fun main() {
    val GRID_SIZE = 12

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

        tiles.add(Tile(id = id, sides = listOf(top, bottom, left, right), image = t.drop(1)))
    }

    for (t in tiles) {
        t.update_count()
    }

    var grid = MutableList<MutableList<Tile?>>(GRID_SIZE) { MutableList<Tile?>(GRID_SIZE) { null } }
    first_check@ for (t in tiles) {
        for (i in 0..7) {
            t.rotation = i
            if ((number_count[t.get(0)] == 1) and (number_count[t.get(2)] == 1)) {
                grid[0][0] = t
                t.used = true
                break@first_check
            }
        }
    }

    first_row@ for (i in 1..GRID_SIZE-1) {
        for (t in tiles) {
            if (!t.used) {
                if (t.search(grid[0][i-1]!!.get(3), 2)) {
                    grid[0][i] = t
                    t.used = true
                    continue@first_row
                }
            }
        }
    }

    for (i in 1..GRID_SIZE-1) {
        next_column@ for (j in 0..GRID_SIZE-1) {
            for (t in tiles) {
                if (!t.used) {
                    if (t.search(grid[i-1][j]!!.get(1), 0)) {
                        grid[i][j] = t
                        t.used = true
                        continue@next_column
                    }
                }
            }
        }
    }

    // println("${grid.map { it.map { it2 -> (it2?.id?.toString() ?: "----") + "(${it2?.rotation?.toString() ?: "-"})" } .joinToString(" ") } .joinToString("\n") }\n")

    val full = MutableList<MutableList<Char>>(GRID_SIZE*8) { MutableList<Char>(GRID_SIZE*8) { '.' } }
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            for (k in 0..7) {
                for (l in 0..7) {
                    var (nx, ny) = rotate(k, l, 8, grid[i][j]!!.rotation)
                    full[i*8+nx][j*8+ny] = grid[i][j]!!.image[1+k][1+l]
                }
            }
        }
    }
    // println("${full.map { it.joinToString("") } .joinToString("\n")}")

    var monsters = false
    for (r in 0..7) {
        if (monsters) {
            break
        }

        for (i in 0..(GRID_SIZE*8-SEA_MONSTER.size)) {
            next_monster@ for (j in 0..(GRID_SIZE*8-SEA_MONSTER[0].size)) {
                for (k in SEA_MONSTER.indices) {
                    for (l in SEA_MONSTER[k].indices) {
                        var (nx, ny) = rotate(i+k, j+l, GRID_SIZE*8, r)
                        if ((SEA_MONSTER[k][l] == '#') and (full[nx][ny] == '.')) {
                            continue@next_monster
                        }
                    }
                }
                monsters = true
                for (k in SEA_MONSTER.indices) {
                    for (l in SEA_MONSTER[0].indices) {
                        var (nx, ny) = rotate(i+k, j+l, GRID_SIZE*8, r)
                        if (SEA_MONSTER[k][l] == '#')
                            full[nx][ny] = 'O'
                    }
                }
            }
        }
    }

    println("${full.fold(0) { acc, e -> acc + e.count { it == '#' } } }")
}