package day8

import lcm
import println
import readInput

fun parse(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val instructions = input[0]
    val map = buildMap<String, Pair<String, String>> {
        for (i in 2 until input.count()) {
            val (key, pair) = input[i].split(" = ")
            put(key, pair.substring(1, 4) to pair.substring(6, 9))
        }
    }
    return instructions to map
}
fun main() {

    fun part1(input: List<String>): Int {
        val (instructions, map) = parse(input)
        var index = 0
        var steps = 0
        var start = "AAA"
        while(start != "ZZZ") {
            if (index == instructions.length) {
                index = 0
            }
            start = if (instructions[index] == 'L') {
                map[start]!!.first
            } else {
                map[start]!!.second
            }
            index++
            steps++
        }
        return steps
    }

    fun part2(input: List<String>): Long {
        val (instructions, map) = parse(input)
        val start = map.keys.filter { it.last() == 'A' }.toMutableList()
        var index = 0
        val steps = mutableListOf<Long>()
        for(i in start.indices) {
            steps.add(0)
            while (start[i].last() != 'Z') {
                if (index == instructions.length) {
                    index = 0
                }
                start[i] = if (instructions[index] == 'L') {
                    map[start[i]]!!.first
                } else {
                    map[start[i]]!!.second
                }
                index++
                steps[steps.count() -1] += 1L
            }
        }
        return lcm(steps)
    }

    //test if implementation meets criteria from the description, like:
    //val testInput = readInput("day8/test2")
    //check(part2(testInput) == 6uL)

    val input = readInput("day8/input")
    //part1(input).println()
    part2(input).println()

}