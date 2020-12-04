import java.io.File

fun main() {
    val lines = File("4.in").readLines().map { it.split(" ") }
    val fields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    var count = 0
    var passport = mutableListOf<String>()
    for (line in lines) {
        if ((line.size == 1) and (line[0] == "")) {
            if (fields.all { it1 -> passport.any { it2 -> it2.startsWith(it1) } }) {
                ++count
            }

            passport.clear()
        } else {
            passport.addAll(line)
        }
    }

    if (fields.all { it1 -> passport.any { it2 -> it2.startsWith(it1) } }) {
        ++count
    }

    println("$count")
}