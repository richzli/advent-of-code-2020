import java.io.File

fun main() {
    var seats = File("11.in").readLines().map { it.toCharArray().toMutableList() } .toMutableList()

    var changed = true
    val dx = listOf(-1, 0, 1, 1, 1, 0, -1, -1)
    val dy = listOf(-1, -1, -1, 0, 1, 1, 1, 0)
    while (changed) {
        changed = false

        var new_seats = seats.map { it.toMutableList() } .toMutableList()
        for (i in new_seats.indices) {
            for (j in new_seats[i].indices) {
                var neighbors = 0
                for (k in dx.indices) {
                    var l = 1
                    while ((seats.elementAtOrNull(i+l*dy[k])?.elementAtOrNull(j+l*dx[k]) ?: 'L') == '.') {
                        l++
                    }
                    neighbors += if ((seats.elementAtOrNull(i+l*dy[k])?.elementAtOrNull(j+l*dx[k]) ?: 'L') == '#') 1 else 0
                }

                if ((seats[i][j] == 'L') and (neighbors == 0)) {
                    changed = true
                    new_seats[i][j] = '#'
                } else if ((seats[i][j] == '#') and (neighbors >= 5)) {
                    changed = true
                    new_seats[i][j] = 'L'
                }
            }
        }

        seats = new_seats
    }

    println("${seats.fold(0) { sum, it -> sum + it.count { it2 -> it2 == '#' } } }")
}