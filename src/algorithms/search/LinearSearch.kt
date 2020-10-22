package algorithms.search

import models.Algorithm
import models.AlgorithmScenario

class LinearSearch(valueToSearch : Int, scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        var res = -1

        for ((i, v) in dataset.withIndex()){
            if (v == valueToSearch){
                res = i
                break
            }
        }

        res
    }
}