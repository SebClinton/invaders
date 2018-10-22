package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Block(color: Option[String], x: Int, y: Int)

object Block {
  def draw(blockX: BlockX, blockY: BlockY, block: Block, ctx: CanvasRenderingContext2D): Unit = {
    import BlockParty.pixelFactor

    block.color.foreach { color =>
      ctx.save()

      ctx.fillStyle = color
      ctx.fillRect(blockX.pixelX, blockY.pixelY, pixelFactor, pixelFactor)

      ctx.restore()
    }
  }
}