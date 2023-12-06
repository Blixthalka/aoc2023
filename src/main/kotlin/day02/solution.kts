package day02

import Helpers.readFileByLine

val maxBalls = mapOf(
    Pair("red", 12),
    Pair("green", 13),
    Pair("blue", 14)
)

val data = readFileByLine("./data1.txt")
    .map { it.replace(";", ",") }
    .map { line ->
        val splitted = line.split(":")
        val gameNr = splitted[0].replace("Game ", "").toInt()
        val gameValues = splitted[1].trim().split(",")
            .map { game ->
                val show = game.trim().split(" ")
                Pair(show[0].toInt(), show[1])
            }
        Pair(gameNr, gameValues)
    }

val solution1 = data
    .filter { (_, gameValues) -> !gameValues.any { (pickedBalls, color) -> maxBalls[color]!! < pickedBalls } }
    .fold(0) { acc, (gameNr, _) -> acc + gameNr }

println("Solution 2: $solution1")

val solution2 = data
    .map { (_, gameValues) -> gameValues }
    .map { gameValues ->
        gameValues.fold(mutableMapOf<String, Int>()) { gameMax, (pickedBalls, color) ->
            if ((gameMax[color] ?: 0) < pickedBalls) {
                gameMax[color] = pickedBalls
            }
            gameMax
        }
    }
    .map { it["green"]!! * it["blue"]!! * it["red"]!! }
    .fold(0) { acc, value -> acc + value }

println("Solution 2: $solution2")


