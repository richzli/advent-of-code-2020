import java.io.File

fun main() {
    val tiles = mutableMapOf<Pair<Int, Int>, Boolean>()
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

    println("${tiles.values.count { it } }")
}