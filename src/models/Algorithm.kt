package models

import utils.createBestCaseIntArray
import utils.createRandomIntArray
import utils.createWorstCaseIntArray
import kotlin.system.measureNanoTime

enum class AlgorithmScenario{
    WORST_CASE,
    BEST_CASE,
    MEDIUM_CASE
}

abstract class Algorithm (scenario: AlgorithmScenario){
    abstract val algorithm : () -> Any
    protected var dataset = arrayOf<Int>()
    var runTimeInNanos = -1L

    protected var refreshDataset = { size : Long ->
        this.dataset = when(scenario){
            AlgorithmScenario.WORST_CASE -> createWorstCaseIntArray(size.toInt(), 6, 1000)
            AlgorithmScenario.BEST_CASE -> createBestCaseIntArray(size.toInt(), 6, 1000)
            AlgorithmScenario.MEDIUM_CASE -> createRandomIntArray(size.toInt(), 6, 1000)
        }
    }

    fun run(size: Long){
        refreshDataset(size)
        runTimeInNanos = measureNanoTime {
            algorithm()
        }
    }
}