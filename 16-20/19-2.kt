import java.io.File

var rules: Map<Int, List<List<Int>>> = mapOf()

fun main() {
    val (rule_lines, strings) = File("19.in").readText().split("\n\n").map { it.split("\n") }
    rules = rule_lines.map(fun(line: String): Pair<Int, List<List<Int>>> {
        var d = line.replace("\"a\"", "-1").replace("\"b\"", "-2").split(": ", " | ")
        val r = d[0].toInt()
        return (r to (when (r) {
            8 -> listOf(listOf(42, 8), listOf(42))
            11 -> listOf(listOf(42, 11, 31), listOf(42, 31))
            else -> d.drop(1).map { it.split(" ").map { it2 -> it2.toInt() } }
        }))
    }).toMap()

    println("${strings.count { parse(it, 0, 0) == it.length } }")
}

fun parse(s: String, idx: Int, curr_rule: Int): Int {
    when (curr_rule) {
        -2 -> return if (s.getOrNull(idx) == 'b') 1 else -1
        -1 -> return if (s.getOrNull(idx) == 'a') 1 else -1
        0 -> { // rule 0 is 8 11
            for (i in 20 downTo 1) {
                var try8 = parsex(s, 0, 8, i)
                if (try8 > 0) {
                    for (j in 20 downTo 1) {
                        var try11 = parsex(s, try8, 11, j)
                        if (try11 == s.length) {
                            return try11
                        }
                    }
                }
            }
            return -1
        }
    }

    for (l in rules[curr_rule]!!) {
        var curr_idx = idx
        var full_match = true
        for (x in l) {  
            val match = parse(s, curr_idx, x)
            if (match > 0) {
                curr_idx += match
            } else {
                full_match = false
                break
            }
        }

        if (full_match and (curr_idx <= s.length)) return curr_idx - idx
    }

    return -1
}

fun parsex(s: String, idx: Int, curr_rule: Int, count: Int): Int {
    if (count == 0) {
        return 0
    }
    var curr_idx = idx
    if (curr_rule == 8) {
        for (i in 1..count) {
            var x = parse(s, curr_idx, 42)
            if (x == -1) return -1
            curr_idx += x
        }
    } else if (curr_rule == 11) {
        for (i in 1..count) {
            var x = parse(s, curr_idx, 42)
            if (x == -1) return -1
            curr_idx += x
        }
        for (i in 1..count) {
            var x = parse(s, curr_idx, 31)
            if (x == -1) return -1
            curr_idx += x
        }
    }

    return curr_idx
}