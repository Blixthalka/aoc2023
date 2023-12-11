package day11

import Helpers.readFileByLine
import kotlin.math.abs

data class Coord(val y: Int, val x: Int)

fun rotate(list: List<String>): MutableList<String> {
    val columns = mutableMapOf<Int, String>()
    for (row in list) {
        for ((i, value) in row.withIndex()) {
            columns[i] = (columns[i] ?: "") + value
        }
    }
    val returnList = mutableListOf<String>()
    for (column in columns.values) {
        returnList.add(column)
    }
    return returnList
}

fun expandRows(list: List<String>): MutableList<Int> {
    val expandedRows = mutableListOf<Int>()
    for ((i, row) in list.withIndex()) {
        if (row.all { it == '.' }) {
            expandedRows.add(i)
        }
    }
    return expandedRows
}

val data = readFileByLine("./data.txt")
val expandedRows = expandRows(data)
val expandedColumns = expandRows(rotate(data))

val galaxies = mutableListOf<Coord>()
for (y in data.indices) {
    for (x in data[y].indices) {
        if (data[y][x] == '#') {
            galaxies.add(Coord(y, x))
        }
    }
}

fun isBetween(index: Int, first: Int, second: Int): Boolean {
    return (index in (first + 1) until second) || (index in (second + 1) until first)
}

fun calc(expandedValue: Int): Long {
    val r = galaxies.sumOf { startingGalaxy ->
        galaxies.sumOf { endingGalaxy ->
            val passedRows = expandedRows.filter { index ->
                isBetween(index, startingGalaxy.y, endingGalaxy.y)
            }.size.toLong() * (expandedValue - 1)

            val passedCols = expandedColumns.filter { index ->
                isBetween(index, startingGalaxy.x, endingGalaxy.x)
            }.size.toLong() * (expandedValue - 1)


            abs(startingGalaxy.y.toLong() - endingGalaxy.y.toLong()) + abs(startingGalaxy.x.toLong() - endingGalaxy.x.toLong()) + passedRows + passedCols
        }
    }
    return r / 2
}


var solution1 = calc(2)
println("Solution 1: $solution1")

val solution2 = calc(1000000)
println("Solution 2: $solution2")


