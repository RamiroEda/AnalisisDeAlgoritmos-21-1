package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class MergeSort(scenario: AlgorithmScenario) : Algorithm(scenario){
    override val algorithm = {
        this.sort(this.dataset)
    }

    private fun merge(arr: Array<Int>, l: Int, m: Int, r: Int) {
        val n1 = m - l + 1
        val n2 = r - m

        val L = IntArray(n1)
        val R = IntArray(n2)

        for (i in 0 until n1) L[i] = arr[l + i]
        for (j in 0 until n2) R[j] = arr[m + 1 + j]

        var i = 0
        var j = 0

        var k = l
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i]
                i++
            } else {
                arr[k] = R[j]
                j++
            }
            k++
        }

        while (i < n1) {
            arr[k] = L[i]
            i++
            k++
        }

        while (j < n2) {
            arr[k] = R[j]
            j++
            k++
        }
    }

    private fun sort(arr: Array<Int>, l: Int = 0, r: Int = arr.size-1) {
        if (l < r) {
            val m = (l + r) / 2

            sort(arr, l, m)
            sort(arr, m + 1, r)

            merge(arr, l, m, r)
        }
    }
}