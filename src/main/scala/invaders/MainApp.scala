package invaders

import org.scalajs.dom.document
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement}

import scala.scalajs.js.JSApp


case class GameState(base: Base, drawGuides: Boolean)

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
    canvas.setAttribute("id", "invaders")
    canvas.setAttribute("width", BlockParty.canvasWidth.toString)
    canvas.setAttribute("height", BlockParty.canvasHeight.toString)

    document.body.appendChild(canvas)

    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    canvas.focus()
    draw(ctx, GameState(Base(BlockX(BlockParty.screenWidth.v / 2 - Base.sprite.blockWidth.v / 2)), true))
  }

  def draw(ctx: CanvasRenderingContext2D, gameState: GameState): Unit = {
    if (gameState.drawGuides) Grid.draw(ctx)
    Base.draw(gameState.base, ctx)
  }

}
