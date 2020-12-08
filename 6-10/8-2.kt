import java.io.File

fun main() {
    var ops = File("8.in").readLines().map { it.split(" ") } .map { Pair(it[0], it[1].toInt()) } .toMutableList()
    for (i in ops.indices) {
        if (ops[i].first == "nop") {
            ops[i] = ops[i].copy(first = "jmp")
            run(ops)?.let { println(it) }
            ops[i] = ops[i].copy(first = "nop")
        } else if (ops[i].first == "jmp") {
            ops[i] = ops[i].copy(first = "nop")
            run(ops)?.let { println(it) }
            ops[i] = ops[i].copy(first = "jmp")
        }
    }
}

fun run(ops: List<Pair<String, Int>>): Int? {
    var executed = BooleanArray(ops.size)
    var accumulator = 0
    var pc = 0

    while (pc < ops.size) {
        if (executed[pc]) return null

        executed[pc] = true

        var (op, num) = ops[pc]
        if (op == "nop") {
            ++pc
        } else if (op == "jmp") {
            pc += num
        } else if (op == "acc") {
            accumulator += num
            ++pc
        }
    }

    return accumulator
}