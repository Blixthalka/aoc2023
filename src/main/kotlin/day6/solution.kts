package day6
import Helpers.readFileByLine

fun parse1(line: String): List<Long> {
    return line.split(":")[1]
        .trim()
        .split("\\s+".toRegex())
        .map { it.toLong() }
}

val input = readFileByLine("./data1.txt")
val data1 = input.map { parse1(it) }

fun findRaceWins(time: Long, distance: Long): Int {
    return (0..time)
        .map { pressedDownTime ->
            pressedDownTime * (time - pressedDownTime)
        }.count { tryDistance -> tryDistance > distance }
}

val solution1 = data1[0].zip(data1[1])
    .map { (time, distance) -> findRaceWins(time, distance) }
    .fold(0) { acc, value -> if (acc == 0) value else value * acc }

println("Solution 1: $solution1")

fun parse2(line: String): Long {
    return line.split(":")[1]
        .replace("\\s+".toRegex(), "")
        .toLong()
}

val data2 = input.map { parse2(it) }
val solution2 = findRaceWins(data2[0], data2[1])

println("Solution 2: $solution2")


