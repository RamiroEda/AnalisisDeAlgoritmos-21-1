package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class InsertSort(scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        this.sort(this.dataset)
    }

    private fun sort(arr: Array<Int>) {
        val n = arr.size
        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
        }
    }
}