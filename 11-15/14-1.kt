import java.io.File

fun main() {
    var mem = LongArray(100000)
    var mask = 0L
    var maskx = 0L

    File("14.in").forEachLine {
        if (it.startsWith("mask")) {
            val m = it.split(" = ")[1]
            mask = m.replace("X", "0").toLong(2)
            maskx = m.replace("1", "0").replace("X", "1").toLong(2)
        } else {
            val vals = it.split("[", "] = ")
            val location = vals[1].toInt()
            val value = vals[2].toLong()

            mem[location] = (maskx and value) + mask
        }
    }

    println("${mem.sum()}")
}