package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Sprite(blocks: List[Block]) {
  val blockHeight: BlockY = BlockY(blocks.map(_.y.v + 1).max)
  val blockWidth : BlockX = BlockX(blocks.map(_.x.v + 1).max)

  def blockAt(x: BlockX, y: BlockY): Option[Block] =
    blocks.find(b => b.x == x && b.y == y)

  def activeBlocks: List[Block] =
    blocks.filter(_.color.isDefined)
}

case class PositionedSprite(pos: Point, sprite: Sprite) {
  def collidesWith(point: Point): Boolean =
    sprite.activeBlocks.exists(b => b.x + pos.x == point.x && b.y + pos.y == point.y)

  def collidesWith(other: PositionedSprite): Boolean = {
    other.sprite.activeBlocks.exists { b =>
      val blockPoint = Point(b.x + other.pos.x, b.y + other.pos.y)
      collidesWith(blockPoint)
    }
  }
}

object Sprite {
  /**
    * This assumes that the string is created using scala's multi-line string syntax, which
    * cases blank lines to be present at the start and end of the string, so the code
    * will drop those.
    */
  def fromString(s: String, colorMappings: (Char, String)*): Sprite = {
    val colorMap: Map[Char, String] = Map(colorMappings: _*)

    val blocks = s.split("\n").drop(1).dropRight(1).toList.zipWithIndex.flatMap { case (row, lineNumber) =>
      row.zipWithIndex.map {
        case (c, col) => Block(colorMap.get(c), BlockX(col), BlockY(lineNumber))
      }.toList
    }

    Sprite(blocks)
  }

  def draw(spriteX: BlockX, spriteY: BlockY, sprite: Sprite, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    sprite.blocks.foreach { block =>
      Block.draw(spriteX, spriteY, block, ctx)
    }

    ctx.restore()
  }
}