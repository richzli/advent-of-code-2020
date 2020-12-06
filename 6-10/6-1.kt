import java.io.File

fun main() {
    var count = 0
    val questions = mutableSetOf<Char>()

    File("6.in").forEachLine {
        questions.addAll(it.toCharArray().toList())
        if (it == "") {
            count += questions.size
            questions.clear()
        }
    }

    println("${count + questions.size}")
}