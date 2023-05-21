import kotlin.math.absoluteValue


const val END_ROW = GRID_SIZE * 3
/**
 * The row where the homes are.
 */
const val HOME_ROW = END_ROW

/**
 * Represents a home where the frog must arrive to win the game.
 * @property x the x coordinate of the left side of the home
 * @property empty true if the home is empty (no frog)
 */
data class Home(val x: Int, val empty: Boolean = true)

/**
 * Create a list of all homes. All empty
 */
fun createHomes() = List(5) { Home(it * 3 * GRID_SIZE + GRID_SIZE / 2) }

/**
 * Returns true if the home is empty and the frog point is near.
 */
fun Home.canAccept(frog: Point) =
    empty && frog.y > (HOME_ROW-1)* GRID_SIZE && (frog.x - x).absoluteValue < GRID_SIZE/6

/**
 * Returns true if the home is full.
 */
fun Home.isFull() = !empty

/**
 * Returns a copy of the home with empty = false.
 */
fun Home.fill(): Home = copy(empty = false)