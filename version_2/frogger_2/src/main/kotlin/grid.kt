import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE


/**
 * Draws the grid in the canvas
 * @receiver the canvas to draw on
 */
fun Canvas.drawGrid() {
    for(x in 0..width step GRID_SIZE)
        drawLine(x,0,x,height, WHITE, 1)
    for(y in 0..height step GRID_SIZE)
        drawLine(0,y,width,y, WHITE, 1)
}