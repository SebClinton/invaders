package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Sprite(blocks: List[List[Block]]) {
  val blockHeight: BlockY = BlockY(blocks.length)
  val blockWidth: BlockX =
    BlockX(blocks.foldLeft(0)((x, row) => row.length.max(x)))
}

object Sprite {
  /**
    * This assumes that the string is created using scala's multi-line string syntax, which
    * cases blank lines to be present at the start and end of the string, so the code
    * will drop those.
    */
  def fromString(s: String, colorMappings: (Char, String)*): Sprite = {
    val colorMap: Map[Char, String] = Map(colorMappings: _*)

    val blocks = s.split("\n").drop(1).dropRight(1).toList.zipWithIndex.map { case (row, lineNumber) =>
      row.zipWithIndex.map {
        case (c, col) => Block(colorMap.get(c), col, lineNumber)
      }.toList
    }

    Sprite(blocks)
  }

  def draw(spriteX: BlockX, spriteY: BlockY, sprite: Sprite, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    sprite.blocks.zipWithIndex.foreach { case (row, y) =>
      row.zipWithIndex.foreach { case (block, x) =>
        Block.draw(BlockX(spriteX.v + x), BlockY(spriteY.v + y), block, ctx)
      }
    }

    ctx.restore()
  }
}