
/**
 * Represents the position of the game.
 * @property col the col of the canvas
 * @property rows the rows of the canvas
 */
data class Point(val x:Int,val y:Int)

/**
 * Represents the direction of the game.
 * @property dcol the direction between the cols of the canvas
 * @property drows the direction between the rows of the canvas
 */
enum class Direction(val dRow: Int = 0, val dCol: Int = 0) {
    UP(dRow= -1), LEFT(dCol = -1), DOWN(dRow = +1), RIGHT(dCol = +1)
}

/**
 * Indicates if the position is valid
 * @receiver the information about the positions
 */
fun Point.isValid() =
    (y in 0..SCREEN_HEIGHT-GRID_SIZE && x in 0..SCREEN_WIDTH-GRID_SIZE)

/**
 * Updating the direction where it is going
 * @receiver the information about the positions
 * @param dir the direction where it is going
 */
operator fun Point.plus(dir: Direction) =
    Point(x + dir.dCol*GRID_SIZE, y + dir.dRow*GRID_SIZE)