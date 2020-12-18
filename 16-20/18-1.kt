import java.io.File

fun main() {
    var total = 0L

    File("18.in").forEachLine {
        val stack = ArrayDeque<Any>()

        it.split(" ").forEach { it2 ->
            var o = it2
            while (o.startsWith("(")) {
                stack.add('(')
                o = o.substring(1)
            }

            var end_paren_count = 0
            while (o.endsWith(")")) {
                ++end_paren_count
                o = o.substring(0, o.lastIndex)
            }

            val c = o.single()
            if (c in '0'..'9') {
                var i = (c - '0').toLong()

                while (stack.lastOrNull() in listOf('+', '*', '(')) {
                    when (stack.lastOrNull()) {
                        '+' -> {
                            stack.removeLast()
                            i += stack.removeLast() as Long
                        }
                        '*' -> {
                            stack.removeLast()
                            i *= stack.removeLast() as Long
                        }
                        '(' -> {
                            if (end_paren_count > 0) {
                                stack.removeLast()
                                --end_paren_count
                            } else {
                                break
                            }
                        }
                    }
                }

                stack.add(i)
            } else {
                stack.add(c)
            }
        }

        total += stack.removeLast() as Long
    }

    println(total)
}