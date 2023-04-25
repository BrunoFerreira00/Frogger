data class Point(val col:Int,val row:Int)


enum class Direction(val dRow: Int = 0, val dCol: Int = 0) {
    UP(dRow= -1), LEFT(dCol = -1), DOWN(dRow = +1), RIGHT(dCol = +1)
}