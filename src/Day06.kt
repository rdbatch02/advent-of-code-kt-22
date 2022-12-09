fun main() {
    fun isMarker(input: String): Boolean {
        input.forEach { char ->
            val charCount = input.count { it == char }
            if (charCount > 1) {
                return false
            }
        }
        return true
    }
    fun part1(input: String): Int {
        for (i in 0..input.length) {
            val marker = isMarker(input.substring(i, i + 4))
            if (marker) {
                return i + 4
            }
        }
        return -1
    }

    fun part2(input: String): Int {
        for (i in 0..input.length) {
            val marker = isMarker(input.substring(i, i + 14))
            if (marker) {
                return i + 14
            }
        }
        return -1
    }

    val input = readInput("Day06").first()
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
