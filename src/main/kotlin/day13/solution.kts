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

fun findMirror(pattern: List<String>): Int {
    for (i in 0..pattern.size - 2) {
        if (pattern[i] == pattern[i + 1] ) {
            val res = verifyPerfect(i, pattern)
            if(res != 0) {
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
        if (pattern[firstIndex] != pattern[secondIndex] ) {
            return 0
        }
    }
    return  index + 1
}

data.forEach {
    val rows = findMirror(it)
    val cols = findMirror(Helpers.rotateRight2d(it))
    println("DIFF ${cols} $rows ")
}



val rows = data.map { findMirror(it) }.sum()
println("--")
val columns = data.map { findMirror(Helpers.rotateRight2d(it)) }.sum()
val solution1 = columns + 100 * rows

println("Solution 1: $solution1")


val solution2 = readFileByLine("./data.txt")
println("Solution 2: $solution2")



data.map {

}



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
