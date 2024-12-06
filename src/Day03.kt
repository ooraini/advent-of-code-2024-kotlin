fun main() {
    fun part1(input: String): Int {
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        return regex.findAll(input)
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: String): Int {
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

        val dos = sortedSetOf<Int>()
        Regex("do\\(\\)").findAll(input)
            .map { it.range.first }
            .forEach { dos.add(it) }


        dos.add(0) // implicit 'do' at the start

        val donts = sortedSetOf<Int>()
        Regex("don't\\(\\)")
            .findAll(input)
            .map { it.range.first }
            .forEach { donts.add(it) }

        donts.add(input.length) // Implicit 'don't' at the end

        // for each 'don't': find the least 'do' that greater than the previous 'don't'
        val safe = donts
            .filter { dos.floor(it)!! > (donts.floor(it - 1) ?: -1) }
            .map { dos.ceiling(donts.floor(it - 1) ?: -1)!!..it }

        return regex.findAll(input)
            .filter { result -> safe.any { result.range.first in it } }
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    checkEq(part1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"), 161)
    checkEq(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"), 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
