package models

class AlgorithmPair(private val name: String, val handler: (Long, AlgorithmScenario) -> Long){
    override fun toString(): String {
        return name
    }
}