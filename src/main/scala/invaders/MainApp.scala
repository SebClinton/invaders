package invaders

import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement, KeyboardEvent}
import org.scalajs.dom.{document, window}

import scala.scalajs.js.JSApp

case class GameState(base: Base, drawGuides: Boolean, alienGrid: AlienGrid)

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
    canvas.setAttribute("id", "invaders")
    canvas.setAttribute("width", BlockParty.canvasWidth.toString)
    canvas.setAttribute("height", BlockParty.canvasHeight.toString)

    document.body.appendChild(canvas)

    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    // here is the mutable state for the game
    var gameState = GameState(
      Base(BlockX(BlockParty.screenWidth.v / 2 - Base.sprite.blockWidth.v / 2)),
      drawGuides = false,
      AlienGrid.create
    )
    var previous: Double = 0

    def frame(timestamp: Double): Unit = {
      val elapsed = timestamp - previous
      gameState = update(gameState, elapsed / 1000)
      draw(gameState, ctx)
      previous = timestamp
      window.requestAnimationFrame(frame)
    }

    def keyDown(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = true)

    def keyUp(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = false)

    canvas.focus()
    window.requestAnimationFrame(frame)
    window.onkeydown = keyDown
    window.onkeyup = keyUp
  }

  def update(gameState: GameState, elapsed: Double): GameState = {
    // TODO implement this!
    gameState
  }

  def keyHandler(gs: GameState, e: KeyboardEvent, value: Boolean): GameState = {
    e.key match {
      case "ArrowLeft" | "a" =>
        e.preventDefault()
        gs // TODO: update the game state to move the base left

      case "ArrowRight" | "d" =>
        e.preventDefault()
        gs // TODO: update the game state to move the base right

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
