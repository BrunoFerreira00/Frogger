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
 */

fun Frog.move(to: Direction):Frog =
    if (state >= FrogState.SMASH_1 && state <= FrogState.HOME) this
        else if (state == FrogState.STAY )
            if((position+to).isValid())
                face(to).copy(position = position + to,state = FrogState.MOVE,frames = STATE_FRAMES)
               else face(to)
        else this

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

fun Frog.detectTurtle(turtles: List<Turtle>):Boolean =
    turtles.any { position.y == it.part.row*GRID_SIZE && position.x+GRID_SIZE/2 in it.part.toRangeX()
            && it.state() != TurtleState.UNDER_WATER}

fun Frog.detectLog(logs: List<Log>):Boolean =
    logs.any { position.y == it.part.row*GRID_SIZE && position.x+GRID_SIZE/2 in it.part.toRangeX() }

fun Frog.detectHome(homes: List<Home>):Boolean =
    homes.any {
        position.x in it.x - GRID_SIZE / 2..it.x + GRID_SIZE / 2 && position.y == HOME_ROW
    }
fun Frog.detectRiver(turtles: List<Turtle>,logs: List<Log>,homes:List<Home>):Boolean=
    position.y in GRID_SIZE*2 .. GRID_SIZE*7
    && !detectTurtle(turtles) && !detectLog(logs) && !detectHome(homes)


fun Frog.logSpeed(logs:List<Log>): Int {
    logs.forEach {
        if (position.y == it.part.row*GRID_SIZE)
            return it.part.speed
    }
    return 0
}

fun Frog.turtleSpeed(turtles: List<Turtle>):Int{
    turtles.forEach {
        if (position.y == it.part.row*GRID_SIZE)
            return it.part.speed
    }
    return 0
}
fun Frog.checkState(state:FrogState, cars:List<Car>, turtles:List<Turtle>, logs:List<Log>, homes:List<Home>):Frog =
    when {
        detectCar(cars) -> copy(state = FrogState.SMASH_1, frames = STATE_FRAMES)
        detectRiver(turtles, logs, homes) -> copy(state = FrogState.DROWN_1, frames = STATE_FRAMES)
        detectHome(homes) -> copy(state = FrogState.HOME, frames = STATE_FRAMES)
        detectLog(logs) -> copy(position = Point(position.x + logSpeed(logs), position.y), state = state)
        detectTurtle(turtles) -> copy(position = Point(position.x + turtleSpeed(turtles), position.y), state = state)
        else -> copy(state = state)
    }





/**
 * Indicates if the position of the frog is the same of the river
 * @receiver the information about the Frog
 */

/**
 * Updating the frog state
 * @receiver the information about the Frog
 * @param cars the list of all cars
 */
fun Frog.step(cars: List<Car>,turtles: List<Turtle>,logs: List<Log>,homes: List<Home>): Frog {
    return if (frames > 0) {
        copy(frames = frames - 1)
    } else {
        when (state) {
            FrogState.STAY ->
                checkState(FrogState.STAY,cars,turtles,logs,homes)
            FrogState.MOVE ->
                checkState(FrogState.STAY,cars,turtles,logs,homes)
            FrogState.SMASH_1, FrogState.SMASH_2, FrogState.DROWN_1, FrogState.DROWN_2 ->
                copy(state= FrogState.values()[state.ordinal+1], frames = STATE_FRAMES)
            FrogState.SMASH_3, FrogState.DROWN_3 ->
                copy(state = FrogState.DEAD, frames = STATE_FRAMES)
            FrogState.DEAD ->
                copy(state = FrogState.GONE)
            else -> this
        }
    }
}
