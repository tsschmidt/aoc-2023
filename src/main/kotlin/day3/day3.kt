package day3

import println
import readInput
import java.lang.Character.isDigit

typealias Point = Pair<Int, Int>
typealias Part = Pair<String, Int>

fun findPoints(row: Int, col: Int, length: Int, maxCol: Int, maxRows: Int): List<Point> {
    val startCol = (col - 1).coerceAtLeast(0)
    val endCol = (col + length).coerceAtMost(maxCol - 1)
    val startRow  = (row - 1).coerceAtLeast(0)
    val endRow = (row + 1).coerceAtMost(maxRows - 1)
    val pts = mutableListOf<Pair<Int, Int>>()
    (startRow..endRow).forEach { r ->
        (startCol..endCol).forEach{ c ->
            pts.add(r to c)
        }
    }
    return pts.toList()
}


fun hasAdjacentSymbol(row: Int, input: List<String>, part: Part ): Boolean {
    val start = part.second
    val pts = findPoints(row, start, part.first.length, input[row].length, input.count())
    val hasSymbol  = pts.any { input[it.first][it.second] != '.' && !input[it.first][it.second].isDigit()}
    return hasSymbol
}

fun findNumbers(input: String): List<Part> {
    var part: String = ""
    var start = -1
    val parts = mutableListOf<Part>()
    input.forEachIndexed {index, c ->
        if (c.isDigit()) {
            part += c
            if (start == -1) {
                start = index
            }
        } else if (part.isNotEmpty()) {
            parts.add(part to start)
            part = ""
            start = -1
        }
    }
    if (part.isNotEmpty()) {
        parts.add(part to start)
    }
    return parts.toList()
}

fun checkLeft(r: Int, c: Int, input: List<String>): List<Int> {
    if (c == 0) {
        return emptyList()
    }
    var l = ""
    var i = c - 1
    while(i >= 0 && input[r][i].isDigit()) {
        l = input[r][i] + l
        i--
    }
    if (l.isNotEmpty()) {
        return listOf(l.toInt())
    }
    return emptyList()
}

fun checkRight(r: Int, c: Int, input: List<String>): List<Int> {
    if (c + 1 == input[r].length) {
        return emptyList()
    }
    var l = ""
    var i = c + 1
    while(i < input[r].length && input[r][i].isDigit()) {
        l += input[r][i]
        i++
    }
    if(l.isNotEmpty()) {
        return listOf(l.toInt())
    }
    return emptyList()
}

fun checkAbove(r: Int, c: Int, input: List<String>): List<Int> {
    if (r == 0) {
        return emptyList()
    }
    if(!input[r-1][c].isDigit()) {
        return checkLeft(r-1, c, input) + checkRight(r - 1, c, input)
    } else {
        var start = c
        while(input[r-1][start].isDigit()) {
            start--
        }
        return checkRight(r-1, start, input)
    }
}

fun checkBelow(r: Int, c: Int, input: List<String>): List<Int> {
    if (r + 1 == input.count()) {
        return emptyList()
    }
    if(!input[r+1][c].isDigit()) {
        return checkLeft(r+1, c, input) + checkRight(r + 1, c, input)
    } else {
        var start = c
        while(input[r+1][start].isDigit()) {
            start--
        }
        return checkRight(r+1, start, input)
    }
}


fun findGears(input: List<String>): List<Point> {
    val gears = mutableListOf<Point>()
    input.forEachIndexed{ ri, r ->
        r.forEachIndexed {ci, c ->
            if (c == '*') {
                val g = checkLeft(ri, ci, input) + checkRight(ri, ci, input) + checkAbove(ri, ci, input) + checkBelow(ri, ci, input)
                if (g.count() == 2) {
                    gears.add(g[0] to g[1])
                }
            }
        }
    }
    return gears
}


fun main() {

    fun part1(input: List<String>): Int {
        return input
            .mapIndexed { index, y ->
                findNumbers(y).filter { p ->  hasAdjacentSymbol(index, input, p) }
            }
            .flatten()
            .sumOf { it.first.toInt() }
    }

    fun part1a(input: List<String>): Int {
        return input.map { findNumbers(it) }
            .flatten()
            .filterIndexed { index, p -> hasAdjacentSymbol(index, input, p) }
            .sumOf { it.first.toInt() }
    }


    fun part2(input: List<String>): Long {
        println(findGears(input).sumOf { it.first.toLong() * it.second.toLong() })

        return 0
    }


    //test if implementation meets criteria from the description, like:
    //val testInput = readInput("day3/test")
    //check(part1(testInput) == 4361 - 35 + 633)

    val input = readInput("day3/input")
    part1(input).println()
    //part2(input).println()
}
