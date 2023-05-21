
/**
 * Represents a log floating in the river.
 * @property part the movable object that represents the log
 */
data class Log(val part: Movable)

/**
 * Creates a log in the given [row] and [column] with the given [size] and [speed].
 */
fun Log(row: Int, column: Int, size: Int, speed: Int) =
    Log(Movable(column * GRID_SIZE, row, size, speed))

/**
 * Creates all logs in the river game.
 */
fun createLogs() = listOf(
    Log(3, 2, 4, +2), Log(3, 8, 4, +2), Log(3, 14, 4, +2),
    Log(5,-2, 6, +4), Log(5, 6, 6, +4),
    Log(6, 0, 3, +1), Log(6, 6, 3, +1), Log(6, 12, 3, +1)
)

/**
 * Returns the log after moving it, for each frame.
 * The log is moved by moving its movable part.
 */
fun Log.move() = Log(part= part.move())