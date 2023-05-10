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

fun createFrog() =
    Frog(Point(SCREEN_WIDTH/2,
        SCREEN_HEIGHT-GRID_SIZE),
        Direction.UP,
        FrogState.STAY,
        STATE_FRAMES)
fun Frog.move(to: Direction,cars: List<Car>):Frog =
    if (state == FrogState.STAY)
         if((position+to).isValid())
            face(to).copy(position = position + to,state = FrogState.MOVE,frames = STATE_FRAMES)
         else
             face(to)
    else copy(state = FrogState.STAY, frames = STATE_FRAMES).step(cars).move(to,cars)


fun Frog.face(to: Direction):Frog =
    if (dir==to) this else copy(dir= to)



fun Frog.step(cars: List<Car>): Frog {
    val detectCar = cars.any{position.row  in it.part.toRangeX() } //TODO:DETECT IF FROG POSITION IS IN CAR POSITION
    val detectRiver = position.row in GRID_SIZE*4 .. GRID_SIZE*7
    return if (frames > 0) {
        copy(frames = frames - 1)
    } else {
        when (state) {
            FrogState.STAY -> this
            FrogState.MOVE -> {
                if (detectCar) {
                    copy(state = FrogState.SMASH_1, frames = STATE_FRAMES)
                } else if (detectRiver) {
                    copy(state = FrogState.DROWN_1, frames = STATE_FRAMES)
                } else {
                    copy(state = FrogState.STAY, frames = STATE_FRAMES)
                }
            }
            FrogState.SMASH_1 -> copy(state = FrogState.SMASH_2, frames = STATE_FRAMES)
            FrogState.SMASH_2 -> copy(state = FrogState.SMASH_3, frames = STATE_FRAMES)
            FrogState.SMASH_3 -> copy(state = FrogState.DEAD, frames = STATE_FRAMES)
            FrogState.DROWN_1 -> copy(state = FrogState.DROWN_2, frames = STATE_FRAMES)
            FrogState.DROWN_2 -> copy(state = FrogState.DROWN_3, frames = STATE_FRAMES)
            FrogState.DROWN_3 -> copy(state = FrogState.DEAD, frames = STATE_FRAMES)
            FrogState.DEAD -> copy(state = FrogState.GONE)
            FrogState.GONE -> this
            FrogState.HOME -> this
        }
    }
}
