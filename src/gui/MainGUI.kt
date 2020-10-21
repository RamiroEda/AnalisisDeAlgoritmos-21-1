package gui

import algorithms.search.LinearSearch
import algorithms.sort.BubbleSort
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.stage.WindowEvent
import models.AlgorithmPair
import models.AlgorithmScenario
import tornadofx.*
import utils.millisToTime

val ALGORIHM_LIST = listOf(
        AlgorithmPair("Busqueda lineal"){ size, scenario ->
            val search = LinearSearch(6, scenario)
            search.run(size)
            search.runTimeInNanos
        },
        AlgorithmPair("Ordenamiento Burbuja"){ size, scenario ->
            val sort = BubbleSort(scenario)
            sort.run(size)
            sort.runTimeInNanos
        }
)

class MainGUI : App(MainView::class)

class MainView : View() {
    private val algorithmQueue = FXCollections.observableArrayList<AlgorithmScope>()

    override val root = vbox{
        prefWidth = 400.0
        prefHeight = 400.0
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
        val scenarioCombo = combobox(
                values = AlgorithmScenario.values().toList()
        ){
            value = AlgorithmScenario.MEDIUM_CASE

            vboxConstraints {
                marginBottom = 16.0
            }
        }
        val timeLimitInput = textfield("0") {
            promptText = "Limite de ejecución (segundos)"
            vboxConstraints {
                marginBottom = 16.0
            }
        }
        val sizeLimitInput = textfield("500000") {
            promptText = "Limite de tamaño de muestra"
            vboxConstraints {
                marginBottom = 16.0
            }
        }
        button("Poner en cola") {
            setOnAction {
                if(timeLimitInput.text.toLongOrNull() != null && sizeLimitInput.text.toLongOrNull() != null){
                    if(sizeLimitInput.text.toLong() > 0){
                        val scope = AlgorithmScope(
                            algoCombo.value,
                            scenarioCombo.value,
                            timeLimitInput.text.toLong(),
                            sizeLimitInput.text.toLong()
                        ){
                            Platform.runLater {
                                if(algorithmQueue.isNotEmpty()){
                                    algorithmQueue.removeAt(0)
                                }

                                if(algorithmQueue.isNotEmpty()){
                                    if (!isAlgorithmRunning) {
                                        val fragment = find<RunTimeChartFragment>(algorithmQueue.first())
                                        fragment.openWindow(
                                                owner = null,
                                                resizable = false
                                        )?.onCloseRequest = EventHandler<WindowEvent> {
                                            fragment.keepRunning = false
                                        }
                                    }
                                }
                            }
                        }
                        if(algorithmQueue.isEmpty()){
                            val fragment = find<RunTimeChartFragment>(scope)
                            fragment.openWindow(
                                    owner = null,
                                    resizable = false
                            )?.onCloseRequest = EventHandler<WindowEvent> {
                                fragment.keepRunning = false
                            }
                        }
                        algorithmQueue.add(scope)
                    }
                }


            }
        }
        label("Algoritmos en cola") {
            vboxConstraints {
                marginTop = 16.0
            }
        }
        listview(
            values = algorithmQueue
        ) {
            prefHeight = 100.0
        }
    }
}


var isAlgorithmRunning = false

class RunTimeChartFragment : Fragment(){
    private val chart : RunTimeChart = RunTimeChart()
    private val startTime = System.currentTimeMillis()
    override val scope = super.scope as AlgorithmScope

    var keepRunning = true
        set(value) {
            field = value
            if(!value){
                isAlgorithmRunning = false
                scope.onCalcEnd()
            }
        }

    init {
        isAlgorithmRunning = true
    }

    override val root = vbox{
        alignment = Pos.CENTER_RIGHT
        title = "Tiempo de ejecución"
        val timeLabel = label("Tiempo: 00:00:00/∞") {
            font = Font.font(18.0)
            vboxConstraints {
                marginRight = 8.0
            }
        }
        this.add(chart.chart)
        tornadofx.runAsync {
            while (keepRunning){
                Thread.sleep(500)
                if(scope.runTimeLimit > 0){
                    if (System.currentTimeMillis() - startTime >= scope.runTimeLimit*1000){
                        if(keepRunning) keepRunning = false
                    }
                }
                Platform.runLater {
                    timeLabel.text = "Tiempo: ${millisToTime(System.currentTimeMillis() - startTime)}/${if (scope.runTimeLimit == 0L){
                        "∞"
                    }else{
                        millisToTime(scope.runTimeLimit*1000)
                    }}"
                }
            }
        }
        tornadofx.runAsync {
            for (i in 1..scope.sizeLimit){
                chart.addRunTime(scope.algorithmPair.handler(i, scope.scenario), i)
                if (!keepRunning){
                    break
                }
            }

            if(keepRunning){
                keepRunning = false
            }
        }
    }
}

class AlgorithmScope(
        val algorithmPair: AlgorithmPair,
        val scenario: AlgorithmScenario,
        val runTimeLimit: Long,
        val sizeLimit: Long,
        val onCalcEnd: () -> Unit = {}
) : Scope(){
    override fun toString(): String {
        return "$algorithmPair $scenario: sizeLimit=$sizeLimit timeLimit=$runTimeLimit"
    }
}

fun main(){
    launch<MainGUI>()
}