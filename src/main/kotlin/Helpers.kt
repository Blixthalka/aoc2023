import java.io.File

object Helpers {
    fun readFileByLine(filename: String): List<String> {
        return File(filename).useLines { it.toList() }
    }
}

