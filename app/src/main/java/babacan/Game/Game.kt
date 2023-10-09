package babacan.Game

import android.util.Log

object Game {
    lateinit var looper: Looper
    lateinit var countDown: CountDown

    init {
        countDown = CountDown()
        looper = Looper()
        looper.addTask(countDown::updateTime)

    }

    var creathingPath: MyPath? = null

    val myPathList: MutableList<MyPath> = mutableListOf()
    var gameOver = false////OBSERVABLE YAP
    val houses: List<GameHouse> = listOf(
        GameHouse(MyRectangle(MyPoint(110f, 100f))),
        GameHouse(MyRectangle(MyPoint(620f, 100f))),
        GameHouse(MyRectangle(MyPoint(1190f, 100f)))
    )

    val sources: List<GameSource> = listOf(
        GameSource(MyRectangle(MyPoint(110f, 450f)), SourceType.ELECTRIC),
        GameSource(MyRectangle(MyPoint(620f, 450f)), SourceType.GAS),
        GameSource(MyRectangle(MyPoint(1190f, 450f)), SourceType.WATER)
    )

    fun start() {
        looper.run()
    }

    fun handleSourceSelecting(point: MyPoint) {
        if (creathingPath == null) {
            sources.forEach {
                if (it.shape.isPointInRectangle(point))
                    creathingPath = MyPath(it)


            }

        }
    }

        fun handleSourceMoving(pointA: MyPoint, pointB: MyPoint) {
            creathingPath?.let { it.lines.add(MyLine(pointA, pointB)) }
        }

        fun handleHouseSelecting(point: MyPoint) {

            houses.forEach {
                if (it.rectangle.isPointInRectangle(point) && it.contains(creathingPath!!.source)) {
                    creathingPath!!.apply {
                        clipLinesInRectangle(it.rectangle)
                        clipLinesInRectangle(this.source.shape)
                        GameMatrix.addPath(this)
                        if (GameMatrix.isIntersection) {
                            gameOver = true
                        } else {
                            myPathList.add(this)
                            creathingPath = null
                        }

                    }

                } else {
                    gameOver = true
                }
            }
        }
    }