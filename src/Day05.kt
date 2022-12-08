import java.util.EmptyStackException
import java.util.Stack
import kotlin.Error

data class CrateMove(val quantity: Int, val source: Int, val dest: Int)
fun main() {
    fun parseCurrentStacks(input: List<String>): List<Stack<String>> {
        val endLineIdx = input.indexOfFirst { it.isEmpty() }
        val endLine = input[endLineIdx - 1]
        val numberOfStacks = endLine.trim().split("   ").last().toInt()
        val stacks: List<Stack<String>> = List(numberOfStacks) { Stack() }
        input.subList(0, endLineIdx).reversed().forEach {line ->
            Regex("(^\\s{3}|\\s{4}|[A-Z])").findAll(line).toList().forEachIndexed { index, matchResult ->
                if (matchResult.value.isNotBlank()) {
                    stacks[index].push(matchResult.value)
                }
            }
        }
        return stacks
    }

    fun parseMoves(input: List<String>): List<CrateMove> {
        val separatorLineIdx = input.indexOfFirst { it.isEmpty() }
        val moveList = input.subList(separatorLineIdx + 1, input.size)
        return moveList.map {
            val matchedValues = Regex("move (\\d+) from (\\d+) to (\\d+)").find(it)?.groupValues ?: throw Error("Error parsing moves")
            CrateMove(matchedValues[1].toInt(), matchedValues[2].toInt(), matchedValues[3].toInt())
        }
    }

    fun printStacks(input: List<Stack<String>>) {
        val stacks = input.toList() // Make deep copy
        var currentDepth = stacks.maxOf { it.size }
        while (stacks.last().isNotEmpty()) {
            stacks.forEach {
                if (it.size < currentDepth) {
                    print("  ")
                }
                else {
                    print(it.pop() + " ")
                }
            }
            println("")
            currentDepth--
        }
        println("1 2 3 4 5 6 7 8 9")
    }

    fun part1(input: List<String>): List<Stack<String>> {
        val stacks = parseCurrentStacks(input)
        val moves = parseMoves(input)
        moves.forEach { move ->
            for (i in 0 until move.quantity) {
                stacks[move.dest - 1].push(stacks[move.source - 1].pop())
            }
        }
        return stacks
    }

    fun part2(input: List<String>): List<Stack<String>> {
        val stacks = parseCurrentStacks(input)
        val moves = parseMoves(input)
        moves.forEach { move ->
            val moveStack = Stack<String>()
            for (i in 0 until move.quantity) {
                moveStack.push(stacks[move.source - 1].pop())
            }
            while (moveStack.isNotEmpty()) {
                stacks[move.dest - 1].push(moveStack.pop())
            }
        }
        return stacks
    }

    val input = readInput("Day05")
    println("Part 1: ")
    printStacks(part1(input))
    println("Part 2: ")
    printStacks(part2(input))
}
