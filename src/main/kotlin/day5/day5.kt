package day5

import println
import readInput

fun parseMaps(input: List<String>): List<Table> {

    val tables = mutableListOf<Table>()
    val maps = mutableListOf<Converter>()

    for( i in 3 until input.count()) {
        if (input[i].isEmpty()) continue
        if (input[i].contains(":")) { tables.add(Table(maps)); maps.clear(); continue }
        val (dest, source, range) = input[i].trim().split(" ").map { it.toLong() }
        maps.add(Converter(dest, source, range))
    }
    tables.add(Table(maps))
    return tables
}

fun parse(input: List<String>): Pair<List<Long>,List<Table>> {
    return input[0].split(":")[1].trim().split(" ").map { it.toLong() } to parseMaps(input)
}

fun main() {

    fun part1(input: List<String>): Long {
        val (seeds,tables) = parse(input)
        var min = Long.MAX_VALUE
        for(seed in seeds) {
            val soil = tables[0].convert(seed)
            val fertilizer = tables[1].convert(soil)
            val water = tables[2].convert(fertilizer)
            val light = tables[3].convert(water)
            val temp = tables[4].convert(light)
            val humidity = tables[5].convert(temp)
            val location = tables[6].convert(humidity)
            min = if (location < min) location else min
        }
        return min
    }

    fun part2(input: List<String>): Long {
        val (seeds, tables) = parse(input)
        val maps = parseMaps(input)
        var min = Long.MAX_VALUE
        val check = mutableListOf<LongRange>(seeds[0] until (seeds[0] + seeds[1]))
        for (i in 2 until seeds.count() step 2) {
            var start = seeds[i]
            val end = start + seeds[i + 1]
            check.add(start until end)
        }
        for(c in check) {
            for (seed in c) {
                val soil = tables[0].convert(seed)
                val fertilizer = tables[1].convert(soil)
                val water = tables[2].convert(fertilizer)
                val light = tables[3].convert(water)
                val temp = tables[4].convert(light)
                val humidity = tables[5].convert(temp)
                val location = tables[6].convert(humidity)
                min = if (location < min) location else min
            }
        }
        return min
    }

    //test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/test")
    check(part2(testInput) == 46L)

    val input = readInput("day5/input")
    part1(input).println()
    part2(input).println()
}

class Converter(val dest: Long, val source: Long, val offset: Long) {

    fun applies(from: Long) = source <= from && from < source + offset

    fun convert(from: Long) = if (applies(from)) dest + (from - source) else from
}

class Table(val converters: List<Converter>) {
    val minApply = converters.map { it.source }.min()
    val maxApply = converters.map { it.source + it.offset}.max()

    fun rangeApplies(seed: LongRange) = seed.first >= minApply && seed.last < maxApply

    fun convert(seed: Long): Long {
        for (c in converters) {
            if(c.applies(seed)) {
                return c.convert(seed)
            }
        }
        return seed
    }

}