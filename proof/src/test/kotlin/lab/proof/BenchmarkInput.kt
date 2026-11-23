package lab.proof

import kotlin.random.Random

const val THREADS = 8
const val FILES = 200_000
const val SEED = 42L

fun makeInput(): List<List<Pair<String, Int>>> {
    require(FILES > 0 && THREADS > 0 && FILES % THREADS == 0)
    val rnd = Random(SEED)
    val exts = listOf("kt", "java", "md", "kts", "json")
    val all = List(FILES) { i ->
        "src/m${i % 32}/File$i.${exts[rnd.nextInt(exts.size)]}" to (1 + rnd.nextInt(400))
    }
    return all.chunked(FILES / THREADS)
}
