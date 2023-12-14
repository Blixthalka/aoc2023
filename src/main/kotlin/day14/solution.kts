package day14

import Helpers.readFileByLine
import Helpers.rotateRight2dCharArray

fun tiltNorth(data: List<CharArray>) {
    for (y in 1 until data.size) {
        if (y == 0) {
            continue
        }
        for (x in data.indices) {
            val value = data[y][x]
            if (value == '.' || value == '#') {
                continue
            }

            for (dy in y - 1 downTo 0) {
                val upVal = data[dy][x]
                if (upVal != '.') {
                    break
                }
                data[dy + 1][x] = '.'
                data[dy][x] = 'O'
            }
        }
    }
}

fun calcNorthWeight(data: List<CharArray>): Long {
    var sum = 0L
    for (y in data.indices) {
        for (x in data.indices) {
            val value = data[y][x]
            if (value == 'O') {
                sum += data.size - y
            }
        }
    }
    return sum
}

fun getCycleLength(history: List<Long>): Int {
    for (h in 3 until history.size / 2) {
        var hasCycle = true
        for (i in 0 until 3) {
            val now = history[history.size - i - 1]
            val prev = history[history.size - i - 1 - h]
            if (now != prev) {
                hasCycle = false
                break
            }
        }
        if (hasCycle) {
            return h
        }
    }
    return -1
}

fun part2(data1: List<CharArray>): Long {
    var data = data1
    val history = mutableListOf<Long>()
    for (cycle in 1..1_000_000_000) {
        for (local in 0..3) {
            tiltNorth(data)
            data = rotateRight2dCharArray(data)
        }

        history.add(calcNorthWeight(data))

        val cycleLength = getCycleLength(history)
        if (cycleLength != -1) {
            val rest = 1_000_000_000 - cycle
            val index = rest % cycleLength
            return history[history.size - 1 - (cycleLength - index)]
        }
    }
    return -1
}

var data1 = readFileByLine("./data.txt")
    .map { it.toCharArray() }

tiltNorth(data1)
val solution1 = calcNorthWeight(data1)
println("Solution 1: $solution1")


var data2 = readFileByLine("./data.txt")
    .map { it.toCharArray() }

val solution2 = part2(data2)
println("Solution 2: $solution2")


