package algorithms.dynamic.dynamic_backpack

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import models.Algorithm
import models.AlgorithmScenario
import java.io.File

data class Item(val weight : Int, val value : Int)

class DynamicBackpack(private val items : List<Item>, maxWeight: Int) : Algorithm(AlgorithmScenario.NO_DATA){
    private val used = ArrayList<Int>()

    override val algorithm = {
        maxForMin(maxWeight)
    }

    fun getMaxValue() = used.sumBy {
        items[it].value
    }

    private fun maxForMin(currentMaxWeight: Int){
        var currentMaxValue = Item(
            weight = Int.MAX_VALUE,
            value = Int.MIN_VALUE
        )
        var currentMaxIndex = -1

        for((i, item) in items.withIndex()){
            if(item.weight <= currentMaxWeight && !used.contains(i) && currentMaxValue.value < item.value){
                currentMaxValue = item
                currentMaxIndex = i
            }
        }

        if (currentMaxIndex > -1){
            used.add(currentMaxIndex)
            return maxForMin(currentMaxWeight - currentMaxValue.weight)
        }
    }
}

fun main(){
    val items : List<Item> = csvReader().readAll(
            File("D:\\Github\\AnalisisDeAlgoritmos-21-1\\src\\algorithms\\dynamic\\dynamic_backpack\\res\\1000_1000-10000.csv")
    ).map {
        Item(it[0].toInt(), it[1].toInt())
    }
    val resultados = ArrayList<Int>()
    for(i in items.indices){
        val backpack = DynamicBackpack(items, i)
        backpack.run(0)
        resultados.add(backpack.getMaxValue())
    }
    println(resultados.subList(0,50))
}