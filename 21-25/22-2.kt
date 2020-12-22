import java.io.File

fun score(deck: List<Int>): Int {
    return deck.foldIndexed(0) { idx, acc, e -> acc + e * (deck.size - idx) }
}

typealias State = Pair<List<Int>, List<Int>>
data class Game(val prev: MutableSet<State>, var state: State)

fun main() {
    val (p1, p2) = File("22.in").readText().split("\n\n").map { it.split("\n").drop(1).mapNotNull { it2 -> it2.toIntOrNull() } .toList() }

    var winner = 0
    var last_game: Game? = null

    val games = ArrayDeque<Game>(listOf(Game(mutableSetOf<State>(), Pair(p1, p2))))
    game_loop@ while (!games.isEmpty()) {
        val curr = games.last()
        when (winner) {
            1 -> {
                curr.state = Pair(curr.state.first.drop(1) + listOf(curr.state.first.first(), curr.state.second.first()), curr.state.second.drop(1))
            }
            2 -> {
                curr.state = Pair(curr.state.first.drop(1), curr.state.second.drop(1) + listOf(curr.state.second.first(), curr.state.first.first()))
            }
        }
        winner = 0

        if (curr.state in curr.prev) {
            winner = 1
            last_game = games.removeLast()
            continue@game_loop
        } else {
            curr.prev.add(curr.state)
        }

        val (p1g, p2g) = curr.state

        if (p1g.size == 0) {
            winner = 2
            last_game = games.removeLast()
            continue@game_loop
        } else if (p2g.size == 0) {
            winner = 1
            last_game = games.removeLast()
            continue@game_loop
        }

        if ((p1g.first() < p1g.size) and (p2g.first() < p2g.size)) {
            games.add(Game(mutableSetOf<State>(), Pair(p1g.drop(1).dropLast(p1g.size-1-p1g.first()), p2g.drop(1).dropLast(p2g.size-1-p2g.first()))))
        } else {
            if (p1g.first() > p2g.first()) {
                curr.state = Pair(curr.state.first.drop(1) + listOf(curr.state.first.first(), curr.state.second.first()), curr.state.second.drop(1))
            } else {
                curr.state = Pair(curr.state.first.drop(1), curr.state.second.drop(1) + listOf(curr.state.second.first(), curr.state.first.first()))
            }
        }
    }

    if (winner == 2) {
        println("${score(last_game!!.state.second)}")
    } else {
        println("${score(last_game!!.state.first)}")
    }
}