
/**
 * Represents the state of the game.
 * @property frog the frog representation
 * @property cars the list of cars in the game
 */
data class Frogger(
    val frog: Frog,
    val cars: List<Car>
)
fun Frogger.moveFrog(dir: Direction):Frogger {
    val newPos = frog.position + dir
    return if (newPos.isValid()) this.copy(
        frog = Frog(position = newPos, dir = dir, state = FrogState.MOVE),
        cars = cars
    ) else this

}
fun Frogger.step() = copy(
    frog = frog,
    cars = cars.map { it.step() }
)
