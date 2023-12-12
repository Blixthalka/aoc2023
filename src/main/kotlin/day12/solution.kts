package day12

import Helpers.readFileByLine

data class Row(val row: String, val arrangement: List<Int>)
data class CacheKey(val string: String, val arrangement: List<Int>)

var cache = mutableMapOf<CacheKey, Long>()

fun containsOnlyBrokenOrUnknown(string: String): Boolean {
    return string.length == string.filter { it == '?' || it == '#' }.length
}

fun isUnknownOrOperational(string: String, index: Int): Boolean {
    if (index < 0) {
        return true
    } else if (index >= string.length) {
        return true
    } else {
        val char = string.get(index)
        return char == '?' || char == '.'
    }
}

fun solve(string: String, arrangements: List<Int>): Long {
    if (arrangements.isEmpty()) {
        return if (string.contains("#")) {
            0L
        } else {
            1L
        }
    }
    val key = CacheKey(string, arrangements)
    if (cache.containsKey(key)) {
        return cache[key]!!
    }

    val toPlace = arrangements[0]
    var sum = 0L
    for (startIndex in string.indices) {
        val endIndex = startIndex + (toPlace - 1)

        if (endIndex >= string.length) {
            break
        }

        val subString = string.substring(startIndex, endIndex + 1)
        val beforeString = string.substring(0, startIndex)

        if (beforeString.contains('#')) {
            break
        }

        if (containsOnlyBrokenOrUnknown(subString)
            && isUnknownOrOperational(string, startIndex - 1)
            && isUnknownOrOperational(string, endIndex + 1)
        ) {
            val afterString = string.substring(kotlin.math.min(endIndex + 2, string.length))
            sum += solve(afterString, arrangements.drop(1))
        }
    }
    cache[key] = sum
    return sum
}

val data = readFileByLine("./data.txt")
    .map { line ->
        val (row, arrangements) = line.split(" ")
        val arranged = arrangements.split(",")
            .map { it.toInt() }
        Row(row, arranged)
    }

val solution1 = data
    .sumOf { row ->
        solve(row.row, row.arrangement)
    }

println("Solution 1: $solution1")

val solution2 = data
    .map { row ->
        Row(
            "${row.row}?${row.row}?${row.row}?${row.row}?${row.row}",
            row.arrangement + row.arrangement + row.arrangement + row.arrangement + row.arrangement
        )
    }
    .sumOf { row ->
        solve(row.row, row.arrangement)
    }

println("Solution 2: $solution2")
