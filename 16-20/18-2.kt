import java.io.File

fun main() {
    var total = 0L

    File("18.in").forEachLine {
        var tokens = mutableListOf<Any>()

        tokens.addAll(listOf('(', '('))

        it.toCharArray().forEach { it2 ->
            when (it2) {
                in '0'..'9' -> tokens.add((it2 - '0').toLong())
                '+' -> tokens.addAll(listOf(')', '+', '('))
                '*' -> tokens.addAll(listOf(')', ')', '*', '(', '('))
                '(' -> tokens.addAll(listOf('(', '(', '('))
                ')' -> tokens.addAll(listOf(')', ')', ')'))
            }
        }

        tokens.addAll(listOf(')', ')'))

        var stack = ArrayDeque<Any>()

        tokens.forEach { it2 ->
            if (it2 is Char) {
                if (it2 == ')') {
                    var i = stack.removeLast() as Long
                    stack.removeLast()
                    while (stack.lastOrNull() in listOf('+', '*')) {
                        if (stack.last() == '+') {
                            stack.removeLast()
                            i += stack.removeLast() as Long
                        } else {
                            stack.removeLast()
                            i *= stack.removeLast() as Long
                        }
                    }
                    stack.add(i)
                } else {
                    stack.add(it2)
                }
            } else if (it2 is Long) {
                var i = it2
                while (stack.lastOrNull() in listOf('+', '*')) {
                    if (stack.last() == '+') {
                        stack.removeLast()
                        i += stack.removeLast() as Long
                    } else {
                        stack.removeLast()
                        i *= stack.removeLast() as Long
                    }
                }
                stack.add(i)
            }
        }

        total += stack.removeLast() as Long
    }

    println(total)
}