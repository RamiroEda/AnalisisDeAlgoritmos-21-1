package algorithms.graphs

import models.Algorithm
import models.AlgorithmScenario

class FibonacciIterative : Algorithm(AlgorithmScenario.SEARCH_BEST_CASE){
    override val algorithm = {
        var actual = 0
        var siguiente = 1

        for (i in this.dataset.indices){
            val aux = siguiente
            siguiente = actual + aux
            actual = aux
        }

        actual
    }
}