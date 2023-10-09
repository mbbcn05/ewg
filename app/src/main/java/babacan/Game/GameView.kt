package babacan.Game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.view.GestureDetectorCompat

class GameView(context:Context):SurfaceView(context),SurfaceHolder.Callback {
    private val mPaint:Paint= Paint()
    //private val gestures=GestureDetectorCompat(context,GestureListener())
    private lateinit var canvas:Canvas
    lateinit var houseBitmap:Bitmap
    lateinit var sourceBitmap: Bitmap
    lateinit var scaledHouse:Bitmap
    lateinit var scaledSource:Bitmap


    init{

        //mPaint.setAntiAlias(true)
       // mPaint.setDither(true)
        //  mPaint.setColor(0xff000000);
        //  mPaint.setColor(0xff000000);
       // mPaint.setStyle(Paint.Style.STROKE)
        //mPaint.setStrokeJoin(Paint.Join.ROUND)
       // mPaint.setStrokeCap(Paint.Cap.ROUND)
       // mPaint.setStrokeWidth(1f)

        houseBitmap=BitmapFactory.decodeResource(resources,R.drawable.house)
        sourceBitmap=BitmapFactory.decodeResource(resources,R.drawable.source)
        scaledHouse=Bitmap.createScaledBitmap(houseBitmap,150,150,true)
        scaledSource=Bitmap.createScaledBitmap(sourceBitmap,150,150,true)
        houseBitmap.recycle()
        sourceBitmap.recycle()


        Game.looper.addTask(::drawCanvas)
        Game.start()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //var retval=false
      //  retval=gestures.onTouchEvent(event)
       // return retval
        when(event.action){

            MotionEvent.ACTION_DOWN->{

                if(Game.creathingPath==null) Game.handleSourceSelecting(MyPoint(event.x,event.y))
            }
            MotionEvent.ACTION_MOVE->{
                Game.creathingPath?.let { Game.handleSourceMoving(event.x,event.y)

            }


            }
            MotionEvent.ACTION_UP->{

                Game.creathingPath?.let {
                    Log.i("kaldırma","kaldırma başarılı")
                    Game.handleHouseSelecting(MyPoint(event.x,event.y))}

            }

      }
        return true
    }
    private fun drawCanvas(){
        if(holder.surface.isValid){
            canvas=holder.lockCanvas()
            canvas.drawRGB(255,255,255)


            drawHouses()
           drawSources()


            drawPaths()
           drawCountDown()
            Game.countDown.updateTime()
            holder.unlockCanvasAndPost(canvas)
        }

    }

    private fun drawPaths() {
        Game.myPathList.forEach{path->path.lines.forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}
        Game.creathingPath?.let{it.lines.forEach{line->canvas.drawLine(line.p1.x,line.p1.y,line.p2.x,line.p2.y,mPaint)}}
    }

    private fun drawSources() {
        Game.sources.forEach{canvas.drawBitmap(scaledSource,it.shape.p1.x,it.shape.p1.y,mPaint)}

    }

    private fun drawHouses() {
        Game.houses.forEach{canvas.drawBitmap(scaledHouse,it.rectangle.p1.x,it.rectangle.p1.y,mPaint)}
    }


    private fun drawCountDown() {canvas.drawText(Game.countDown.second.toString(),30f,50f,mPaint)
    }
    private inner class  GestureListener:GestureDetector.SimpleOnGestureListener(){


        override fun onDown(e: MotionEvent): Boolean {

           if(Game.creathingPath==null) Game.handleSourceSelecting(MyPoint(e.x,e.y))
            return true
        }


        override fun onSingleTapUp(e: MotionEvent): Boolean {//HATA BU ANİ ÇEKME

            Game.creathingPath?.let {  Game.handleHouseSelecting(MyPoint(e.x,e.y))}

            return true
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {

            Log.i("srolling..","${e1.x} - ${e1.y}      ${e2.x}-${e2.y}")
         //   Game.creathingPath?.let { Game.handleSourceMoving(MyPoint(e1.x,e1.y),MyPoint(e2.x,e2.y)) }
            return true
        }

    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }
}
