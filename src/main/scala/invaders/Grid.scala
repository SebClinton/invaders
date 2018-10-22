package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

object Grid {
  def draw(ctx: CanvasRenderingContext2D): Unit = {
    val minor = BlockParty.pixelFactor
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
