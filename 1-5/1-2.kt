import java.io.File

fun main() {
    val nums = File("1.in").readLines().map { it.toInt() } .toIntArray()
    nums.sort()

    for (i in nums.indices) {
        var j = i + 1
        var k = nums.size - 1
        while (j < k) {
            val sum = nums[i] + nums[j] + nums[k]
            if (sum == 2020) {
                println("${nums[i]} * ${nums[j]} * ${nums[k]} = ${nums[i] * nums[j] * nums[k]}")
                return
            } else if (sum > 2020) {
                k--
            } else {
                j++
            }
        }
    }
}