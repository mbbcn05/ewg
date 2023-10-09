package babacan.Game

class GameHouse(val rectangle: MyRectangle) {

    private val sourceMap:HashMap<SourceType,GameSource> =HashMap()

    private  fun contains(source: GameSource)=sourceMap.get(source.type)!=null

    private fun accept(source: GameSource)=sourceMap.put(source.type,source)

    fun acceptIfNotContained(source: GameSource):Boolean{
        return contains(source)
            .apply{if(!this)accept(source)}
    }

}