package day3

import Helpers.readFileByLine

val data = readFileByLine("./data1.txt")

fun solve1() {
    fun isAdjacentToSymbol(x: Int, y: Int): Boolean {
        for (y2 in y - 1..y + 1) {
            for (x2 in x - 1..x + 1) {
                if (y2 < 0 || x2 < 0 || y2 >= data.size || x2 >= data[0].length) {
                    continue
                }
                val current = data[y2][x2]
                if (!current.isDigit() && current != '.') {
                    return true
                }
            }
        }
        return false
    }

    var partSum = 0
    var currentNumber = ""
    var currentIsPartOfSchematic = false
    for (y in data.indices) {
        for (x in data[y].indices) {
            val current = data[y][x]
            if (!current.isDigit() && currentNumber.isEmpty()) {
                continue;
            } else if (!current.isDigit()) {
                if (currentIsPartOfSchematic) {
                    partSum += currentNumber.toInt()
                }
                currentNumber = ""
                currentIsPartOfSchematic = false
            } else {
                currentNumber += current
                if (isAdjacentToSymbol(x, y)) {
                    currentIsPartOfSchematic = true
                }
                if (x == (data[y].length - 1)) {
                    if (currentIsPartOfSchematic) {
                        partSum += currentNumber.toInt()
                    }
                    currentNumber = ""
                    currentIsPartOfSchematic = false
                }
            }
        }
    }

    println("Solution 1: $partSum")
}

solve1()

fun solve2() {
    
    fun findNumber(y: Int, x: Int): Pair<Pair<Int, Int>, Int> {
        var stringNum = "" + data[y][x]
        var xMinus = x - 1
        while (true) {
            if (xMinus < 0 || xMinus >= data[0].length) {
                break
            }
            val current = data[y][xMinus]
            if (!current.isDigit()) {
                break
            }
            stringNum = current + stringNum
            xMinus -= 1
        }
        val start = Pair(y, xMinus)
        var xPlus = x + 1
        while (true) {
            if (xPlus < 0 || xPlus >= data[0].length) {
                break
            }
            val current = data[y][xPlus]
            if (!current.isDigit()) {
                break
            }
            stringNum += current
            xPlus += 1
        }
        return Pair(start, stringNum.toInt())
    }


    fun findAdjacentNumbers(x: Int, y: Int): List<Int> {
        val res = mutableListOf<Pair<Pair<Int, Int>, Int>>()
        for (y2 in y - 1..y + 1) {
            for (x2 in x - 1..x + 1) {
                if (y2 < 0 || x2 < 0 || y2 >= data.size || x2 >= data[0].length) {
                    continue
                }
                val current = data[y2][x2]
                if (current.isDigit()) {
                    res.add(findNumber(y2, x2))
                }
            }
        }

        return res.distinctBy { it.first }
            .map { it.second }
    }

    var partSum = 0
    for (y in data.indices) {
        for (x in data[y].indices) {
            val current = data[y][x]
            if (current == '*') {
                val adjacent = findAdjacentNumbers(x, y)
                if (adjacent.size == 2) {
                    partSum += (adjacent[0] * adjacent[1])
                }
            }
        }
    }
    println("Solution 2: $partSum")

}
solve2()


//println("Solution 2: $solution2" )


