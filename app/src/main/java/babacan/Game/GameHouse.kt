package babacan.Game

class GameHouse(val rectangle: MyRectangle) {

    private val sourceMap:HashMap<SourceType,GameSource> =HashMap()
    fun contains(source: GameSource)=sourceMap.get(source.type)!=null
    fun accept(source: GameSource)=sourceMap.put(source.type,source)
    fun acceptIfNotContained(source: GameSource):Boolean{
        return contains(source)
            .apply{if(!this)accept(source)}
    }

}