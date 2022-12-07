fun main() {
    val items = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" // I wonder if there's a slick way to do this, will check later
    fun pointsOfItem(item: String): Int = items.indexOf(item) + 1

    fun part1(input: List<String>): Int {
        var totalPriority = 0
        input.forEach {
            val firstHalf = it.substring(0, it.length/2)
            val secondHalf = it.removePrefix(firstHalf)
            val dupes = firstHalf.filter { firstHalfItem -> secondHalf.contains(firstHalfItem) }.split("").distinct().joinToString("")
            totalPriority += dupes.sumOf { dupe -> pointsOfItem(dupe.toString()) }
        }
        return totalPriority
    }

    fun part2(input: List<String>): Int {
        val groups: MutableList<MutableList<String>> = mutableListOf(mutableListOf())
        input.forEach {  // Setup groups of 3
            if (groups.last().size < 3) {
                groups.last().add(it)
            }
            else {
                groups.add(mutableListOf(it))
            }
        }
        val priorityItem: List<String> = groups.map { group ->
            group.first().find { item -> group[1].contains(item) && group[2].contains(item) }.toString()
        }
        return priorityItem.sumOf { pointsOfItem(it) }
    }

    val input = readInput("Day03")
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
