
/**
 * Represents the state of the game.
 * @property frog the frog representation
 * @property cars the list of cars in the game
 */
data class Frogger(
    val frog: Frog,
    val cars: List<Car>
)

fun Frogger.moveFrog(to: Direction):Frogger = if (frog.frames == 0) copy(frog = frog.move(to)) else death()
//TODO: é preciso incluir a death aqui para que se após dar um passo morre

fun Frogger.detectCar(car:List<Car>):Boolean =
    car.any { it.part.toRangeX().contains(frog.position.row) }

fun Frogger.detectRiver():Boolean = frog.position.row in GRID_SIZE*4 .. GRID_SIZE*8

fun Frogger.death():Frogger{
   return if (detectCar(cars)) copy(frog= frog.copy(state = FrogState.SMASH_1))
        else if (detectRiver()) copy(frog= frog.copy(state = FrogState.DROWN_1))
            else this
}


fun Frogger.step() = copy(
    frog = frog.step(),
    cars = cars.map { it.step() }
)
