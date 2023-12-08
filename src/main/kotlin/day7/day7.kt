package day7

import println
import readInput

typealias Play = Pair<Hand, Int>

fun parse(input: List<String>): List<Play> {
    return input.map { it.split(" ") }
            .map { Hand(it[0]) to it[1].toInt() }
}

fun main() {


    fun part1(input: List<String>): Int {
        val r = parse(input).sortedWith(HandComparator())
                .mapIndexed { index, p ->
                    p.second * (index + 1)
                }
                .sum()
        return r
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    //test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/test")
    check(part1(testInput) == 5905)

    val input = readInput("day7/input")
    part1(input).println()
    part2(input).println()
}

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
    UNKNOWN
}
data class Hand(val cards: String) {
    /*
    val type: HandType
        get() {
            val g = cards.groupBy { it }
            return when {
               g.size == 5 -> HandType.HIGH_CARD
               g.size == 1 -> HandType.FIVE_OF_A_KIND
               g.size == 2 && g.any { it.value.size == 4 } -> HandType.FOUR_OF_A_KIND
               g.size == 2 && g.any { it.value.size == 3 } -> HandType.FULL_HOUSE
               g.size == 3 && g.any { it.value.size == 3 } -> HandType.THREE_OF_A_KIND
               g.size == 3 && g.any { it.value.size == 2 } -> HandType.TWO_PAIR
               g.size == 4 -> HandType.ONE_PAIR
               else -> HandType.UNKNOWN
            }
        }
    */
    val type: HandType
        get() {
            val g = cards.groupBy { it }.toMutableMap()
            if (g.containsKey('J') && g['J']!!.size < 5) {
                val j = g.remove('J')
                val max = g.values.maxBy { it.count() }
                val s = buildList<Char> {
                    addAll(g[max[0]]!!)
                    addAll(j!!)
                }
                g[max[0]] = s
                g.remove('J')
            }
            val t = when {
                g.size == 5 -> HandType.HIGH_CARD
                g.size == 1 -> HandType.FIVE_OF_A_KIND
                g.size == 2 && g.any { it.value.size == 4 } -> HandType.FOUR_OF_A_KIND
                g.size == 2 && g.any { it.value.size == 3 } -> HandType.FULL_HOUSE
                g.size == 3 && g.any { it.value.size == 3 } -> HandType.THREE_OF_A_KIND
                g.size == 3 && g.any { it.value.size == 2 } -> HandType.TWO_PAIR
                g.size == 4 -> HandType.ONE_PAIR
                else -> HandType.UNKNOWN
            }
            if (cards.contains('J')) {
                println("${g} -- $t")
            }
            return t
        }


}

val cards = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

val cards2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

class HandComparator() : Comparator<Play> {
    override fun compare(p0: Pair<Hand, Int>?, p1: Pair<Hand, Int>?): Int {
        p1 ?: throw IllegalArgumentException()
        p0 ?: throw IllegalArgumentException()
        val c = p0.first.type.ordinal.compareTo(p1.first.type.ordinal)
        return if ( c != 0) return c else findHighCard(p0, p1, 0)
    }

    fun findHighCard(p0: Play, p1: Play, index: Int): Int {
        return if (p0.first.cards[index] == p1.first.cards[index]) {
            findHighCard(p0, p1, index + 1)
        } else {
            cards2.indexOf(p0.first.cards[index]).compareTo(cards2.indexOf(p1.first.cards[index]))
        }
    }
}