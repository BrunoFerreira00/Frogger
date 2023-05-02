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
fun Canvas.drawSideWalk( x: Int, y: Int) {
    this.drawImage("frogger|135,196,16,16",x,y,GRID_SIZE,GRID_SIZE)
}
/**
 * Draws the purple walks on all the row of the canvas.
 * @param c the canvas to draw on
 * @param y1 row of the first sidewalk
 * @param y2 row of the second sidewalk
 */
fun Canvas.setSidewalks( y1: Int, y2: Int) {
    for (x in 0..SCREEN_WIDTH step GRID_SIZE) {
        drawSideWalk( x, y1)
        drawSideWalk( x, y2)
    }
}

/**
 * Draws the line home on the canvas.
 * @param c the canvas to draw on
 * @param x1 the position of the col where the first part of the home is
 * @param x2 the position of the row where the second part of the home is
 * @param y the position of the col where the part of the home is
 */
fun Canvas.drawLineHome( x1: Int, x2: Int, y: Int){
    this.drawImage("frogger|1,188,32,24",x1,y,HOME_WIDTH,HOME_HEIGHT)
    this.drawImage("frogger|35,188,8,24",x2,y,HOME_INSIDE ,HOME_HEIGHT)
}
/**
 * Draws the line home on all the row of the canvas.
 * @param c the canvas to draw on
 */
fun Canvas.timesDrawHome(){
    for (x in 0..SCREEN_WIDTH step HOME_END) {
        drawLineHome(x,x+GRID_SIZE*2,GRID_SIZE+GRID_SIZE/2)
        drawLineHome(x, x + GRID_SIZE * 2 + GRID_SIZE / 2, GRID_SIZE + GRID_SIZE / 2)
    }
}

/**
 * Draws the river on different parts of the canvas.
 * @param c the canvas to draw on
 * @param x the position of the col where the river is
 * @param y the position of the row where the river is
 */
fun Canvas.drawRiver(x: Int, y:Int){
    this.drawImage("frogger|1,390,16,16",x,y,GRID_SIZE*GRID_COLS,GRID_SIZE*(GRID_ROWS/2))
}

fun Canvas.drawCars(x:Int, y:Int, car:List<Car>){
     car.forEach{when (it.type){
        CarType.TRUCK -> this.drawImage("frogger|74,116,32,16",x,y,GRID_SIZE*CarType.TRUCK.size,GRID_SIZE)
        CarType.SPEED2 -> this.drawImage("frogger|19,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.SPEED1 -> this.drawImage("frogger|37,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.BULLDOZER -> this.drawImage("frogger|56,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.CAR -> this.drawImage("frogger|0,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        }
    }
}

fun Canvas.drawFrog( x: Int, y:Int,dir:Direction,state:FrogState) {
    when(state) {
       FrogState.MOVE -> this.drawImage("frogger|${0+18*(dir.ordinal*2)},0,16,16", x, y, GRID_SIZE, GRID_SIZE)
        else -> this.drawImage("frogger|${0+18*dir.ordinal},0,16,16", x, y, GRID_SIZE, GRID_SIZE)
    }
}

/**
 * Draws all game arena.
 * Before drawing the canvas,it will be erased
 * @receiver the canvas to draw on
 * @param g(Frogger) the positions of the cars to be drawn
 */
fun Canvas.drawGame(g: Frogger) {
    erase()
    g.cars.forEach {
        drawCars(it.part.x, it.part.row*GRID_SIZE, listOf(it))
    }
    drawRiver(0,0)
    setSidewalks(UPPERWALK_ROW,DOWNWALK_ROW)
    timesDrawHome()
    drawFrog( g.frog.position.col, g.frog.position.row, g.frog.dir,g.frog.state)
  //  drawGrid()
}