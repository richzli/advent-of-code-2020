import java.io.File

fun main() {
    val nums = File("10.in").readLines().map { it.toInt() } .toMutableList()
    nums.add(0)
    val numsarr = nums.toIntArray()
    numsarr.sort()
    val ones = numsarr.asIterable().windowed(2).fold(0) { sum, element -> if (element[1] - element[0] == 1) sum + 1 else sum }
    val threes = numsarr.asIterable().windowed(2).fold(0) { sum, element -> if (element[1] - element[0] == 3) sum + 1 else sum }

    println("${ones * (threes + 1)}")
}