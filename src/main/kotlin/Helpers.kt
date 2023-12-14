import java.io.File

object Helpers {
    fun readFileByLine(filename: String): List<String> {
        return File(filename).useLines { it.toList() }
    }

    fun print2d(list: List<String>) {
        for (string in list) {
            println(string)
        }
    }

    fun print2dCharArray(list: List<CharArray>) {
        for (string in list) {
            println(string)
        }
    }

    fun rotateRight2d(list: List<String>): List<String> {
        val rows = list.size
        val cols = list[0].length

        val ret = Array(cols) { Array(rows) { ' ' } }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                ret[j][i] = list[i][j]
            }
        }

        return ret.map { (String(it.toCharArray())).reversed() }.toList();
    }

    fun rotateRight2dCharArray(list: List<CharArray>): List<CharArray> {
        return rotateRight2d(list.map { String(it) })
            .map { it.toCharArray() }
    }


}

