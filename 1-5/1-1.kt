import java.io.File

fun main() {
    val nums = Array(2021) { _ -> false }
    var target = 0

    File("1.in").forEachLine { 
        val n = it.toInt()
        nums[n] = true
        if (nums[2020 - n])
            target = n
    }

    println("${target} * ${2020 - target} = ${target * (2020 - target)}")
}