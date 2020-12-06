import java.io.File

fun main() {
    var count = 0
    var questions = ('a'..'z').toSet()

    File("6.in").forEachLine {
        if (it == "") {
            count += questions.size
            questions = ('a'..'z').toSet()
        } else {
            questions = questions.intersect(it.toCharArray().toSet())
        }
    }

    println("${count + questions.size}")
}