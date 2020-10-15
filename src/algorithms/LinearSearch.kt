package algorithms

import models.Algorithm

class LinearSearch(array : Array<Int>, valueToSearch : Int) : Algorithm() {
    override val algorithm = {
        var res = -1

        for ((i, v) in array.withIndex()){
            if (v == valueToSearch) res = i
        }

        res
    }
}

fun main(){
    val search = LinearSearch(arrayOf(1,2,3), 3)
    search.run()
    print(search.runInNanos)
}