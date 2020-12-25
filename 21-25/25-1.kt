import java.io.File

fun main() {
    var (card, door) = File("25.in").readLines().map { it.toInt() }
    var cnt_c = 0
    var key_c = 1
    
    while (key_c != card) {
        ++cnt_c
        key_c = (key_c * 7) % 20201227
    }

    var cnt_d = 0
    var key_d = 1
    while (key_d != door) {
        ++cnt_d
        key_d = (key_d * 7) % 20201227
    }

    var ans = 1L
    for (i in 1..cnt_d) {
        ans = (ans * key_c) % 20201227
    }

    println("$ans")
}