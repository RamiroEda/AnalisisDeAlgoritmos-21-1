package algorithms.graphs

import models.Algorithm
import models.AlgorithmScenario

class FibonacciRecursive : Algorithm(AlgorithmScenario.SEARCH_BEST_CASE){
    override val algorithm = {
        fibonacci(this.dataset.size)
    }


    private fun fibonacci(position: Int) : Int{
        if(position < 2) return position
        return fibonacci(position - 1) + fibonacci(position - 2)
    }
}