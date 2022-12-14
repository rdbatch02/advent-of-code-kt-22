
fun <T : Comparable<T>> Iterable<T>.maxOrDefault(default: T): T = this.maxOrNull() ?: default
public inline fun <T> List<T>.indexOfFirstOrSize(predicate: (T) -> Boolean): Int {
    val firstIndex = this.indexOfFirst(predicate)
    return if (firstIndex > -1) firstIndex else this.size
}
fun main() {
    fun parseMap(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.map { it.digitToInt() }
        }
    }

    fun checkIsVisible(input: List<List<Int>>, row: Int, col: Int): Boolean {
        val treeHeight = input[row][col]
        val treeColumn = input.map { it[col] }
        val treeRow = input[row]
        if ((treeColumn.subList(0, row).maxOrNull() ?: -1) < treeHeight) { // Check North
            return true
        }
        if ((treeColumn.subList(row + 1, treeColumn.size).maxOrNull() ?: -1) < treeHeight) { // Check South
            return true
        }
        if ((treeRow.subList(0, col).maxOrNull() ?: -1) < treeHeight) { // Check West
            return true
        }
        if ((treeRow.subList(col + 1, treeRow.size).maxOrNull() ?: -1) < treeHeight) { // Check East
            return true
        }
        return false
    }

    fun calculateRangeScore (range: List<Int>, treeHeight: Int): Int {
        return when (val rangeStop = range.indexOfFirst { it >= treeHeight }) {
            0 -> { // Top row
                0
            }
            -1 -> { // Nothing bigger to the north
                range.size
            }
            else -> {
                range.subList(0, rangeStop + 1).size
            }
        }
    }

    fun calculateScenicScore(input: List<List<Int>>, row: Int, col: Int): Int {
        val treeHeight = input[row][col]
        val treeColumn = input.map { it[col] }
        val treeRow = input[row]

        // Reverse North and West so that list[0] is right next to the tree in question
        val northOfTree = treeColumn.subList(0, row).reversed()
        val southOfTree = treeColumn.subList(row + 1, treeColumn.size)
        val westOfTree = treeRow.subList(0, col).reversed()
        val eastOfTree = treeRow.subList(col + 1, treeRow.size)


        val northScore = calculateRangeScore(northOfTree, treeHeight)
        val southScore = calculateRangeScore(southOfTree, treeHeight)
        val westScore = calculateRangeScore(westOfTree, treeHeight)
        val eastScore = calculateRangeScore(eastOfTree, treeHeight)
        return northScore * southScore * westScore * eastScore
    }

    fun part1(input: List<String>): Int {
        val map = parseMap(input)
        var visibleTrees = 0
        for (rowIdx in map.indices) {
            val row = map[rowIdx]
            for (colIdx in row.indices) {
                if (checkIsVisible(map, rowIdx, colIdx)) {
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        val map = parseMap(input)
        val scores = mutableListOf<Int>()
        for (rowIdx in map.indices) {
            val row = map[rowIdx]
            for (colIdx in row.indices) {
                scores.add(calculateScenicScore(map, rowIdx, colIdx))
            }
        }
        return scores.max()
    }

    val input = readInput("Day08")
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
