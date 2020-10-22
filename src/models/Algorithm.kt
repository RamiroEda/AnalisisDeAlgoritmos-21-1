package models

import utils.*
import kotlin.system.measureNanoTime

enum class AlgorithmScenario (private val title: String){
    SEARCH_WORST_CASE("Busqueda: Peor caso"),
    SEARCH_BEST_CASE("Busqueda: Mejor caso"),
    SORT_WORST_CASE("Ordenar: Peor caso"),
    SORT_BEST_CASE("Ordenar: Mejor caso"),
    RANDOM_CASE("Caso aleatorio");


    override fun toString(): String {
        return title
    }
}

abstract class Algorithm (scenario: AlgorithmScenario){
    abstract val algorithm : () -> Any
    protected var dataset = arrayOf<Int>()
    var runTimeInNanos = -1L

    protected var refreshDataset = { size : Long ->
        this.dataset = when(scenario){
            AlgorithmScenario.SEARCH_WORST_CASE -> createSearchWorstCaseIntArray(size.toInt(), 6, 1000)
            AlgorithmScenario.SEARCH_BEST_CASE -> createSearchBestCaseIntArray(size.toInt(), 6, 1000)
            AlgorithmScenario.RANDOM_CASE -> createRandomIntArray(size.toInt(), 6, 1000)
            AlgorithmScenario.SORT_WORST_CASE -> createSortWorstCaseIntArray(size.toInt())
            AlgorithmScenario.SORT_BEST_CASE -> createSortBestCaseIntArray(size.toInt())
        }
    }

    fun run(size: Long){
        refreshDataset(size)
        runTimeInNanos = measureNanoTime {
            algorithm()
        }
    }
}