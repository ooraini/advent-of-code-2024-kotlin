
fun main() {
    fun safe1(report: List<Int>): Boolean {
        val increasing = report
            .zipWithNext { a, b -> b - a }
            .all { it in 1..3 }

        val decreasing = report
            .zipWithNext { a, b -> b - a }
            .all { it in -3..-1 }

        return increasing || decreasing
    }

    fun safe2(report: List<Int>): Boolean {
        report.indices.forEach { index ->
            val sublist = mutableListOf<Int>()
            sublist.addAll(report)
            sublist.removeAt(index)
            if (safe1(sublist)) return true
        }
        return false
    }

    fun part1(input: List<String>): Int = input
        .map { row -> row.split(" ").map { string -> string.toInt() } }
        .count { safe1(it) }

    fun part2(input: List<String>): Int = input
        .map { row -> row.split(" ").map { string -> string.toInt() } }
        .count { safe2(it) }

    val testInput = """
        1 3 2 4 5
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    checkEq(part1(testInput.lines()), 2)
    checkEq(part2(testInput.lines()), 4)

    val input = readInputLines("Day02")
    part1(input).println()
    part2(input).println()
}
