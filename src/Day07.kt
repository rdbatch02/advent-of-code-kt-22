import java.util.*

interface FileSystemObject {
    val name: String
}
fun main() {
    data class File(
        override val name: String,
        val size: Int
    ): FileSystemObject
    data class Directory(
        override val name: String,
        val contents: MutableMap<String, FileSystemObject> = mutableMapOf(),
        val parentDir: Directory? = null
    ): FileSystemObject {
        fun getSize(): Int {
            return contents.map {
                if (it.value is Directory) {
                    (it.value as Directory).getSize()
                } else {
                    (it.value as File).size
                }
            }.sum() ?: 0
        }
    }

    fun parseFileSystem(input: List<String>): Directory {
        val dirStack: Stack<String> = Stack()
//        val fileSystem: MutableMap<String, FileSystemObject> = mutableMapOf("/" to Directory("/", mutableMapOf()))
        val rootDirectory = Directory("/")
        var currentDirectory: Directory = rootDirectory
        input.subList(1, input.size).forEach {terminalOutput -> // Skip the first command which cd's into root
            val outputArray = terminalOutput.split(" ")
            if (outputArray.first() == "$") { // Parsing a command
                if (outputArray[1] == "cd") { // change dir
                    if (outputArray.last() == "..") { // Up dir
                        dirStack.pop()
                        currentDirectory = currentDirectory.parentDir ?: currentDirectory // Don't go up if we're at the root
                    }
                    else { // Down dir
                        val newDirName = outputArray.last()
                        dirStack.push(newDirName)
                        currentDirectory = currentDirectory.contents?.get(newDirName) as Directory
                    }
                }
                // else would be ls which is effectively a no-op for state
            }
            else { // Parsing a file or directory
                val objectName = outputArray.last()
                if (outputArray.first() == "dir") { // Parsing a directory
                    val newDir = Directory(objectName, parentDir = currentDirectory)
                    currentDirectory.contents[objectName] = newDir
                }
                else { // Parsing a file
                    val newFile = File(objectName, outputArray.first().toInt())
                    currentDirectory.contents[objectName] = File(objectName, outputArray.first().toInt())
                }
            }
        }
        return rootDirectory
    }

    fun sumDirSizesUnderThreshold(threshold: Int, dir: Directory): Int {
        var sizeUnderThreshold = 0
        if (dir.getSize() <= threshold) {
            sizeUnderThreshold += dir.getSize()
        }
        dir.contents.values.filterIsInstance<Directory>().forEach {
            sizeUnderThreshold += sumDirSizesUnderThreshold(threshold, it)
        }
        return sizeUnderThreshold
    }

    fun part1(input: List<String>): Int {
        val rootDirectory = parseFileSystem(input)
        return sumDirSizesUnderThreshold(100000, rootDirectory)
    }

    fun findSmallestDirectoryAboveThreshold(threshold: Int, dir: Directory): Directory {
        var smallestDirSeen = dir
        dir.contents.values.filterIsInstance<Directory>().forEach {
            val smallestChildDir = findSmallestDirectoryAboveThreshold(threshold, it)
            if (smallestChildDir.getSize() > threshold && smallestChildDir.getSize() < smallestDirSeen.getSize()) {
                smallestDirSeen = smallestChildDir
            }
        }
        return smallestDirSeen
    }

    fun part2(input: List<String>): Int {
        // Delete at least 30000000 in a single directory
        val totalSpace = 70000000
        val updateSize = 30000000
        val rootDirectory = parseFileSystem(input)
        val freeSpace = totalSpace - rootDirectory.getSize()
        println("Free space - " + freeSpace)
        val neededSpace = updateSize - freeSpace
        println("Need - " + neededSpace)

        return findSmallestDirectoryAboveThreshold(neededSpace, rootDirectory).getSize()
    }

    val input = readInput("Day07")
    println("Part 1 - " + part1(input))
    println("Part 2 - " + part2(input))
}
