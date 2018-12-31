package invaders

import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement, KeyboardEvent}
import org.scalajs.dom.{document, window}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

object Invaders {
  @JSExportTopLevel("invaders.Invaders")
  protected def getInstance(): this.type = this

  import UpdateFunctions._

  // here is the mutable state for the game
  var gameState = GameState(
    Base.make(BlockX(BlockParty.screenWidth.v / 2 - Base.sprite.blockWidth.v / 2)),
    drawGuides = false,
    AlienGrid.create,
    moveLeft = false,
    moveRight = false,
    gridDirectionLeft = false,
    gridTickDelay = 850.0,
    bullet = None,
    bombs = List(),
    forts = List(Fort.make(BlockX(25)), Fort.make(BlockX(72)), Fort.make(BlockX(120)), Fort.make(BlockX(169))),
    splats = List(),
    score = 0,
    lives = 3,
    timerHandles = Nil,
    gridLoopHandle = None,
    playerDeath = false,
    stepCount = 0
  )

  private val bulletLoop: () => Any =
    () => gameState = updateBullet(gameState)

  private val bombLoop: () => Any =
    () => gameState = updateBombs(gameState)

  private val baseLoop: () => Any =
    () => gameState = updateBase(gameState)

  private lazy val gridLoop: () => Any = { () =>
    gameState = updateGrid(gameState)
    gameState = gameState.copy(gridLoopHandle = Some(window.setTimeout(gridLoop, gameState.gridTickDelay)))
  }

  private def keyDown(e: KeyboardEvent): Unit =
    gameState = keyHandler(gameState, e, value = true)

  private def keyUp(e: KeyboardEvent): Unit =
    gameState = keyHandler(gameState, e, value = false)

  // How many milliseconds to wait between calls to the base loop
  private val baseTickDelay: Double = 20

  // How many milliseconds to wait between calls to the bullet loop
  private val bulletTickDelay: Double = 5

  private val bombTickDelay: Double = 5

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
    canvas.setAttribute("id", "invaders")
    canvas.setAttribute("width", BlockParty.canvasWidth.toString)
    canvas.setAttribute("height", BlockParty.canvasHeight.toString)
    canvas.setAttribute("imageSmoothingEnabled", "false")

    document.body.appendChild(canvas)

    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    def frame(elapsed: Double): Unit = {
      if (gameState.playerDeath) {
        stopGameLoops()
        gameState = gameState.copy(playerDeath = false)
        window.setTimeout(() => {
          startGameLoops()
          window.requestAnimationFrame(frame)
        }, 1000)
      } else {
        draw(gameState, ctx)
        window.requestAnimationFrame(frame)
      }
    }

    canvas.focus()
    window.requestAnimationFrame(frame)

    startGameLoops()

    window.onkeydown = keyDown
    window.onkeyup = keyUp
  }

  private def startGameLoops(): Unit = {
    val handles = List(
      window.setInterval(baseLoop, baseTickDelay),
      window.setInterval(bulletLoop, bulletTickDelay),
      window.setInterval(bombLoop, bombTickDelay)
    )

    val gridLoopHandle = window.setTimeout(gridLoop, gameState.gridTickDelay)

    gameState = gameState.copy(timerHandles = handles, gridLoopHandle = Some(gridLoopHandle))
  }

  private def stopGameLoops(): Unit = {
    gameState.timerHandles.foreach(window.clearInterval)
    gameState.gridLoopHandle.foreach(window.clearTimeout)
    gameState.copy(timerHandles = Nil, gridLoopHandle = None)
  }

  def keyHandler(gs: GameState, e: KeyboardEvent, value: Boolean): GameState = {
    e.key match {
      case "ArrowLeft" | "a" =>
        e.preventDefault()
        gs.copy(moveLeft = value)

      case "ArrowRight" | "d" =>
        e.preventDefault()
        gs.copy(moveRight = value)

      case " " | "ArrowDown" if gs.bullet.isEmpty =>
        e.preventDefault()
        Sounds.shoot.play()
        gs.copy(bullet = Some(Bullet.make(gs.base.centreX, gs.base.y - 2)))

      case _ => gs
    }
  }

  def draw(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {
    import DrawFunctions._

    if (gameState.lives > 0) {
      drawArena(gameState, ctx)
      drawScoreboard(gameState, ctx)
    } else {
      stopGameLoops()
      drawScoreboard(gameState, ctx)
    }
  }


}
