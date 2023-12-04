package day4

import Helpers.readFileByLine

fun splitToInts(string: String): List<Int> {
    return string.trim().split("\\s+".toRegex())
        .map { it.toInt() }
}

val games = readFileByLine("./data1.txt")
    .map { line ->
        val (winning, numbers) = Regex("""Card\s+\d+:([\d\s]+)\|([\d ]+)""")
            .find(line.trim())!!
            .destructured

        Pair(splitToInts(winning), splitToInts(numbers))
    }

val solution1 = games
    .map { (winningNumbers, numbers) ->
        numbers
            .filter { num -> winningNumbers.contains(num) }
            .fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2 }
    }
    .fold(0) { acc, gameScore -> acc + gameScore }


println("Solution 1: $solution1")

fun solve(index: Int): Int {
    val (winningNumbers, numbers) = games[index]
    val totalWinning = numbers
        .filter { num -> winningNumbers.contains(num) }
        .size

    return 1 + (index + 1..index + totalWinning)
        .map { i -> solve(i) }
        .fold(0) {acc, v -> acc + v}
}

val solution2 = List(games.size) { index -> solve(index) }
    .fold(0) { acc, gameScore -> acc + gameScore }

println("Solution 2: $solution2")


