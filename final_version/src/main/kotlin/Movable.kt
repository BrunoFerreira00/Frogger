
/**
 * Represents a movable object (Cars or Logs) in a row of the grid game.
 * For each move [x] is incremented by [speed].
 * @property x the x coordinate of the left side of object
 * @property row the line where the object moves (y = [row] * GRID_SIZE)
 * @property size the number of grid cells occupied by the object
 * @property speed the speed of the object (negative means right to left)
 */
data class Movable(val x: Int, val row: Int, val size: Int, val speed: Int)

/**
 * Returns the range of x coordinates occupied by the movable object.
 */
fun Movable.toRangeX() = x .. (x + GRID_SIZE * size)

/**
 * Move the movable object the number of points corresponding to its speed.
 * If the object is out of the screen, it is moved to the other side.
 */
fun Movable.move(): Movable = when {
    speed < 0 && x < -size * GRID_SIZE -> copy(x = SCREEN_WIDTH)
    speed > 0 && x > SCREEN_WIDTH -> copy(x = -size * GRID_SIZE)
    else -> copy(x = x + speed)
}