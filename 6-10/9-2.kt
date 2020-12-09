import java.io.File

fun main() {
    val target: Long = 1504371145
    var sum: Long = 0
    val arr = ArrayDeque<Long>()
    File("9.in").forEachLine {
        val i = it.toLong()

        while (sum > target) {
            sum -= arr.removeFirst()
        }

        if ((sum == target) and (arr.size > 1)) {
            println("${arr.maxOrNull()!! + arr.minOrNull()!!}")
        }

        arr.add(i)
        sum += i
    }
}