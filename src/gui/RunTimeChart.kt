package gui

import javafx.application.Platform
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import utils.mean

const val UPDATE_THRESHOLD = 100

class RunTimeChart {
    val chart = LineChart<Number, Number>(NumberAxis(), NumberAxis())
    private val timeSeries = XYChart.Series<Number, Number>()
    private val meanSeries = XYChart.Series<Number, Number>()
    private val lastItems = ArrayList<Long>()

    init {
        chart.title = "Tiempo de ejecución"
        chart.createSymbols = false
        chart.animated = false
        chart.prefWidth = 800.0
        chart.prefHeight = 600.0
        timeSeries.name = "Tiempo de ejecucion"
        meanSeries.name = "Promedio"
        Platform.runLater {
            chart.data.add(timeSeries)
            chart.data.add(meanSeries)
            chart.yAxis.label = "Tiempo"
            chart.xAxis.label = "Tamaño"
            meanSeries.node.lookup(".chart-series-line").style = "-fx-stroke: #03a9f4;"
        }
        initDataset(0, 0)
    }

    private fun initDataset(runTime: Long, size: Int){
        if (lastItems.size > 1000){
            lastItems.removeAt(0)
        }
        lastItems.add(runTime)
        val mean = lastItems.mean()
        Platform.runLater {
            if (size % UPDATE_THRESHOLD == 0){
                timeSeries.data.add(XYChart.Data<Number, Number>(size, runTime))
                meanSeries.data.add(XYChart.Data<Number, Number>(size, mean))
            }
        }

    }

    fun addRunTime(runTime : Long, size: Int){
        initDataset(runTime, size)
    }
}