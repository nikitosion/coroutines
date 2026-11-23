package lab.proof

import lab.fixed.Indexer as FixedIndexer
import lab.given.Indexer as GivenIndexer
import org.jetbrains.lincheck.Lincheck
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class StarterTest {
    @Test
    fun givenSequentialExample() {
        val indexer = GivenIndexer()
        indexer.record("A.kt", 3)
        indexer.record("B.kt", 5)
        val result = indexer.snapshot()
        assertEquals(8L, result.totalLines)
        assertEquals(mapOf("kt" to 2), result.filesByExtension)
    }

    @Test
    fun fixedSequentialExample() {
        val indexer = FixedIndexer()
        indexer.record("A.kt", 3)
        indexer.record("B.kt", 5)
        val result = indexer.snapshot()
        assertEquals(8L, result.totalLines)
        assertEquals(mapOf("kt" to 2), result.filesByExtension)
    }

    @Test
    fun lincheckStarts() {
        Lincheck.runConcurrentTest {
            val indexer = GivenIndexer()
            val worker = thread { indexer.record("A.kt", 3) }
            worker.join()
            check(indexer.snapshot().totalLines == 3L)
        }
    }
}
