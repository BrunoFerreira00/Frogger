import pt.isel.canvas.*
//import java.awt.Canvas


/**
 * The first row where the cars are.
 * The row is computed by adding the ordinal of the car type to this constant.
 * @see CarType.ordinal
 */
const val START_CAR_ROW = 9

/**
 * Represents the type of car in the game.
 * @property speed the speed of the car
 * @property size the number of cells occupied by the car
 * @property ordinal the position of the car in the enum class and the row where it is created
 */
enum class CarType(val speed: Int, val size: Int = 1) {
    TRUCK(-3,2), SPEED1(+3), CAR(-3), BULLDOZER(+1), SPEED2(-1)
}

/**
 * Represents a car in the game.
 * @property type the type of the car
 * @property part the movable object that represents the car
 */
data class Car(val type: CarType, val part: Movable)

/**
 * Creates a car of the given [type] in the given [column] of the grid.
 * @return the created car.
 */
private fun car(column: Int, type: CarType) =
    Car(type, Movable(column * GRID_SIZE, START_CAR_ROW+type.ordinal, type.size, type.speed))

/**
 * Creates a list of all cars in a game.
 */
fun createCars() = listOf(
    car(0, CarType.TRUCK), car(6, CarType.TRUCK),
    car(0, CarType.SPEED1),
    car(1, CarType.CAR), car(5, CarType.CAR), car(9, CarType.CAR),
    car(2, CarType.BULLDOZER), car(7, CarType.BULLDOZER), car(12, CarType.BULLDOZER),
    car(0, CarType.SPEED2), car(7, CarType.SPEED2), car(10, CarType.SPEED2)
)

fun Car.step() = copy(part = part.move())


fun getCars(c: Canvas, cars: List<Car>) {
    cars.forEach { car ->
        val newCars = car.step()
        drawCars(c, newCars.part.x, newCars.part.row * GRID_SIZE, newCars)
    }
}

