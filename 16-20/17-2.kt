import java.io.File

fun main() {
    var cells = MutableList<MutableList<MutableList<MutableList<Char>>>>(13) {
        MutableList<MutableList<MutableList<Char>>>(13) {
            MutableList<MutableList<Char>>(20) {
                MutableList<Char>(20) { '.' }
            }
        }
    }

    File("17.in").readText().split("\n").map { it.toCharArray() } .forEachIndexed { idx, e ->
        e.forEachIndexed { idx2, e2 ->
            cells[6][6][idx + 6][idx2 + 6] = e2
        }
    }

    for (step in 1..6) {
        val new_cells = cells.map { it.map { it2 -> it2.map { it3 -> it3.toMutableList() } .toMutableList() } .toMutableList() } .toMutableList()

        for (i in cells.indices) {
            for (j in cells[i].indices) {
                for (k in cells[i][j].indices) {
                    for (l in cells[i][j][k].indices) {
                        var neighbors = 0
                        for (x in -1..1) {
                            for (y in -1..1) {
                                for (z in -1..1) {
                                    for (w in -1..1) {
                                        if ((cells.elementAtOrNull(i + x)
                                            ?.elementAtOrNull(j + y)
                                            ?.elementAtOrNull(k + z)
                                            ?.elementAtOrNull(l + w) ?: '.') == '#') {
                                                ++neighbors
                                        }
                                    }
                                }
                            }
                        }

                        if (cells[i][j][k][l] == '#') {
                            --neighbors
                            if (neighbors !in 2..3) {
                                new_cells[i][j][k][l] = '.'
                            }
                        } else if ((cells[i][j][k][l] == '.') and (neighbors == 3)) {
                            new_cells[i][j][k][l] = '#'
                        }
                    }
                }
            }
        }

        cells = new_cells
    }

    println("${cells.fold(0) { acc, e -> acc + e.fold(0) { acc2, e2 -> acc2 + e2.fold(0) { acc3, e3 -> acc3 + e3.count { it == '#' } } } } }")
}