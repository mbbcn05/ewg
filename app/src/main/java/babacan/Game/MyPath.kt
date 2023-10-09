package babacan.Game

import android.icu.lang.UCharacter.LineBreak


class MyPath (val source:GameSource){
    val lines= mutableListOf<MyLine>()
    fun clipLinesInRectangle(rectangle: MyRectangle){
        val removingLines= mutableListOf<MyLine>()
        lines.forEach{line->if(rectangle.isPointInRectangle(line.p1)&&rectangle.isPointInRectangle(line.p2)){
            removingLines.add(line)
        }
            lines.removeAll(removingLines)
        }
    }
}