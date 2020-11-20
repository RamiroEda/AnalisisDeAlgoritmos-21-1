package algorithms.graphs

import models.Algorithm
import models.AlgorithmScenario

class FibonacciDynamic : Algorithm(AlgorithmScenario.RANDOM_CASE) {
    override val algorithm = {
        fibonacci(this.dataset.size)
    }

    private fun fibonacci(n: Int): Int {
        val f = IntArray(n + 2)
        f[0] = 0
        f[1] = 1
        var i: Int = 2
        while (i <= n) {
            f[i] = f[i - 1] + f[i - 2]
            i++
        }
        return f[n]
    }
}