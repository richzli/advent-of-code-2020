import java.io.File

var rules: Map<Int, List<List<Int>>> = mapOf()

fun main() {
    val (rule_lines, strings) = File("19.in").readText().split("\n\n").map { it.split("\n") }
    rules = rule_lines.map(fun(line: String): Pair<Int, List<List<Int>>> {
        var d = line.replace("\"a\"", "-1").replace("\"b\"", "-2").split(": ", " | ")
        return (d[0].toInt() to (d.drop(1).map { it.split(" ").map { it2 -> it2.toInt() } }))
    }).toMap()

    println("${strings.count { parse(it, 0, 0) == it.length } }")
}

fun parse(s: String, idx: Int, curr_rule: Int): Int {
    when (curr_rule) {
        -2 -> return if (s.getOrNull(idx) == 'b') 1 else -1
        -1 -> return if (s.getOrNull(idx) == 'a') 1 else -1
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

        if (full_match) return curr_idx - idx
    }

    return -1
}