package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class BubbleSort(scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        bubbleSort(this.dataset)
    }

    private fun bubbleSort(arr: Array<Int>) {
        val n = arr.size
        for (i in 0 until n - 1) for (j in 0 until n - i - 1) if (arr[j] > arr[j + 1]) {
            val temp = arr[j]
            arr[j] = arr[j + 1]
            arr[j + 1] = temp
        }
    }
}