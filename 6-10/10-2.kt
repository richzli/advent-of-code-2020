import java.io.File

fun main() {
    val nums = File("10.in").readLines().map { it.toInt() } .toMutableList()
    with (nums) {
        addAll(listOf(0, nums.maxOrNull()!! + 3))
        sort()
    }

    val arngs = MutableList(nums.size) { idx -> if (idx == 0) 1L else 0L }
    for (i in 1..nums.lastIndex)
        for (j in 1..3)
            arngs[i] += if ((nums[i] - (nums.elementAtOrNull(i-j) ?: -10)) <= 3) arngs[i-j].toLong() else 0L
    println("${arngs[arngs.lastIndex]}")
}