package day07

import Helpers.readFileByLine

val data = readFileByLine("./data1.txt")
    .map { line ->
        val v = line.split("\\s+".toRegex())
        Pair(v[0], v[1].toInt())
    }

fun score(cards: String): Int {
    val map = mutableMapOf<Char, Int>()
    for (char in cards) {
        map.compute(char) { _, value -> if (value == null) 1 else value + 1 }
    }
    val same = map.values.sorted()
    if (same.contains(5)) {
        return 7
    } else if (same.contains(4)) {
        return 6
    } else if (same.contains(3) && same.contains(2)) {
        return 5
    } else if (same.contains(3)) {
        return 4
    } else if (same == listOf(1, 2, 2)) {
        return 3
    } else if (same.contains(2)) {
        return 2
    } else {
        return 1
    }
}

fun cardSequenceScore(cards: String, jackScore: Int): List<Int> {
    return cards.map { card ->
        if (card.isDigit()) {
            card.toString().toInt()
        } else if (card == 'T') {
            10
        } else if (card == 'J') {
            jackScore
        } else if (card == 'Q') {
            12
        } else if (card == 'K') {
            13
        } else {
            14
        }
    }
}

fun cardSequenceCompare(
    cards1: Pair<String, Int>,
    cards2: Pair<String, Int>,
    jackScore: Int
): Int {
    val score1 = cardSequenceScore(cards1.first, jackScore)
    val score2 = cardSequenceScore(cards2.first, jackScore)

    for ((v1, v2) in score1.zip(score2)) {
        if (v1 == v2) {
            continue
        } else if (v1 > v2) {
            return 1
        } else {
            return -1
        }
    }
    return 0
}

fun solve(sorter: Comparator<Pair<String, Int>>): Int {
    return data.sortedWith(sorter)
        .mapIndexed { index, cards -> (index + 1) * cards.second }
        .fold(0) { acc, value -> acc + value }
}

val solution1Sorting = compareBy<Pair<String, Int>> { score(it.first) }
    .thenComparator { cards1, cards2 -> cardSequenceCompare(cards1, cards2, 11) }
val solution1 = solve(solution1Sorting)

println("Solution 1: $solution1")

fun highestScore(cards: String): Int {
    var max = 0
    for (value in "23456789TQKA") {
        val score = score(cards.replace('J', value))
        if (score > max) {
            max = score
        }
    }
    return max
}

val solution2Sorting = compareBy<Pair<String, Int>> { highestScore(it.first) }
    .thenComparator { cards1, cards2 -> cardSequenceCompare(cards1, cards2, 1) }
val solution2 = solve(solution2Sorting)

println("Solution 2: $solution2")


