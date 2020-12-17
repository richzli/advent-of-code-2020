import java.io.File

fun main() {
    var cells = MutableList<MutableList<MutableList<Char>>>(13) { MutableList<MutableList<Char>>(20) { MutableList<Char>(20) { '.' } } }

    File("17.in").readText().split("\n").map { it.toCharArray() } .forEachIndexed { idx, e ->
        e.forEachIndexed { idx2, e2 ->
            cells[6][idx + 6][idx2 + 6] = e2
        }
    }


    val dz = listOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val dy = listOf(-1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1)
    val dx = listOf(-1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1)
    for (step in 1..6) {
        val new_cells = cells.map { it.map { it2 -> it2.toMutableList() } .toMutableList() } .toMutableList()

        for (i in cells.indices) {
            for (j in cells[i].indices) {
                for (k in cells[i][j].indices) {
                    var neighbors = 0
                    for (n in dz.indices) {
                        if ((cells.elementAtOrNull(i + dz[n])?.elementAtOrNull(j + dy[n])?.elementAtOrNull(k + dx[n]) ?: '.') == '#') {
                            ++neighbors
                        }
                    }

                    if (cells[i][j][k] == '#') {
                        if (neighbors !in 2..3) {
                            new_cells[i][j][k] = '.'
                        }
                    } else if ((cells[i][j][k] == '.') and (neighbors == 3)) {
                        new_cells[i][j][k] = '#'
                    }
                }
            }
        }

        cells = new_cells
    }

    println("${cells.fold(0) { acc, e -> acc + e.fold(0) { acc2, e2 -> acc2 + e2.count { it == '#' } } } }")
}