package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class QuickSort(scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        this.sort(this.dataset)
    }

    private fun partition(arr: Array<Int>, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1
        for (j in low until high) {
            if (arr[j] < pivot) {
                i++
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }

        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp
        return i + 1
    }

    private fun sort(arr: Array<Int>, low: Int = 0, high: Int = arr.size-1) {
        if (low < high) {
            val pi = partition(arr, low, high)
            sort(arr, low, pi - 1)
            sort(arr, pi + 1, high)
        }
    }
}