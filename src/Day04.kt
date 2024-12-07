enum class Direction {
    UP, DOWN, LEFT, RIGHT, UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT;

    fun transform(pair: Pair<Int, Int>): Pair<Int, Int> {
        val (x, y) = pair
        return when (this) {
            UP -> Pair(x, y - 1)
            DOWN -> Pair(x, y + 1)
            LEFT -> Pair(x - 1, y)
            RIGHT -> Pair(x + 1, y)
            UP_RIGHT -> UP.transform(RIGHT.transform(pair))
            DOWN_RIGHT -> DOWN.transform(RIGHT.transform(pair))
            DOWN_LEFT -> DOWN.transform(LEFT.transform(pair))
            UP_LEFT -> UP.transform(LEFT.transform(pair))
        }
    }

    fun transform(pair: Pair<Int, Int>, times: Int): Pair<Int, Int> =
        if (times == 0) pair
        else transform(transform(pair), times - 1)
}

fun main() {
    // top left is (0, 0)
    fun grid(input: String): List<List<Char>> {
        return input.lines().map { it.toCharArray().toList() }
    }

    fun List<List<Any>>.points(): List<Pair<Int, Int>> =
        flatMapIndexed { y, xs -> List(xs.size) { x -> Pair(x, y) } }

    fun List<List<Char>>.at(point: Pair<Int, Int>): String {
        val (x, y) = point
        return if (y >= 0 && y < this.size && x >= 0 && x < this[0].size) this[y][x].toString()
        else ""
    }


    fun List<List<Char>>.safeSlice(
        point: Pair<Int, Int>,
        direction: Direction,
        size: Int = 4
    ): String {
        val (x, y) = point

        return (0..<size).joinToString(separator = "") { at(direction.transform(Pair(x, y), it)) }
    }

    fun part1(input: String): Int {
        val grid = grid(input)
        return grid.points()
            .flatMap { point -> Direction.entries.map { grid.safeSlice(point, it) } }
            .count { it == "XMAS" }
    }

    fun part2(input: String): Int {
        val grid = grid(input)
        return grid.points().count { (x, y) ->
            listOf(
                grid.safeSlice(Pair(x - 1, y - 1), Direction.DOWN_RIGHT, size = 3),
                grid.safeSlice(Pair(x + 1, y - 1), Direction.DOWN_LEFT, size = 3),
                grid.safeSlice(Pair(x + 1, y + 1), Direction.UP_LEFT, size = 3),
                grid.safeSlice(Pair(x - 1, y + 1), Direction.UP_RIGHT, size = 3)
            ).count { it == "MAS" } >= 2
        }
    }

    val testInput = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()

    checkEq(part1(testInput), 18)
    checkEq(part2(testInput), 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
