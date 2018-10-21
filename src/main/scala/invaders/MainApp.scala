package invaders

import org.scalajs.dom.document
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement}

import scala.scalajs.js.JSApp


case class GameState(base: Base, drawGuides: Boolean)

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.getElementById("invaders").asInstanceOf[HTMLCanvasElement]
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    canvas.focus()
    draw(ctx, GameState(Base(canvas.width / 2), true))
  }

  def draw(ctx: CanvasRenderingContext2D, gameState: GameState): Unit = {
    if (gameState.drawGuides) Grid.draw(ctx)
    Base.draw(gameState.base, ctx)
  }

}