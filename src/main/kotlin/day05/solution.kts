package day05

import Helpers.readFileByLine
import java.math.BigDecimal


fun solveSeed(startSeed: BigDecimal): BigDecimal {
    var currentElement = startSeed;
    for (instructions in allInstructions) {
        var solution: BigDecimal? = null;
        for (mapping in instructions) {
            solution = mapping.getMapping(currentElement)
            if (solution != null) {
                break
            }
        }
        if (solution == null) {
            solution = currentElement
        }
        currentElement = solution as BigDecimal
    }
    return currentElement
}

class Mapping(private val from: BigDecimal, private val to: BigDecimal, private val increment: BigDecimal) {
    override fun toString(): String {
        return "Mapping(from=$from, to=$to, increment=$increment)"
    }

    fun getMapping(seed: BigDecimal): BigDecimal? {
        if (seed >= from && seed < (from + increment)) {
            val diff = seed.minus(from)
            return to + diff
        }
        return null
    }
}

val data = readFileByLine("./data.txt")

var seeds = listOf<BigDecimal>();
var allInstructions = mutableListOf<List<Mapping>>();
var localInstructions = mutableListOf<Mapping>();

for (line in data) {
    if (line.startsWith("seeds:")) {
        seeds = line.replace("seeds:", "")
            .trim()
            .split("\\s".toRegex())
            .map { it.toBigDecimal() }
    } else if (line == "") {
        if (localInstructions.isNotEmpty()) {
            allInstructions.add(localInstructions);
        }
        localInstructions = mutableListOf<Mapping>();
    } else if (line[0].isDigit()) {
        val instruction = line.trim()
            .split("\\s".toRegex())
            .map { it.toBigDecimal() }
        val mapping = Mapping(instruction[1], instruction[0], instruction[2])
        localInstructions.add(mapping)
    }
}
if (localInstructions.isNotEmpty()) {
    allInstructions.add(localInstructions);
}

val solution1 = seeds.map { startSeed -> solveSeed(startSeed) }
    .fold(null) { acc: BigDecimal?, value: BigDecimal ->
        if (acc == null) {
            value
        } else if (acc < value) {
            acc
        } else {
            value
        }
    }

println("Solution 1: $solution1")

var min: BigDecimal?= null
for (index in seeds.indices step 2) {
    val start = seeds[index]
    val increment = seeds[index + 1]
    println("s $start e $increment")

    var curr = start
    while (curr != start + increment) {
        val solution = solveSeed(curr)
        if (min == null || solution < min) {
            min = solution
        }
        curr += BigDecimal.ONE
    }
}

val solution2 = min

println("Solution 2: $solution2")
