import org.scalajs.dom.document
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement}

import scala.scalajs.js.JSApp

case class GameState(drawGuides: Boolean)

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.getElementById("invaders").asInstanceOf[HTMLCanvasElement]
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    canvas.focus()
    draw(ctx, GameState(true))
  }

  def draw(ctx: CanvasRenderingContext2D, gameState: GameState): Unit = {
    if (gameState.drawGuides) drawGrid(ctx)
  }

  def drawGrid(ctx: CanvasRenderingContext2D): Unit = {
    val minor = 10
    val major = minor * 5
    val stroke = "#00FF00"
    val fill = "#009900"
    ctx.save()
    ctx.strokeStyle = stroke
    ctx.fillStyle = fill
    val width = ctx.canvas.width
    val height = ctx.canvas.height

    0.to(width, minor).foreach { x =>
      ctx.beginPath
      ctx.moveTo(x, 0)
      ctx.lineTo(x, height)
      ctx.lineWidth = if (x % major == 0) 0.5
      else 0.25
      ctx.stroke
      if (x % major == 0) ctx.fillText(x.toString, x, 10)
    }

    0.to(height, minor).foreach { y =>
      ctx.beginPath
      ctx.moveTo(0, y)
      ctx.lineTo(width, y)
      ctx.lineWidth = if (y % major == 0) 0.5
      else 0.25
      ctx.stroke
      if (y % major == 0) ctx.fillText(y.toString, 0, y + 10)
    }
  }
}
