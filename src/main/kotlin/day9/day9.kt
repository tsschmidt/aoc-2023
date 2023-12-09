package day9

import println
import readInput

fun parse(input: List<String>): List<List<Int>> {
    return input.map { it.split(" ").map { it.toInt() } }
}

fun parseSequence(seqs: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
    val n = mutableListOf<Int>()
    for (i in 1 until seqs.last().count()) {
        n += seqs.last()[i] - seqs.last()[i - 1]
    }
    seqs.add(n)
    if (seqs.last().all { it == 0})  {
        return seqs
    }
    return parseSequence(seqs)
}

fun extrapolate(seqs: MutableList<MutableList<Int>>, index: Int): Int {
   if (index == seqs.count() - 1) {
       seqs.last().add(0)
       return extrapolate(seqs, index - 1)
   }
   if (index == 0) {
       return seqs[0].last() + seqs[1].last()
   }
   seqs[index].add(seqs[index].last() + seqs[index + 1].last())
   return extrapolate(seqs, index - 1)
}

fun extrapolatePrevious(seqs: MutableList<MutableList<Int>>, index: Int): Int {
    if (index == seqs.count() - 1) {
        seqs.last().add(0, 0)
        return extrapolatePrevious(seqs, index - 1)
    }
    if (index == 0) {
        return seqs[0].first() - seqs[1].first()
    }
    seqs[index].add(0, seqs[index].first() - seqs[index + 1].first())
    return extrapolatePrevious(seqs, index - 1)
}

fun main() {

    fun part1(input: List<String>): Int {
        val seqs = parse(input)
        var sum = 0
        for(i in seqs.indices) {
            val ps = parseSequence(mutableListOf(seqs[i].toMutableList()))
            sum += extrapolate(ps, ps.count() - 1)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val seqs = parse(input)
        var sum = 0
        for(i in seqs.indices) {
            val ps = parseSequence(mutableListOf(seqs[i].toMutableList()))
            val mps = ps.toMutableList()
            sum += extrapolatePrevious(ps, ps.count() - 1)
        }
        println(sum)
        return sum
    }

    //test if implementation meets criteria from the description, like:
    val testInput = readInput("day9/test")
    check(part2(testInput) == 2)

    val input = readInput("day9/input")
    part1(input).println()
    part2(input).println()
}
