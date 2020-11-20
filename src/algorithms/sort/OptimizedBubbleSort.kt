package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class OptimizedBubbleSort(scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        bubbleSort(this.dataset)
    }

    private fun bubbleSort(arr: Array<Int>, n: Int = arr.size) {
        var j: Int
        var temp: Int
        var swapped: Boolean
        var i: Int = 0
        while (i < n - 1) {
            swapped = false
            j = 0
            while (j < n - i - 1) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                    swapped = true
                }
                j++
            }

            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false) break
            i++
        }
    }
}