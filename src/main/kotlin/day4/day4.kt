package day4

import println
import readInput

fun getCards(input: List<String>): List<List<List<Int>>> {
    return input.map { card ->
        card.substringAfter(":").split("|").map { it.trim().split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() } }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val cards = getCards(input)
        var total = 0.0
        cards.forEach { c ->
            val num = c[0].intersect(c[1].toSet()).count()
            if (num > 0) {
                total += Math.pow(2.0, num - 1.0)
            }
        }
        return total.toInt()
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val cards = getCards(input)
        val results = mutableMapOf<Int, Int>()
        val copies = MutableList<Int>(cards.count()) { 1 }
        for (i in 0 until cards.count()) {
            for (j in 0 until copies[i]) {
                total++
                val num = if (results.containsKey(i)) results[i] else { results[i] = cards[i][0].intersect(cards[i][1].toSet()).count(); results[i] }
                for (k in (i + 1)..(i + num!!)) {
                    copies[k] = copies[k] + 1
                }
            }
        }

        return total
    }

    //test if implementation meets criteria from the description, like:
    //val testInput = readInput("day4/test")
    //check(part2(testInput) == 30)

    val input = readInput("day4/input")
    //part1(input).println()
    part2(input).println()
}
