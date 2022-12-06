fun main() {
    fun getElfTotals(input: List<String>): List<Int> {
        val elfTotals: MutableList<Int> = mutableListOf(0)
        input.forEach{
            if (it.isNotBlank()) {
                elfTotals[elfTotals.lastIndex] = elfTotals.last() + it.toInt()
            }
            else {
                elfTotals.add(0)
            }
        }
        return elfTotals
    }
    fun part1(input: List<String>): Int {
        return getElfTotals(input).maxOf { it }
    }

    fun part2(input: List<String>): Int {
        val elfTotals = getElfTotals(input).sortedDescending()
        var top3 = 0
        for (i in 0..2) {
           top3 += elfTotals[i]
        }
        return top3
    }

    val input = readInput("Day01")
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
