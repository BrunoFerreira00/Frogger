
/**
 * Represents the state of the game.
 * @property frog the frog representation
 * @property cars the list of cars in the game
 */
data class Frogger(
    val frog: Frog,
    val cars: List<Car>
)

fun Frogger.moveFrog(to: Direction):Frogger = if (frog.frames == 0) copy(frog = frog.move(to,cars))
        else this

fun Frogger.step() = copy(
    frog = frog.step(cars),
    cars = cars.map { it.step() }
)
