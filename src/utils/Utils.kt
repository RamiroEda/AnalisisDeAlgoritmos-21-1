package utils

import kotlin.Error
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

fun createWorstCaseIntArray(size: Int, valueToSearch: Int, upperBound: Int) : Array<Int> {
    if(valueToSearch >= upperBound) throw Exception("El valor a buscar debe ser menor al limite superior")
    return Array(size){
        if(it == size-1){
            valueToSearch
        }else{
            Random.nextInt().rem(upperBound).absoluteValue
        }
    }
}

fun createBestCaseIntArray(size: Int, valueToSearch: Int, upperBound: Int) : Array<Int> {
    if(valueToSearch >= upperBound) throw Exception("El valor a buscar debe ser menor al limite superior")
    return Array(size){
        if(it == 0){
            valueToSearch
        }else{
            Random.nextInt().rem(upperBound).absoluteValue
        }
    }
}


fun millisToTime(millis: Long) : String {
    val seconds = millis.div(1000).toInt().rem(60)
    val minutes = millis.div(1000).div(60).toInt().rem(60)
    val hours = millis.div(1000).div(60).div(60).toInt()

    return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}


fun <T>ArrayList<T>.mean() : Double{
    if (this.size > 0){
        if (this.first() !is Number) throw Error("El tipo de dato no es numero")
    }

    var total = 0.0

    for (num in this){
        total += (num as Number).toDouble()
    }

    return total.div(this.size)
}