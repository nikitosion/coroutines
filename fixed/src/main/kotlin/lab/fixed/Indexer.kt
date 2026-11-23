package lab.fixed

data class Stats(val totalLines: Long, val filesByExtension: Map<String, Int>)

class Indexer {
    private var totalLines = 0L
    private val filesByExtension = mutableMapOf<String, Int>()

    fun record(path: String, lines: Int) {
        totalLines += lines
        val extension = path.substringAfterLast('.')
        synchronized(filesByExtension) {
            filesByExtension[extension] = filesByExtension.getOrDefault(extension, 0) + 1
        }
    }

    fun snapshot(): Stats = Stats(totalLines, filesByExtension.toMap())
}
