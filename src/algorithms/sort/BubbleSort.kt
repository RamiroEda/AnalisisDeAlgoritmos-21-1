package algorithms.sort

import models.Algorithm
import models.AlgorithmScenario

class BubbleSort (scenario: AlgorithmScenario) : Algorithm(scenario) {
    override val algorithm = {
        var aux : Int
        for (i in 1 until dataset.size){
            for (e in 0 until dataset.size-i){
                if(dataset[e] > dataset[e+1]){
                    aux = dataset[e]
                    dataset[e] = dataset[e+1]
                    dataset[e+1] = aux
                }
            }
        }
    }
}