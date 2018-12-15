package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Block(color: Option[String], x: BlockX, y: BlockY)

object Block {
  def draw(spriteX: BlockX, spriteY: BlockY, block: Block, ctx: CanvasRenderingContext2D): Unit = {
    import BlockParty.pixelFactor

    block.color.foreach { color =>
      ctx.save()

      ctx.fillStyle = color
      ctx.fillRect((block.x + spriteX).pixelX, (block.y + spriteY).pixelY, pixelFactor, pixelFactor)

      ctx.restore()
    }
  }
}