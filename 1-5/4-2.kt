import java.io.File

fun main() {
    val lines = File("4.in").readLines().map { it.split(" ") }

    var count = 0
    var passport = mutableListOf<String>()
    for (line in lines) {
        if ((line.size == 1) and (line[0] == "")) {
            count += check(passport)
            passport.clear()
        } else {
            passport.addAll(line)
        }
    }
    count += check(passport)

    println("$count")
}

fun check(passport: MutableList<String>): Int {
    var count = 0
    for (field in passport) {
        val parts = field.split(":")
        when (parts[0]) {
            "byr" -> {
                val yr = parts[1].toIntOrNull() ?: 0
                if ((1920 <= yr) and (yr <= 2002)) count++
            }
            "iyr" -> {
                val yr = parts[1].toIntOrNull() ?: 0
                if ((2010 <= yr) and (yr <= 2020)) count++
            }
            "eyr" -> {
                val yr = parts[1].toIntOrNull() ?: 0
                if ((2020 <= yr) and (yr <= 2030)) count++
            }
            "hgt" -> {
                if (parts[1].endsWith("cm")) {
                    val ht = parts[1].substring(0, parts[1].length - 2).toIntOrNull() ?: 0
                    if ((150 <= ht) and (ht <= 193)) count++
                } else if (parts[1].endsWith("in")) {
                    val ht = parts[1].substring(0, parts[1].length - 2).toIntOrNull() ?: 0
                    if ((59 <= ht) and (ht <= 76)) count++
                }
            }
            "hcl" -> {
                val reg = Regex("#[0-9a-f]{6}")
                if (reg matches parts[1]) count++
            }
            "ecl" -> {
                if (arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(parts[1])) count++
            }
            "pid" -> {
                val reg = Regex("[0-9]{9}")
                if (reg matches parts[1]) count++
            }
        }
    }

    return if (count == 7) 1 else 0
}