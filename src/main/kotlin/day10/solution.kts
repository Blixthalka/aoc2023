package day10

import Helpers.readFileByLine
import kotlin.math.floor

val data = readFileByLine("./data.txt")

data class Move(val y: Int, val x: Int) {
    fun negate(): Move {
        return Move(y * -1, x * -1)
    }
}

data class Coord(val y: Int, val x: Int)

object Moves {
    val EAST = Move(0, 1)
    val WEST = Move(0, -1)
    val NORTH = Move(-1, 0)
    val SOUTH = Move(1, 0)

    fun all(): List<Move> {
        return listOf(EAST, WEST, NORTH, SOUTH)
    }
}

val moves = listOf(Moves.EAST, Moves.WEST, Moves.NORTH, Moves.SOUTH)

val movableDirections = mapOf(
    '|' to listOf(Moves.NORTH, Moves.SOUTH),
    '-' to listOf(Moves.WEST, Moves.EAST),
    'L' to listOf(Moves.NORTH, Moves.EAST),
    'J' to listOf(Moves.NORTH, Moves.WEST),
    '7' to listOf(Moves.SOUTH, Moves.WEST),
    'F' to listOf(Moves.SOUTH, Moves.EAST),
    '.' to listOf(),
    'S' to Moves.all()
)

fun findStart(): Coord {
    for (y in data.indices) {
        for (x in data[y].indices) {
            if (data[y][x] == 'S') {
                return Coord(y, x)
            }
        }
    }
    throw RuntimeException("RIP")
}

fun canMove(yCurr: Int, xCurr: Int, outMove: Move): Boolean {
    val yMove = yCurr + outMove.y
    val xMove = xCurr + outMove.x

    if (yMove < 0 || yMove >= data.size || xMove < 0 || xMove >= data[yMove].length) {
        return false
    }

    val inMove = outMove.negate()
    val outPipe = data[yCurr][xCurr]
    val inPipe = data[yMove][xMove]

    val canMoveOut = movableDirections[outPipe]!!.contains(outMove)
    val canMoveIn = movableDirections[inPipe]!!.contains(inMove)

    return canMoveOut && canMoveIn
}

fun solve1(): MutableList<Coord> {
    val path = mutableListOf<Coord>()
    var (y, x) = findStart()
    while (true) {
        for (move in Moves.all()) {
            val movedY = y + move.y
            val movedX = x + move.x

            if (!canMove(y, x, move)) {
                continue
            } else if (data[movedY][movedX] == 'S' && path.size > 2) {
                path.add(Coord(y, x))
                return path
            } else if (path.contains(Coord(movedY, movedX))) {
                continue
            } else {
                path.add(Coord(y, x))
                y = movedY
                x = movedX
                break;
            }
        }
    }
}


val path = solve1()
val pathLength = path.size
var floor = floor(pathLength / 2.0)
if (pathLength % 2 == 1) {
    floor + 1
}
val solution1 = floor.toInt()
println("Solution 1: $solution1")

path.add(path[0])
data class Node(val value: Char, val virtual: Boolean)

val data2 = Array<Array<Node>>(data.size * 2) { y ->
    Array(data[0].length * 2)
    { x ->
        if (y % 2 == 0 && x % 2 == 0) {
            Node(data[y / 2][x / 2], false)
        } else {
            Node('.', true)
        }
    }
}


for (index in 0..path.size - 2) {
    val from = path[index]
    val to = path[index + 1]

    val diffX = to.x - from.x
    val diffY = to.y - from.y

    data2[from.y * 2 + diffY][from.x * 2 + diffX] = Node('Z', true)
    data2[from.y * 2][from.x * 2] = Node('Z', false)
}

for (y in data2) {
    for (n in y) {
        print(n.value)
    }
    println()
}

val resMap = mutableMapOf<Coord, Boolean>()

fun isNotEnclosed(yStart: Int, xStart: Int): Boolean {
    var visited = mutableListOf<Coord>()
    var toVisit = mutableListOf<Coord>(Coord(yStart, xStart))

    while(toVisit.isNotEmpty()) {
        var curCoord = toVisit[0]
        val y = curCoord.y
        val x = curCoord.x
        toVisit.removeAt(0)
        visited.add(curCoord)

        for (yMove in y - 1..y + 1) {
            for (xMove in x - 1..x + 1) {
                val coord = Coord(yMove, xMove)

                if (yMove == y && xMove == x || (xMove != x && yMove != y)) {
                    continue
                }

                if (yMove < 0 || yMove >= data2.size || xMove < 0 || xMove >= data2[yMove].size) {
                    resMap[Coord(y, x)] = true
                    return true
                }

                if (data2[yMove][xMove].value == 'Z') {
                    continue
                } else if (visited.contains(coord)) {
                    continue
                } else if (resMap.contains(coord)) {
                    if (resMap[coord]!!) {
                        resMap[Coord(y, x)] = true
                        return true
                    } else {
                        continue
                    }
                } else {
                    if (!toVisit.contains(coord)) {
                        toVisit.add(coord)
                    }
                }
            }
        }
    }
    return false
}


val solutions = mutableListOf<Coord>()
val notEnclosed = mutableListOf<Coord>()

fun solve2(): Int {
    var solution = 0
    for (y in data2.indices) {
        println("yolo $y")
        for (x in data2[y].indices) {
            val node = data2[y][x]
            if (node.value == 'Z') {
                continue
            }
            val enclosed = isNotEnclosed(y, x)
            resMap[Coord(y, x)] = enclosed
            if (!enclosed && !node.virtual) {
                solutions.add(Coord(y, x))
                solution += 1
            } else {
                notEnclosed.add(Coord(y, x))
            }

        }
    }
    return solution
}

val solution2 = solve2()

println(solutions)

(data.indices).forEach { y ->
    (data[y].indices).forEach { x ->
        if (solutions.contains(Coord(y * 2, x * 2))) {
            print('X')
        } else if (notEnclosed.contains(Coord(y * 2, x * 2))) {
            print('.')
        } else {
            print(data[y][x])
        }
    }
    println()
}


println("Solution 2: $solution2")

// 1190 high
// 450 high
// 423
// 420
// 419
// 34