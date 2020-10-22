package gui

import javafx.application.Platform
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.runAsync

const val UPDATE_THRESHOLD = 200

class RunTimeChart {
    val chart = LineChart<Number, Number>(NumberAxis(), NumberAxis())
    private val chartItemsBuffer = ArrayList<Long>()


    init {
        chart.title = "Tiempo de ejecución"
        chart.createSymbols = false
        chart.animated = false
        chart.prefWidth = 800.0
        chart.prefHeight = 600.0
        Platform.runLater {
            chart.yAxis.label = "Tiempo"
            chart.xAxis.label = "Tamaño"
        }
        initDataset(0, 0)
    }

    private fun initDataset(runTime: Long, size: Long){
        chartItemsBuffer.add(runTime)
        if (size.rem(UPDATE_THRESHOLD) == 0L){
            if(chart.data.isNotEmpty()){
                val items = chartItemsBuffer.mapIndexed { i: Int, value: Long ->
                    XYChart.Data<Number, Number>(size+1-chartItemsBuffer.size+i, value)
                }
                Platform.runLater {
                    chart.data.last().data.addAll(items)
                }
                chartItemsBuffer.clear()
            }
        }
    }

    fun addRunTime(runTime : Long, size: Long){
        initDataset(runTime, size)
    }

    fun addSeries(name: String){
        val series = XYChart.Series<Number, Number>()
        series.name = name
        Platform.runLater {
            chart.data.add(series)
        }
    }
}