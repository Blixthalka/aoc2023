package day15

import Helpers.readFileByLine

val data = readFileByLine("./data.txt")[0].split(",")

fun hash(string: String): Int {
    return string.fold(0) { acc, value ->
        ((acc + value.code) * 17) % 256
    }
}

val solution1 = data.sumOf { hash(it) }
println("Solution 1: $solution1")

fun label(string: String): String {
    return if (string.contains("=")) {
        val (label, _) = string.split("=")
        label
    } else {
        string.replace("-", "")
    }
}

val solution2 = data.fold(Array(256) { mutableListOf<String>() }) { boxes, string ->
    val label = label(string)
    val hash = hash(label)
    val boxContent = boxes[hash]
    val index = boxContent.indexOfFirst { it.startsWith(label) }

    if (string.contains("=")) {
        if (index == -1) {
            boxContent.add(string)
        } else {
            boxContent[index] = string
        }
    } else {
        if (index != -1) {
            boxContent.removeAt(index)
        }
    }
    boxes[hash] = boxContent
    boxes
}.mapIndexed { boxesIndex, box ->
    box.mapIndexed { boxIndex, value ->
        val (_, focal) = value.split("=")
        (boxesIndex + 1) * (boxIndex + 1) * focal.toInt()
    }.sum()
}.sum()

println("Solution 2: $solution2")


