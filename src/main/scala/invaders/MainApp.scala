package invaders

import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement, KeyboardEvent}
import org.scalajs.dom.{document, window}

import scala.scalajs.js.JSApp

case class GameState(
                      base: Base,
                      drawGuides: Boolean,
                      alienGrid: AlienGrid,
                      moveLeft: Boolean,
                      moveRight: Boolean,
                      gridDirectionLeft: Boolean,
                      gridTickDelay: Double
                    )

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
    canvas.setAttribute("id", "invaders")
    canvas.setAttribute("width", BlockParty.canvasWidth.toString)
    canvas.setAttribute("height", BlockParty.canvasHeight.toString)
    canvas.setAttribute("imageSmoothingEnabled", "false")

    document.body.appendChild(canvas)

    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    // here is the mutable state for the game
    var gameState = GameState(
      Base.make(BlockX(BlockParty.screenWidth.v / 2 - Base.sprite.blockWidth.v / 2)),
      drawGuides = false,
      AlienGrid.create,
      moveLeft = false,
      moveRight = false,
      gridDirectionLeft = false,
      gridTickDelay = 850.0
    )

    val tickRate: Double = 20

    val baseLoop: () => Any = { () =>
      gameState = updateBase(gameState)
    }

    lazy val gridLoop: () => Any = { () =>
      gameState = updateGrid(gameState)
      window.setTimeout(gridLoop, gameState.gridTickDelay)
    }

    def frame(elapsed: Double): Unit = {
      draw(gameState, ctx)
      window.requestAnimationFrame(frame)
    }

    def keyDown(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = true)

    def keyUp(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = false)

    canvas.focus()
    window.requestAnimationFrame(frame)
    window.setInterval(baseLoop, tickRate)
    window.setTimeout(gridLoop, gameState.gridTickDelay)
    window.onkeydown = keyDown
    window.onkeyup = keyUp
  }

  def updateGrid(gameState: GameState): GameState = {
    val oldGrid = gameState.alienGrid
    val drawSprite1 = !oldGrid.drawSprite1

    if (gameState.gridDirectionLeft) {
      if (oldGrid.x.v <= 0) {
        gameState.copy(gridDirectionLeft = false, alienGrid = oldGrid.copy(y = oldGrid.y + 4, drawSprite1 = drawSprite1))
      } else {
        gameState.copy(alienGrid = oldGrid.copy(x = oldGrid.x - 2, drawSprite1 = drawSprite1))
      }
    } else {
      if (oldGrid.x + oldGrid.width >= BlockParty.screenWidth) {
        gameState.copy(gridDirectionLeft = true, alienGrid = oldGrid.copy(y = oldGrid.y + 4, drawSprite1 = drawSprite1))
      } else {
        gameState.copy(alienGrid = oldGrid.copy(x = oldGrid.x + 2, drawSprite1 = drawSprite1))
      }
    }
  }

  def updateBase(gameState: GameState): GameState = {
    val oldBase = gameState.base

    if (gameState.moveLeft) {
      val newX = 2.max(oldBase.blockX.v - 2)
      val newBase = oldBase.copy(blockX = BlockX(newX))
      gameState.copy(base = newBase)
    }
    else if (gameState.moveRight) {
      val newX = (oldBase.blockX.v + 2).min(BlockParty.screenWidth.v - oldBase.blockWidth.v - 2)
      val newBase = oldBase.copy(blockX = BlockX(newX))
      gameState.copy(base = newBase)
    } else
      gameState
  }

  def keyHandler(gs: GameState, e: KeyboardEvent, value: Boolean): GameState = {
    e.key match {
      case "ArrowLeft" | "a" =>
        e.preventDefault()
        gs.copy(moveLeft = value)

      case "ArrowRight" | "d" =>
        e.preventDefault()
        gs.copy(moveRight = value)

      case _ => gs
    }
  }

  def draw(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height)
    if (gameState.drawGuides) Grid.draw(ctx)
    Base.draw(gameState.base, ctx)
    AlienGrid.draw(gameState.alienGrid, ctx)
  }

}
