import java.io.File
import kotlin.math.abs

fun main() {
    var posx: Int = 0
    var posy: Int = 0
    var angle: Int = 0

    File("12.in").forEachLine {
        val cmd = it.get(0)
        val num = it.substring(1).toInt()

        when (cmd) {
            'N' -> posy += num
            'S' -> posy -= num
            'E' -> posx += num
            'W' -> posx -= num
            'L' -> {
                angle += num
                angle %= 360
            }
            'R' -> {
                angle += 360 - num
                angle %= 360
            }
            'F' -> {
                when (angle) {
                    0 -> posx += num
                    90 -> posy += num
                    180 -> posx -= num
                    270 -> posy -= num
                }
            }
        }
    }

    println("${abs(posx) + abs(posy)}")
}