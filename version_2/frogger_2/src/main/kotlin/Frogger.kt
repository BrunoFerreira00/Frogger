
/**
 * Represents the state of the game.
 * @property frog the frog representation
 * @property cars the list of cars in the game
 */
data class Frogger(
    val frog: Frog,
    val cars: List<Car>
)
fun Frogger.step() = Frogger(frog/*.step()*/,cars = cars.map{it.step()})
