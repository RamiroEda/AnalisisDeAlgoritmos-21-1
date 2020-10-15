package gui

import algorithms.LinearSearch
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import models.AlgorithmPair
import tornadofx.*
import utils.createRandomIntArray
import utils.millisToTime

const val LIMITE = 2500000

val ALGORIHM_LIST = listOf(
        AlgorithmPair("Linear Search"){ size ->
            val arreglo = createRandomIntArray(size.toInt(), 6, 1000)

            val search = LinearSearch(arreglo, 6)
            search.run()
            search.runInNanos
        }
)

class MainGUI : App(MainView::class)

class MainView : View() {
    override val root = vbox{
        prefWidth = 200.0
        prefHeight = 150.0
        padding = Insets(8.0)
        alignment = Pos.CENTER
        label("Selecciona un algoritmo"){
            font = Font.font(16.0)
            vboxConstraints {
                marginBottom = 16.0
            }
        }
        val algoCombo = combobox(
            values = ALGORIHM_LIST
        ){
            value = ALGORIHM_LIST.first()

            vboxConstraints {
                marginBottom = 16.0
            }
        }
        button("Calcular") {
            setOnAction {
                find<RunTimeChartFragment>(AlgorithmScope(algoCombo.value)).openWindow(
                        owner = null,
                        resizable = false
                )
            }
        }
    }
}

class RunTimeChartFragment : Fragment(){
    private val chart : RunTimeChart = RunTimeChart()
    private val startTime = System.currentTimeMillis()
    override val scope = super.scope as AlgorithmScope

    override val root = vbox{
        alignment = Pos.CENTER_RIGHT
        title = "Tiempo de ejecución"
        val timeLabel = label("Tiempo: 00:00:00") {
            font = Font.font(18.0)
            vboxConstraints {
                marginRight = 8.0
            }
        }
        this.add(chart.chart)
        tornadofx.runAsync {
            while (true){
                Thread.sleep(500)
                Platform.runLater {
                    timeLabel.text = "Tiempo: ${millisToTime(System.currentTimeMillis() - startTime)}"
                }
            }
        }
        tornadofx.runAsync {
            for (i in 1..LIMITE){
                chart.addRunTime(scope.algorithmPair.second(i.toLong()), i)
            }
        }
    }
}

class AlgorithmScope(
        val algorithmPair: AlgorithmPair
) : Scope()

fun main(){
    launch<MainGUI>()
}