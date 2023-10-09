package babacan.Game

class Looper {
    var maxFrameTime = 1000/60
    val taskList:MutableList<()->Unit> = mutableListOf()
   // val myBuffer = mutableListOf<Byte>()
    private var running =false
    private var frameStartTime:Double=0.0
    var frameTime: Double= 0.0
    private fun initialize(){
        if(running) return
        this.running=true
        while(running){
            frameStartTime= System.nanoTime().toDouble()
            taskList.forEach{it()}
            frameTime=System.nanoTime().toDouble()-frameStartTime/1000000
            if(frameTime<maxFrameTime){
                Thread.sleep((maxFrameTime-frameTime).toLong())

            }
        }
    }
    fun stop(){
        this.running = false
    }
    fun run() {
        if (running) return
        Thread { initialize() }.start()
    }
    fun addTask(task:()->Unit){
        taskList.add(task)
}}