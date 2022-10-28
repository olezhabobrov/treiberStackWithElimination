package mpp.stackWithElimination

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.jetbrains.kotlinx.lincheck.*
import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.*
import org.jetbrains.kotlinx.lincheck.strategy.stress.*
import org.jetbrains.kotlinx.lincheck.verifier.VerifierState
import org.junit.*
import java.io.File

class TreiberStackWithEliminationTest {
    private val q = TreiberStackWithElimination<Int>()

    @Operation
    fun push(x: Int): Unit = q.push(x)

    @Operation
    fun pop(): Int? = q.pop()

    @Test
    fun stressTest() =
        StressOptions()
            .iterations(100)
            .invocationsPerIteration(50_000)
            .threads(3)
            .actorsPerThread(3)
            .sequentialSpecification(IntStackSequential::class.java)
            .logLevel(LoggingLevel.INFO)
            .check(this::class.java)
    }

class IntStackSequential {
    private val q = ArrayDeque<Int>()

    fun push(x: Int) {
        q.addLast(x)
    }

    fun pop(): Int? = q.removeLastOrNull()

}