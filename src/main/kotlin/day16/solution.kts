package day16

import Helpers.readFileByLine
import java.lang.Integer.max

data class Beam(val y: Int, val x: Int, val dy: Int, val dx: Int)

val data = readFileByLine("./data.txt")
    .map { it.toCharArray() }

fun run(start: Beam): Int {
    var beams = mutableListOf<Beam>(start)
    var visited = mutableListOf<Beam>()

    var timesWhenNoNewFound = 0
    while (timesWhenNoNewFound < 20) {
        var newBeams = mutableListOf<Beam>()
        for (beam in beams) {
            if (!visited.contains(beam)) {
                visited.add(beam)
                timesWhenNoNewFound = 0
            } else {
                continue
            }
            val newY = beam.y + beam.dy
            val newX = beam.x + beam.dx

            if (newY !in data.indices || newX !in data[newY].indices) {
                continue
            }

            when (data[newY][newX]) {
                '.' -> newBeams.add(Beam(newY, newX, beam.dy, beam.dx))
                '-' -> {
                    if (beam.dy == 0) {
                        newBeams.add(Beam(newY, newX, beam.dy, beam.dx))
                    } else {
                        newBeams.add(Beam(newY, newX, 0, 1))
                        newBeams.add(Beam(newY, newX, 0, -1))
                    }
                }
                '|' -> {
                    if (beam.dx == 0) {
                        newBeams.add(Beam(newY, newX, beam.dy, beam.dx))
                    } else {
                        newBeams.add(Beam(newY, newX, 1, 0))
                        newBeams.add(Beam(newY, newX, -1, 0))
                    }
                }
                '/' -> {
                    newBeams.add(Beam(newY, newX, beam.dx * -1, beam.dy * -1))
                }
                '\\' -> {
                    newBeams.add(Beam(newY, newX, beam.dx, beam.dy))
                }
            }
        }
        beams = newBeams
        timesWhenNoNewFound += 1
    }
    return visited.map { Pair(it.y, it.x) }.distinct().size - 1
}

val solution1 = run(Beam(0, -1, 0, 1))
println("Solution 1: $solution1")

var solution2 = 0
for (y in data.indices) {
    solution2 = max(solution2, run(Beam(y, -1, 0, 1)))
    solution2 = max(solution2, run(Beam(y, data.size, 0, -1)))
    println("y $y")
}
for (x in data[0].indices) {
    solution2 = max(solution2, run(Beam(-1, x, 1, 0)))
    solution2 = max(solution2, run(Beam(data[0].size, x, -1, 0)))
    println("x $x")
}

println("Solution 2: $solution2")
