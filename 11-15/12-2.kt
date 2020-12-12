import java.io.File
import kotlin.math.abs

fun main() {
    var posx: Int = 0
    var posy: Int = 0
    var wayx: Int = 10
    var wayy: Int = 1

    File("12.in").forEachLine {
        val cmd = it.get(0)
        val num = it.substring(1).toInt()

        when (cmd) {
            'N' -> wayy += num
            'S' -> wayy -= num
            'E' -> wayx += num
            'W' -> wayx -= num
            'L' -> {
                when (num) {
                    90 -> {
                        val temp = wayy
                        wayy = wayx
                        wayx = -temp
                    }
                    180 -> {
                        wayy *= -1
                        wayx *= -1
                    }
                    270 -> {
                        val temp = wayy
                        wayy = -wayx
                        wayx = temp
                    }
                }
            }
            'R' -> {
                when (num) {
                    270 -> {
                        val temp = wayy
                        wayy = wayx
                        wayx = -temp
                    }
                    180 -> {
                        wayy *= -1
                        wayx *= -1
                    }
                    90 -> {
                        val temp = wayy
                        wayy = -wayx
                        wayx = temp
                    }
                }
            }
            'F' -> {
                posx += num * wayx
                posy += num * wayy
            }
        }
    }

    println("${abs(posx) + abs(posy)}")
}