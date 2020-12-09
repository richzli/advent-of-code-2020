import java.io.File

fun main() {
    val prev = ArrayDeque<Long>()
    File("9.in").forEachLine { it ->
        val i = it.toLong()
        if (prev.size < 25) {
            prev.add(i)
        } else {
            if (!prev.any { it2 -> prev.contains(i - it2) and (it2 * 2 != i) }) {
                println(it)
            }
            prev.add(i)
            prev.removeFirst()
        }
    }
}