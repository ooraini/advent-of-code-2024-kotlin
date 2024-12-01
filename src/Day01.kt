import kotlin.math.abs

fun main() {
    fun split(input: List<String>) = input
        .map { it.split("\\s+".toRegex()) }
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .fold(Pair(listOf<Int>(), listOf<Int>())) { acc, pair ->
            with(acc) { Pair(first + pair.first, second + pair.second) }
        }

    fun part1(input: List<String>): Int {
        val (left, right) = split(input)

        return left.sorted().zip(right.sorted())
            .sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = split(input)

        val score = right.groupingBy { it }.eachCount()
        return left.sumOf { it * (score[it] ?: 0) }
    }

    val testInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()

    checkEq(part1(testInput.lines()), 11)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
