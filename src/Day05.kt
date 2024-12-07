fun main() {
    fun readRules(input: String): Pair<Map<Int, Set<Int>>, Map<Int, Set<Int>>> {
        val precedesRules = input.lines()
            .map { line -> line.split("|").map { it.toInt() } }
            .groupBy({ it[0] }, { it[1] })
            .mapValues { it.value.toSet() }
            .withDefault { emptySet() }

        val succeedRules = input.lines()
            .map { line -> line.split("|").map { it.toInt() } }
            .groupBy({ it[1] }, { it[0] })
            .mapValues { it.value.toSet() }
            .withDefault { emptySet() }


        return Pair(precedesRules, succeedRules)
    }

    fun readPrints(input: String): List<List<Int>> = input.lines()
        .map { line -> line.split(",").map { it.toInt() } }

    fun hasViolation(
        print: List<Int>,
        precedesRules: Map<Int, Set<Int>>,
        succeedRules: Map<Int, Set<Int>>
    ): Boolean {
        return print.withIndex().any { (i, page) ->
            val before = print.subList(0, i)
            val after = print.subList(i + 1, print.size)

            val precedeViolation = after.any { succeedRules.getValue(page).contains(it) }
            val succeedsViolation = before.any { precedesRules.getValue(page).contains(it) }

            precedeViolation || succeedsViolation
        }
    }

    fun comparator(precedesRules: Map<Int, Set<Int>>): Comparator<Int> {
        return Comparator { p1, p2 ->
            if (precedesRules.getValue(p1).contains(p2)) -1
            else if (precedesRules.getValue(p2).contains(p1)) 1
            else 0
        }
    }

    fun part1(input: String): Int {
        val split = input.split("\n\n")
        val (precedesRules, succeedRules) = readRules(split[0])
        val prints = readPrints(split[1])

        return prints.sumOf { print ->
            if (hasViolation(print, precedesRules, succeedRules)) 0 else print[print.size / 2]
        }
    }

    fun part2(input: String): Int {
        val split = input.split("\n\n")
        val (precedesRules, succeedRules) = readRules(split[0])
        val prints = readPrints(split[1])

        val comparator = comparator(precedesRules)

        return prints
            .sumOf { print ->
                if (hasViolation(print, precedesRules, succeedRules)) print.sortedWith(comparator)[print.size / 2]
                else 0
            }
    }

    val testInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13
        
        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()

    checkEq(part1(testInput), 143)
    checkEq(part2(testInput), 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
