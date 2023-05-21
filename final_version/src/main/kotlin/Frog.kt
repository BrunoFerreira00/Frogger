import javax.swing.text.Position

/**
 * Represents the state of a frog.
 * STAY, GONE and HOME are stable states, the others are intermediate states.
 * When frog is moved: STAY => MOVE->(STAY|HOME).
 * When frog is smashed: (STAY||MOVE) => SMASH_1->SMASH_2->SMASH_3->DEAD->GONE.
 * When frog is drowned: (STAY||MOVE) => DROWN_1->DROWN_2->DROWN_3->DEAD->GONE.
 */
enum class FrogState {
    STAY,   // Frog is not moving (in a cell)
    MOVE,   // Frog is moving between cells
    SMASH_1, SMASH_2, SMASH_3,  // Intermediate states when frog is smashed
    DROWN_1, DROWN_2, DROWN_3,  // Intermediate states when frog is drowned
    DEAD,   // Frog is dead
    GONE,   // Frog is hidden
    HOME    // Frog is in a home
    ,
}

/**
 * Number of frames to change for next state if current state is intermediate.
 */
private const val STATE_FRAMES = 5

/**
 * The information of the frog.
 * @property position the position (x and y) of the frog
 * @property dir the direction where the frog is facing (UP, LEFT, DOWN, RIGHT)
 * @property state the state of the frog
 * @property frames the number of frames left to change state
 */


data class Frog(
    val position: Point,
    val dir: Direction,
    val state: FrogState,
    val frames: Int = 0,
)

/**
 * Creates the frog on certain condition
 */
fun createFrog() =
    Frog(Point(SCREEN_WIDTH/2,
        SCREEN_HEIGHT-GRID_SIZE),
        Direction.UP,
        FrogState.STAY,
        STATE_FRAMES)

/**
 * Indicates the next move of the frog and if it is valid
 * @receiver the information about the Frog
 * @param to indicates  the direction the frog is going to
 * @param cars indicates the list of the cars
 */
fun Frog.move(to: Direction,cars: List<Car>):Frog =
    if (state == FrogState.GONE) this
        else if (state == FrogState.STAY )
            if((position+to).isValid())
                face(to).copy(position = position + to,state = FrogState.MOVE,frames = STATE_FRAMES)
            else
                face(to)
        else copy(state = FrogState.STAY, frames = STATE_FRAMES).step(cars).move(to,cars)

/**
 * Indicates the new direction the draw of the frog is going
 * @receiver the information about the Frog
 * @param to indicates  the direction the frog is going to
 */
fun Frog.face(to: Direction):Frog =
    if (dir==to) this else copy(dir=to)

/**
 * Indicates if the position of the frog is the same of any car
 * @receiver the information about the Frog
 * @param cars the list of cars
 */
fun Frog.detectCar(cars: List<Car>):Boolean =
    cars.any { position.y == it.part.row*GRID_SIZE && position.x+GRID_SIZE/2 in it.part.toRangeX() }

/**
 * Indicates if the position of the frog is the same of the river
 * @receiver the information about the Frog
 */
fun Frog.detectRiver():Boolean= position.y in GRID_SIZE*3 .. GRID_SIZE*7

/**
 * Updating the frog state
 * @receiver the information about the Frog
 * @param cars the list of all cars
 */
fun Frog.step(cars: List<Car>): Frog {
    return if (frames > 0) {
        copy(frames = frames - 1)
    } else {
        when (state) {
            FrogState.MOVE -> {
                if (detectCar(cars)) {
                    copy(state = FrogState.SMASH_1, frames = STATE_FRAMES)
                } else if (detectRiver()) {
                    copy(state = FrogState.DROWN_1, frames = STATE_FRAMES)
                } else {
                    copy(state = FrogState.STAY, frames = STATE_FRAMES)
                }
            }
            FrogState.SMASH_1, FrogState.SMASH_2, FrogState.DROWN_1, FrogState.DROWN_2 ->
                copy(frames = STATE_FRAMES, state= FrogState.values()[state.ordinal+1])
            FrogState.SMASH_3, FrogState.DROWN_3 -> copy( state = FrogState.DEAD, frames = STATE_FRAMES)
            FrogState.DEAD -> copy(state = FrogState.GONE)
            else -> this
        }
    }
}
