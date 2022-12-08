fun main() {
    fun parseInput(input: List<String>): List<List<IntRange>> {
        return input.map { inputLine ->
            val assignmentStrings = inputLine.split(",")
            assignmentStrings.map {
                val assignmentSections = it.split("-")
                IntRange(assignmentSections.first().toInt(), assignmentSections.last().toInt())
            }
        }
    }

    fun part1(input: List<String>): Int {
        val ranges = parseInput(input)
        return ranges.count {rangePair ->
            rangePair.first() in rangePair.last() || rangePair.last() in rangePair.first()
        }
    }

    fun part2(input: List<String>): Int {
        val ranges = parseInput(input)
        return ranges.count {rangePair ->
            rangePair.first().partiallyOverlaps(rangePair.last()) || rangePair.last().partiallyOverlaps(rangePair.first())
        }
    }

    val input = readInput("Day04")
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
