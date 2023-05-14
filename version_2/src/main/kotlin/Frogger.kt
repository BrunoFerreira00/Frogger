
/**
 * Represents the state of the game.
 * @property frog the frog representation
 * @property cars the list of cars in the game
 */
data class Frogger(
    val frog: Frog,
    val cars: List<Car>
)
/**
 * Updating the moves of the frog
 * @receiver the information about the frog
 * @param to indicates the direction where the frog is going
 */
fun Frogger.moveFrog(to: Direction):Frogger = if (frog.frames == 0) copy(frog = frog.move(to,cars)) else this

/**
 * Updating both frog and cars
 * @receiver the information about the cars anda the frog
 */
fun Frogger.step() = copy(
    frog = frog.step(cars),
    cars = cars.map { it.step() }
)
