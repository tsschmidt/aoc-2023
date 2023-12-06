package day1

import println
import readInput

fun main() {

    val m = mapOf(
        "zero" to "0",
        "eight" to "8",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "nine" to "9")

    fun foundNumber(s: String): Boolean {
        return s.endsWith("one") ||
                s.endsWith("two") ||
                s.endsWith("three") ||
                s.endsWith("four") ||
                s.endsWith("five") ||
                s.endsWith("six") ||
                s.endsWith("seven") ||
                s.endsWith("eight") ||
                s.endsWith("nine") ||
                s.endsWith("zero")
    }

    fun foundNumberLast(s: String): Boolean {
        return s.startsWith("one") ||
                s.startsWith("two") ||
                s.startsWith("three") ||
                s.startsWith("four") ||
                s.startsWith("five") ||
                s.startsWith("six") ||
                s.startsWith("seven") ||
                s.startsWith("eight") ||
                s.startsWith("nine") ||
                s.startsWith("zero")
    }

    fun transformNumber(s: String?): Int {
        s ?: throw IllegalArgumentException("null")
        return if (s.last().isDigit()) {
            s.last().digitToInt()
        } else if (s.first().isDigit()) {
            s.first().digitToInt()
        } else {
            when {
                s.contains("zero") -> 0
                s.contains("one") -> 1
                s.contains("two") -> 2
                s.contains("three") -> 3
                s.contains("four") -> 4
                s.contains("five") -> 5
                s.contains("six") -> 6
                s.contains("seven") -> 7
                s.contains("eight") -> 8
                s.contains("nine") -> 9
                else -> { println(s); throw IllegalArgumentException("not found") }
            }
        }
    }

    fun part1(input: List<String>): Int {

        return input.map { it.filter { c -> c.isDigit() } }
            .map { ("${it.first()}${it.last()}").toInt()}.sum()

    }

    fun part2(input: List<String>): Int {
        return input.map{
            val firstF = it.runningFold("", { acc, c -> acc + c})
            val lastF = it.reversed().runningFold("", { acc: String, c: Char -> c + acc })
            //println(lastF)
            val first = transformNumber(firstF.find { it.isNotEmpty() && (it.last().isDigit() || foundNumber(it)) })
            val last = transformNumber(lastF.find { it.isNotEmpty() && (it.first().isDigit() || foundNumberLast(it))})
            "$first$last".toInt()
        }.sum()
    }

    //test if implementation meets criteria from the description, like:
    val testInput = readInput("day1_part2_test")
    check(part2(testInput) == 281)

    val input = readInput("day1_part1")
    part1(input).println()
    part2(input).println()
}
