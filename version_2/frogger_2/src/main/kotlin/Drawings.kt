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
    drawImage("frogger|135,196,16,16",x,y,GRID_SIZE,GRID_SIZE)
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
    drawImage("frogger|1,188,32,24",x1,y,HOME_WIDTH,HOME_HEIGHT)
    drawImage("frogger|35,188,8,24",x2,y,HOME_INSIDE ,HOME_HEIGHT)
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
        CarType.TRUCK -> drawImage("frogger|74,116,32,16",x,y,GRID_SIZE*CarType.TRUCK.size,GRID_SIZE)
        CarType.SPEED2 -> drawImage("frogger|19,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.SPEED1 -> drawImage("frogger|37,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.BULLDOZER -> drawImage("frogger|56,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        CarType.CAR -> drawImage("frogger|0,116,16,16",x,y,GRID_SIZE,GRID_SIZE)
        }
    }
}

fun Canvas.drawFrog(x:Int,y:Int,state:FrogState) {
    when (state) {
        FrogState.STAY -> drawImage("frogger|0,0,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.MOVE -> drawImage("frogger|0,16,16,16", x+GRID_SIZE/2, y+GRID_SIZE/2, GRID_SIZE, GRID_SIZE)
        FrogState.SMASH_1 -> drawImage("frogger|0,32,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.SMASH_2 -> drawImage("frogger|0,48,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.SMASH_3 -> drawImage("frogger|0,64,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.DROWN_1 -> drawImage("frogger|0,80,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.DROWN_2 -> drawImage("frogger|0,96,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.DROWN_3 -> drawImage("frogger|0,112,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.DEAD -> drawImage("frogger|0,128,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.GONE -> drawImage("frogger|0,144,16,16", x, y, GRID_SIZE, GRID_SIZE)
        FrogState.HOME -> drawImage("frogger|0,160,16,16", x, y, GRID_SIZE, GRID_SIZE)
    } //TODO ajeitar as imagens do sapo incluindo a do move com os valores corretos
}

fun Canvas.gameOver(){
    drawImage("frogger|144,361,8,8",(GRID_SIZE/2)*3,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|91,361,8,8",(GRID_SIZE/2)*4,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|46,370,8,8",(GRID_SIZE/2)*5,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|128,361,8,8",(GRID_SIZE/2)*6,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|65,370,8,8",(GRID_SIZE/2)*8,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|127,370,8,8",(GRID_SIZE/2)*9,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|128,361,8,8",(GRID_SIZE/2)*10,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    drawImage("frogger|91,361,8,8",(GRID_SIZE/2)*11,UPPERWALK_ROW,GRID_SIZE,GRID_SIZE)
    //TODO é preciso ajeitar a posição da imagem para o centro do passeio
}

/**
 * Draws all game arena.
 * Before drawing the canvas,it will be erased
 * @receiver the canvas to draw on
 * @param g(Frogger) the positions of the cars to be drawn
 */
fun Canvas.drawGame(g: Frogger) {
    erase()
    drawRiver(0,0)
    setSidewalks(UPPERWALK_ROW,DOWNWALK_ROW)
    timesDrawHome()
    if (g.frog.state != FrogState.GONE) {
        drawFrog(g.frog.position.col, g.frog.position.row, g.frog.state)
    }
    g.cars.forEach {
        drawCars(it.part.x, it.part.row*GRID_SIZE, listOf(it))
    }
    if (g.frog.state == FrogState.DEAD || g.frog.state == FrogState.GONE ) gameOver()
//  drawGrid()
}
