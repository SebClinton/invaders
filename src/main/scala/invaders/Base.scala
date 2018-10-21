package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Base(x: Double)

object Base {
  val heightInPixels = 7
  val widthInPixels = 13

  val invaderGreen = "#40f62e"

  val pixelFactor = 5.0

  def draw(base: Base, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    drawBottomChunk(base, ctx)

    ctx.restore()
  }

  private def drawBottomChunk(base: Base, ctx: CanvasRenderingContext2D): Unit = {
    // bottom chunk
    val leftx = base.x - (widthInPixels / 2.0) * pixelFactor
    val rightx = base.x + (widthInPixels / 2.0) * pixelFactor

    val topy = ctx.canvas.height - (3 * pixelFactor)
    val bottomy = ctx.canvas.height

    ctx.fillStyle = invaderGreen
    ctx.fillRect(leftx, topy, rightx - leftx, bottomy - topy)
  }
}
