package models

import kotlin.system.measureNanoTime

abstract class Algorithm{
    abstract val algorithm : () -> Any
    var timeStartInMillis = -1L
    var timeEndInMillis = -1L
    var runInNanos = -1L

    fun run(){
        timeStartInMillis = System.currentTimeMillis()
        runInNanos = measureNanoTime {
            algorithm()
        }
        timeEndInMillis = System.currentTimeMillis()
    }
}