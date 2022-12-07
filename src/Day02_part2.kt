data class Move_Part2(
    val points: Int,
    val beats: String,
    val losesTo: String
) {
    companion object {
        fun rock() = Move_Part2(points = 1, beats = "C", losesTo = "B")
        fun paper() = Move_Part2(points = 2, beats = "A", losesTo = "C")
        fun scissors() = Move_Part2(points = 3, beats = "B", losesTo = "A")
    }
}
fun main() {
    val availableMoves: Map<String, Move_Part2> = mapOf(
        "A" to Move_Part2.rock(),
        "B" to Move_Part2.paper(),
        "C" to Move_Part2.scissors()
    )
    fun parseData(input: List<String>): List<Pair<Move_Part2, String>> {
        return input.map {
            val round = it.split(" ")
            Pair(availableMoves[round.first()] ?: throw NotImplementedError(), round.last())
        }
    }

    fun part2(input: List<String>): Int {
        val rounds = parseData(input)
        var totalScore = 0
        rounds.forEach {
            totalScore += when (it.second) {
                // I need to win
                "X" -> availableMoves[it.first.beats]?.points ?: throw NotImplementedError()
                // I need to draw
                "Y" -> 3 + it.first.points
                // I need to lose
                "Z" -> 6 + (availableMoves[it.first.losesTo]?.points ?: throw NotImplementedError())
                else -> throw NotImplementedError()// Shouldn't get here, but keeping it for clarity on the above conditions
            }
        }
        return totalScore
    }

    val input = readInput("Day02")
    println("Part 2 - " + part2(input))
}
