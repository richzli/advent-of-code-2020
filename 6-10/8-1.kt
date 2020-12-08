import java.io.File

fun main() {
    val ops = File("8.in").readLines().map { it.split(" ") } .map { Pair(it[0], it[1].toInt()) }
    var executed = BooleanArray(ops.size)
    var accumulator = 0
    var pc = 0

    while (!executed[pc]) {
        executed[pc] = true

        var (op, num) = ops[sp]
        if (op == "nop") {
            ++pc
        } else if (op == "jmp") {
            pc += num
        } else if (op == "acc") {
            accumulator += num
            ++pc
        }
    }

    println("$accumulator")
}