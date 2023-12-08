package day6

import println
import readInput

fun parse(input: List<String>): List<Race> {
     val times = input[0].split(":")[1]
             .split(" ")
             .filter { it.isNotEmpty() }
             .map { it.trim().toLong() }
    val distances = input[1].split(":")[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim().toLong() }
    val races = mutableListOf<Race>()
    for (i in 0 until times.count()) {
        races.add(Race(times[i], distances[i]))
    }
    return races
}

fun parseRace(input: List<String>): Race {
    val time = input[0].split(":")[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .reduce { acc, i -> acc + i }.toLong()

    val distance = input[1].split(":")[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .reduce { acc, i -> acc + i }.toLong()
    return Race(time, distance)
}

fun main() {

    fun part1(input: List<String>): Int {
        val races = parse(input)
        val results = races.map { r ->
            (1 until r.time).map {
                val d = (r.time - it) * it
                it to d
            }.count { it.second > r.distance }
        }
        return results.reduce { acc, i -> acc * i  }
    }

    fun part2(input: List<String>): Int {
        val race = parseRace(input)
        return (1 until race.time).map {
            val d = (race.time - it) * it
            it to d
        }.count { it.second > race.distance }
    }

    //test if implementation meets criteria from the description, like:
    //val testInput = readInput("day6/test")
    //check(part2(testInput) == 71503)

    val input = readInput("day6/input")
    //part1(input).println()
    part2(input).println()
}

data class Race(val time: Long, val distance: Long)
