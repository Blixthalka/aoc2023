package day12

import Helpers.readFileByLine

data class Row(val row: String, val arrangement: List<Int>)


fun isBroken(row: String, arrangement: List<Int>): Boolean {
    val v = row.split("?")[0]
        .replace(".", " ").trim()
        .split("\\s".toRegex())
        .filter { it.isNotEmpty() }

    for (i in 0..v.size - 2) {
        if (i >= arrangement.size) {
            return true
        }
        if(arrangement[i] != v[i].length) {
            return true
        }
    }

    return false
}

fun solve(mainRow: Row): List<String> {
    println(mainRow.row)

    var alreadySolved = mutableListOf<String>()

    fun solve2(row: String): List<String> {
        if (isBroken(row, mainRow.arrangement)) {
            return mutableListOf()
        }
        if (alreadySolved.contains(row)) {
            return mutableListOf()
        }
        alreadySolved.add(row)

        val toReplace = row.filter { it == '?' }.length
        if (toReplace == 0) {
            return mutableListOf(row)
        }

        var index = 0
        return (0..toReplace).flatMap {
            index = row.indexOf('?', index, false)
            val arr = row.toCharArray()
            arr[index] = '#'
            val broken = String(arr)
            arr[index] = '.'
            val fixed = String(arr)
            solve2(broken) + solve2(fixed)
        }
    }
    return solve2(mainRow.row)
}


val data = readFileByLine("./data.txt")
    .map { line ->
        val (row, arrangements) = line.split(" ")
        val arranged = arrangements.split(",")
            .map { it.toInt() }
        Row(row, arranged)
    }.sumOf { row ->
        solve(row)
            .filter { computedString ->

                val computed = computedString.replace(".", " ").trim()
                    .split("\\s".toRegex())
                    .filter { it.isNotEmpty() }

                val first = row.arrangement.mapIndexed { index, value ->
                    if (index >= computed.size) {
                        false
                    } else {
                        computed[index].length == value
                    }
                }
                    .all { it }

                val second = computed.mapIndexed { index, value ->
                    if (index >= row.arrangement.size) {
                        false
                    } else {
                        row.arrangement[index] == value.length
                    }
                }.all { it }
                first && second
            }
            .distinct()
            .count()
    }

println("Solution 1: $data")

val solution2 = readFileByLine("./data.txt")
println("Solution 2: $solution2")


