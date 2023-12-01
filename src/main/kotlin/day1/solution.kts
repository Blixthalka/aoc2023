package day1

import Helpers.readFileByLine


fun findNumber1(string: String): Char {
    for (item in string) {
        if (item.isDigit()) {
            return item
        }
    }
    throw RuntimeException("RIP");
}

val solution1 = readFileByLine("./data1.txt")
    .map {
        val first = findNumber1(it)
        val last = findNumber1(it.reversed())
        String(charArrayOf(first, last)).toInt()
    }
    .fold(0) { acc, value -> acc + value }

println("Solution 1: $solution1")

val numbers = mapOf(
    Pair("one", '1'),
    Pair("two", '2'),
    Pair("three", '3'),
    Pair("four", '4'),
    Pair("five", '5'),
    Pair("six", '6'),
    Pair("seven", '7'),
    Pair("eight", '8'),
    Pair("nine", '9')
)

val numbersReversed = numbers.map { Pair(it.key.reversed(), it.value) }.toMap()

fun containsAny(string: String, list: Collection<String>): String? {
    for (item in list) {
        if (string.contains(item)) {
            return item
        }
    }
    return null
}

fun findNumber2(string: String, map:Map<String, Char>): Char {
    var taken = ""
    for (item in string) {
        taken += item
        val contains = containsAny(taken, map.keys)
        if (contains != null) {
            return map[contains] ?: throw RuntimeException("RIP 2")
        } else if (item.isDigit()) {
            return item
        }
    }
    throw RuntimeException("RIP");
}

val solution2 = readFileByLine("./data1.txt")
    .map {
        val first = findNumber2(it, numbers)
        val last = findNumber2(it.reversed(), numbersReversed)
        String(charArrayOf(first, last)).toInt()
    }
    .fold(0) { acc, value -> acc + value }


println("Solution 2: $solution2")


