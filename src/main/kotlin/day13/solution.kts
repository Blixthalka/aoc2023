package day13

import Helpers
import Helpers.readFileByLine

val data = mutableListOf<List<String>>()
var currList = mutableListOf<String>()
for (line in readFileByLine("./data.txt")) {
    if (line.isEmpty()) {
        data.add(currList)
        currList = mutableListOf()
    } else {
        currList.add(line)
    }
}
data.add(currList)
//println(data)

fun findMirror(pattern: List<String>, notSolution: Int): Int {
    for (i in 0..pattern.size - 2) {
        if (pattern[i] == pattern[i + 1]) {
            if (i + 1 == notSolution) {
                continue
            }
            val res = verifyPerfect(i, pattern)
            if (res != 0) {
                return res
            }
        }
    }
    //println("none")
    return 0
}

fun verifyPerfect(index: Int, pattern: List<String>): Int {
    // println("$index ${pattern[index]}")
    for (i in 1..index) {
        val firstIndex = index - i
        val secondIndex = index + i + 1
        if (firstIndex < 0 || secondIndex > pattern.size - 1) {
            return index + 1
        }
        // println("${pattern[firstIndex]} !! ${pattern[secondIndex]}")
        if (pattern[firstIndex] != pattern[secondIndex]) {
            return 0
        }
    }
    return index + 1
}

fun findMirror2(pattern: List<String>, notIndex: Int): Int {

    val pp = pattern.map { it.toCharArray() }
    for (y in pp.indices) {
        for (x in pp[y].indices) {
            pp[y][x] = if (pp[y][x] == '#') '.' else '#'
            val mirrorIndex = findMirror(pp.map { String(it) }, notIndex)
            if (mirrorIndex != 0) {
                return mirrorIndex
            }
            pp[y][x] = if (pp[y][x] == '#') '.' else '#'
        }
    }
    return 0
}

fun diffsOfStrings(a: String, b: String): Int {
    return a.zip(b)
        .map { (a, b) -> if (a == b) 0 else 1 }
        .sum()
}

val rows = data.map { findMirror(it, -1) }.sum()
println("--")
val columns = data.map { findMirror(Helpers.rotateRight2d(it), -1) }.sum()
val solution1 = columns + 100 * rows

println("Solution 1: $solution1")

val (rows2, cols2) = data.map {
    var row = findMirror(it, -1)
    var col = findMirror(Helpers.rotateRight2d(it), -1)

    println("first $row $col")

    if (row == 0) {
        row = findMirror2(it, -1)
    } else {
        row = findMirror2(it, row)
    }
    if (col == 0) {
        col = findMirror2(Helpers.rotateRight2d(it), -1)
    } else {
        col = findMirror2(Helpers.rotateRight2d(it), col)
    }
    println("$row -- $col")
    Pair(row, col)
}.reduce { acc, (row, col) -> Pair(acc.first + row, acc.second + col) }


val solution2 = cols2 + 100 * rows2

println("Solution 2: $solution2")


/*
 for (i in index until pattern.size) {
        if (stack.isEmpty()) {
            if (index != pattern.size - 1) {
                return 0
            }
            break
        }
        val prev = stack[stack.size - 1]
        stack.removeLast()
        //println("$prev -- ${pattern[i]}")
        if (prev != pattern[i]) {
            return 0
        }
    }
 */
