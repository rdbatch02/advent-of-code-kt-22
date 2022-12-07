class Move(
    val representations: List<String>,
    val points: Int,
    val beats: String
) {

    companion object {
        fun parseMove(input: String): Move {
            return when(input) {
                "A" -> rock()
                "X" -> rock()
                "B" -> paper()
                "Y" -> paper()
                "C" -> scissors()
                "Z" -> scissors()
                else -> throw NotImplementedError()
            }
        }
        private fun rock(): Move = Move(
            representations = listOf("A", "X"),
            points = 1,
            beats = "C"
        )
        private fun paper(): Move = Move(
            representations = listOf("B", "Y"),
            points = 2,
            beats = "A"
        )
        private fun scissors(): Move = Move(
            representations = listOf("C", "Z"),
            points = 3,
            beats = "B"
        )
    }
}
fun main() {
    fun parseData(input: List<String>): List<Pair<Move, Move>> {
        return input.map {
            val moves = it.split(" ")
            Pair(Move.parseMove(moves.first()), Move.parseMove(moves.last()))
        }
    }

    fun checkWin(round: Pair<Move, Move>): Boolean = round.first.representations.contains(round.second.beats)
    fun checkDraw(round: Pair<Move, Move>): Boolean = round.first.beats == round.second.beats

    fun part1(input: List<String>): Int {
        val rounds = parseData(input)
        var totalScore = 0
        rounds.forEach {
            totalScore += it.second.points // Add points for my move regardless of win
            if (checkWin(it)) {
                totalScore += 6 // 6 points for win
            }
            else if (checkDraw(it)) {
                totalScore += 3 // 3 points for draw
            }
        }
        return totalScore
    }

    val input = readInput("Day02")
    println("Part 1 - " + part1(input))
}
