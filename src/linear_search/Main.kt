package linear_search

import utils.createRandomIntArray
import kotlin.system.measureNanoTime

fun main(){
    val array = createRandomIntArray(100000000, 6, 1000000)

    val time = measureNanoTime {
        val index = array.linearSearch(6)
    }

    println("Busqueda lineal: $time nanosegundos o ${time/1000000.0} segundos")
}