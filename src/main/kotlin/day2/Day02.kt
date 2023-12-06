package day2

import println
import readInput
import kotlin.math.max

fun main() {

    val check = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun playable(g: String): Boolean {
        val sets = g.trim().split(",").map { s -> val t = s.trim().split(" "); t[1] to t[0].toInt()  }
        return sets.all { p -> p.second <= check[p.first]!! }
    }


    fun part1(input: List<String>): Int {
        val games = input.map { it.split(":")[1].split(";") }
        println(games)
        val m = games.mapIndexed{ index: Int, strings: List<String> -> index + 1 to strings.all { playable(it) } }.filter { it.second }
        return m.sumOf { it.first }
    }

    fun findMin(g: List<String>): Triple<Int, Int, Int> {
        val sets = g.map { it.trim().split(",").map { s -> val t = s.trim().split(" "); t[1] to t[0].toInt()  } }
        var red = 0
        var blue = 0
        var green = 0

        sets.flatten().forEach {
            when(it.first) {
                "blue" -> blue = max(blue, it.second)
                "green" -> green = max(green, it.second)
                "red" -> red = max(red, it.second)
            }
        }
        return Triple(red, green, blue)
    }

    fun part2(input: List<String>): Int {
        val games = input.map { it.split(":")[1].split(";") }
        return games.map {findMin(it) }
            .map { it.first * it.second * it.third }.sum()

    }

    //test if implementation meets criteria from the description, like:
    //val testInput = readInput("day2/test")
    //check(part2(testInput) == 2286)

    val input = readInput("day2/part1")
    //part1(input).println()
    part2(input).println()
}
