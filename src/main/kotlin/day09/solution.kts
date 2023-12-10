package day09

import Helpers.readFileByLine

val data = readFileByLine("./data.txt")
    .map { line -> line.trim().split("\\s".toRegex()).map { it.toInt() } }
    .map { numbers ->
        val levels = mutableListOf<List<Int>>(numbers)
        var currNumbers = numbers
        while (currNumbers.any { it != 0 }) {
            currNumbers = (0..currNumbers.size - 2)
                .map { i -> currNumbers[i + 1] - currNumbers[i] }
            levels.add(currNumbers)
        }
        return@map levels.reversed()
    }

val solution1 = data.sumOf { levels ->
    levels
        .map { it.last() }
        .reduce { acc, value -> value + acc }
}

println("Solution 1: $solution1")

val solution2 = data.sumOf { levels ->
    levels
        .map { it.first() }
        .reduce { acc, value -> value - acc }
}

println("Solution 2: $solution2")
