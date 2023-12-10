package day08

import Helpers.readFileByLine
import java.math.BigDecimal
import java.util.function.Predicate

val data = readFileByLine("./data.txt")
val instruction = data.take(1)[0]
val nodeMap = data.drop(2)
    .map { line ->
        val (key, left, right) = Regex("""([A-Z\d]{3}) = \(([A-Z\d]{3})\, ([A-Z\d]{3})\)""")
            .find(line.trim())!!
            .destructured

        key to Pair(left, right)
    }
    .associate { it }
    .toMap()

fun solve(startingNode: String, endCondition: Predicate<String>): BigDecimal {
    var steps = BigDecimal.ZERO
    var instructionIndex = 0;
    var currentNode = startingNode

    while (true) {
        if (endCondition.test(currentNode)) {
            break
        }

        val inst = instruction[instructionIndex]
        val (left, right) = nodeMap[currentNode]!!

        currentNode = if (inst == 'L') {
            left
        } else {
            right
        }

        instructionIndex = (instructionIndex + 1) % instruction.length
        steps += BigDecimal.ONE
    }
    return steps
}

val solution1 = solve("AAA") { it == "ZZZ" }
println("Solution 1: $solution1")

fun solve2(): BigDecimal {
    val allLoopSteps = nodeMap.keys.filter { it.endsWith("A") }
        .map { solve(it) { node -> node.endsWith("Z") } }

    var leastCommonMultiple = allLoopSteps[0]
    for (loopStep in allLoopSteps.drop(1)) {
        val stepLength = listOf(loopStep, leastCommonMultiple).maxOf { it }

        var steps = BigDecimal.ZERO
        while (true) {
            steps += stepLength
            if (steps % loopStep == BigDecimal.ZERO) {
                leastCommonMultiple = steps
                break
            }
        }
    }
    return leastCommonMultiple
}

val solution2 = solve2()
println("Solution 2: $solution2")

