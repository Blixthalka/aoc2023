package day2

import Helpers.readFileByLine

val maxi = mapOf(
    Pair("red", 12),
    Pair("green", 13),
    Pair("blue", 14)
)

val solution1 = readFileByLine("./data1.txt")
    .map { it.replace(";", ",") }
    .map {
        val splitted = it.split(":")
        val gameNr = splitted[0].replace("Game ", "").toInt()
        val values = splitted[1].trim().split(",")
            .map {
                val show = it.trim().split(" ")
                Pair(show[0].toInt(), show[1])
            }
        Pair(gameNr, values)
    }
    .filter { value -> !value.second.any { maxi[it.second]!! < it.first } }
    .fold(0) { acc, value ->  acc + value.first }

println("Solution 2: $solution1")

val solution2 = readFileByLine("./data1.txt")
    .map {
        val splitted = it.split(":")
        val gameNr = splitted[0].replace("Game ", "").toInt()
        val values = splitted[1].trim().split(";")
            .map {
               value -> value.split(",")
                   .map {
                       val show = it.trim().split(" ")
                       Pair(show[0].toInt(), show[1])
                   }
            }
        Pair(gameNr, values)
    }
    .map { game ->
        val gameMax = mutableMapOf<String, Int>()
        game.second.forEach { section -> section.forEach {
            var maxValue = 0;
            if (gameMax.containsKey(it.second)) {
                val currentMax = gameMax[it.second]
                if (currentMax!! > it.first) {
                    maxValue = currentMax
                } else {
                    maxValue = it.first
                }
            } else {
                maxValue = it.first
            }
            gameMax[it.second] = maxValue
        } }
        gameMax
    }
    .map { it["green"]!! * it["blue"]!! * it["red"]!! }
    .fold(0) { acc, value ->  acc + value }

println("Solution 2: $solution2")


