package utils

import kotlin.math.absoluteValue
import kotlin.random.Random

fun createRandomIntArray(size: Int, valueToSearch: Int, upperBound: Int) : Array<Int> {
    if(valueToSearch >= upperBound) throw Exception("El valor a buscar debe ser menor al limite superior")
    val valueToSearchIndex = Random.nextInt().rem(size).absoluteValue
    return Array(size){
        if(it == valueToSearchIndex){
            valueToSearch
        }else{
            Random.nextInt().rem(upperBound).absoluteValue
        }
    }
}