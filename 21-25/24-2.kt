import java.io.File

fun main() {
    var tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
    File("24.in").forEachLine { line ->
        val cs = line.toCharArray()
        var x = 0
        var y = 0
        var cptr = 0
        while (cptr < cs.size) {
            if (cs[cptr] == 'e') {
                ++x
            } else if (cs[cptr] == 'w') {
                --x
            } else if (cs[cptr] == 'n') {
                ++cptr
                if (cs[cptr] == 'e') {
                    ++x
                    ++y
                } else if (cs[cptr] == 'w') {
                    ++y
                }
            } else if (cs[cptr] == 's') {
                ++cptr
                if (cs[cptr] == 'e') {
                    --y
                } else if (cs[cptr] == 'w') {
                    --x
                    --y
                }
            }
            ++cptr
        }
        tiles[Pair(x, y)] = !tiles.getOrDefault(Pair(x, y), false)
    }

    val dx = listOf(1, 1, 0, -1, -1, 0)
    val dy = listOf(0, 1, 1, 0, -1, -1)

    for (i in 1..100) {
        var neighbors = mutableMapOf<Pair<Int, Int>, Int>()
        for ((tile, color) in tiles) {
            val (x, y) = tile
            if (color) {
                for (j in 0..5) {
                    neighbors[Pair(x+dx[j], y+dy[j])] = neighbors.getOrDefault(Pair(x+dx[j], y+dy[j]), 0) + 1
                }
            }
        }
        tiles = neighbors.mapValues { if (tiles.getOrDefault(Pair(it.key.first, it.key.second), false)) (if (it.value in 1..2) true else false) else (if (it.value == 2) true else false) } .toMutableMap()
    }

    println("${tiles.values.count { it } }")
}