import pt.isel.canvas.*

const val REFRESH_TIME = 25 // 40 fps (1000/40 = 25 ms)
const val SCREEN_HEIGHT = 768
const val SCREEN_WIDTH = 672

/**
 * Dimensions of the grid
 */
const val GRID_SIZE = 48 // Side of the grid square
const val GRID_ROWS = 16  // Number of rows in the grid
const val GRID_COLS = 14  // Number of columns in the grid

/**
 * Information about the cars, vel= velocity, start= initial position
 */
const val CAR1X_VEL = 1
const val CAR4X_VEL = 2
const val CAR1_START_X = 624
const val CAR4_START_X = 0

/**
 * Represents the cars in canvas
 * @property car1X the car1 position(x) in the grid
 * @property car4X the car4 position(x) in the grid
 */

data class Frogger (
    val car1X: Int,
    val car4X: Int,
)

/**
 * Main function of the game.
 */

fun main() {
    onStart {
        val canvas = Canvas(SCREEN_WIDTH, SCREEN_HEIGHT, BLACK)
        var game = Frogger(car1X= CAR1_START_X, car4X= CAR4_START_X)
        canvas.onTimeProgress(REFRESH_TIME) {
            game = game.step()
            canvas.drawGame(game)
        }
        canvas.onKeyPressed { key ->
            when (key.char) {
                '1' -> game = game.copy(car1X= CAR1_START_X)
                '4' -> game = game.copy(car4X= CAR4_START_X)
                'q','Q' -> canvas.close()
            }
        }
    }
    onFinish { }
}

/**
 * Move the cars in the same direction
 * @receiver the positions of the cars in Frogger to be moved
 * @return the new position of the cars after the move
 */
fun Frogger.step(): Frogger{
    val x1 = car1X - CAR1X_VEL
    val x4 = car4X + CAR4X_VEL
    return Frogger(
        if(x1<-GRID_SIZE) CAR1_START_X else x1,
        if(x4>SCREEN_WIDTH) CAR4_START_X else x4)
}

