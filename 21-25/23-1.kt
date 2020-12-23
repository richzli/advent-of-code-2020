import java.io.File

data class LLNode(val value: Int, var next: LLNode?)

fun main() {
    val cups = File("23.in").readText().split("").mapNotNull { if (it == "") null else LLNode(it.toInt(), null) }
    for (i in cups.indices) {
        cups[i].next = cups[(i+1)%cups.size]
    }

    var curr: LLNode? = cups[0]
    for (i in 1..100) {
        val move = curr?.next
        curr?.next = curr?.next?.next?.next?.next

        var destination = if (curr?.value == 1) 9 else curr!!.value - 1
        while (destination in listOf(move?.value, move?.next?.value, move?.next?.next?.value)) {
            destination -= 1
            if (destination < 1) {
                destination = 9
            }
        }

        var dest = curr.next
        while (dest?.value != destination) {
            dest = dest?.next
        }

        move?.next?.next?.next = dest.next
        dest.next = move
        curr = curr.next
    }

    var one = curr
    while (one?.value != 1) {
        one = one?.next
    }
    while (one?.next?.value != 1) {
        print(one?.next?.value)
        one = one?.next
    }
}