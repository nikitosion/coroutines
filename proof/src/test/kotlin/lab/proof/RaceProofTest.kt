package lab.proof

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("race-proof")
class RaceProofTest {
    @Test
    fun checkTarget() {
        val target = requireNotNull(System.getProperty("kfd.target"))
        require(target in setOf("given", "fixed")) { "Unknown target: $target" }
        val failures = (1..100).count { defectReproduced(target) }
        println("RACE-PROOF: $failures/100 runs failed on $target/")
        assertEquals(0, failures, "Counterexample found")
    }

    private fun defectReproduced(target: String): Boolean =
        TODO("Implement the Lincheck scenario for $target")
}
