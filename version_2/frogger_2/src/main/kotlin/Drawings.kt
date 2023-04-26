import pt.isel.canvas.Canvas

/**
 * Dimensions of the home
 */
const val HOME_HEIGHT = 72
const val HOME_WIDTH = 96
const val HOME_INSIDE = 24

/**
 * Dimensions of all the parts of the home
 */
const val HOME_END = GRID_SIZE*3


const val UPPERWALK_ROW = GRID_SIZE*8
const val DOWNWALK_ROW = GRID_SIZE*14


/**
 * Draws the two purple walks on different rows of the canvas.
 * @param c the canvas to draw on
 * @param x the position of the col where the walks are
 * @param y the position of the rows where the  sidewalks are
 */
fun drawSideWalk(c: Canvas, x: Int, y: Int) {
    c.drawImage("frogger|135,196,16,16",x,y,GRID_SIZE,GRID_SIZE)
}
/**
 * Draws the purple walks on all the row of the canvas.
 * @param c the canvas to draw on
 * @param y1 row of the first sidewalk
 * @param y2 row of the second sidewalk
 */
fun setSidewalks(c: Canvas, y1: Int, y2: Int) {
    for (x in 0..SCREEN_WIDTH step GRID_SIZE) {
        drawSideWalk(c, x, y1)
        drawSideWalk(c, x, y2)
    }
}

/**
 * Draws the line home on the canvas.
 * @param c the canvas to draw on
 * @param x1 the position of the col where the first part of the home is
 * @param x2 the position of the row where the second part of the home is
 * @param y the position of the col where the part of the home is
 */
fun drawLineHome(c:Canvas, x1: Int, x2: Int, y: Int){
    c.drawImage("frogger|1,188,32,24",x1,y,HOME_WIDTH,HOME_HEIGHT)
    c.drawImage("frogger|35,188,8,24",x2,y,HOME_INSIDE ,HOME_HEIGHT)
}
/**
 * Draws the line home on all the row of the canvas.
 * @param c the canvas to draw on
 */
fun timesdrawhome(c: Canvas){
    for (x in 0..SCREEN_WIDTH step HOME_END) {
        drawLineHome(c,x,x+GRID_SIZE*2,GRID_SIZE+GRID_SIZE/2)
        drawLineHome(c, x, x + GRID_SIZE * 2 + GRID_SIZE / 2, GRID_SIZE + GRID_SIZE / 2)
    }
}

/**
 * Draws the river on different parts of the canvas.
 * @param c the canvas to draw on
 * @param x the position of the col where the river is
 * @param y the position of the row where the river is
 */
fun drawriver(c: Canvas, x: Int, y:Int){
    c.drawImage("frogger|1,390,16,16",x,y,GRID_SIZE*GRID_COLS,GRID_SIZE*(GRID_ROWS/2))
}

fun Car.drawCars(c: Canvas,x:Int,y:Int){
    when (type) {
        CarType.TRUCK -> c.drawImage("frogger|74,116,32,16",x,y,GRID_SIZE*2,GRID_SIZE)
        CarType.SPEED2 -> c.drawImage("frogger|19,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.SPEED1 -> c.drawImage("frogger|37,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.BULLDOZER -> c.drawImage("frogger|56,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.CAR -> c.drawImage("frogger|0,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
    }
}

fun Canvas.drawFrog(c:Canvas, x: Int, y:Int) {
    c.drawImage("frogger|0,0,16,16", x, y, GRID_SIZE, GRID_SIZE)
}

/**
 * Draws all game arena.
 * Before drawing the canvas,it will be erased
 * @receiver the canvas to draw on
 * @param g(Frogger) the positions of the cars to be drawn
 */
fun Canvas.drawGame(g: Frogger) {
    erase()
    drawriver(this,0,0)
    setSidewalks(this,UPPERWALK_ROW,DOWNWALK_ROW)
    timesdrawhome(this)
    timesdrawhome(this)
    drawFrog(this, g.frog.position.col, g.frog.position.row) //sapo não está centralizado com as casas
    getCars(this)
    //drawGrid()
}