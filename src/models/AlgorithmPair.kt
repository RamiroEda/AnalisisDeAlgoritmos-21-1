package models

class AlgorithmPair(private val first: String, val second: (Long) -> Long){
    override fun toString(): String {
        return first
    }
}