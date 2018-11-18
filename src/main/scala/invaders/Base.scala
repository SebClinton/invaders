package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class BlockX(v: Int) {
  def pixelX: Int = v * BlockParty.pixelFactor

  def -(i: Int): BlockX = BlockX(v - i)

  def +(i: Int): BlockX = BlockX(v + i)

  def +(i: BlockX): BlockX = BlockX(v + i.v)

  def >=(x: BlockX): Boolean = v >= x.v
}

case class BlockY(v: Int) {
  def pixelY: Int = v * BlockParty.pixelFactor

  def +(i: Int): BlockY = BlockY(v + i)

  def +(i: BlockY): BlockY = BlockY(v + i.v)
}

case class Base(blockX: BlockX, sprite: Sprite) {
  val blockHeight: BlockY = sprite.blockHeight
  val blockWidth: BlockX = sprite.blockWidth
}

object Base {

  def make(x: BlockX): Base = Base(x, sprite)

  def draw(base: Base, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    Sprite.draw(base.blockX, BlockY(BlockParty.screenHeight.v - sprite.blockHeight.v - 40), base.sprite, ctx)

    ctx.restore()
  }

  val blockString: String =
    """
      |      x
      |     xxx
      |     xxx
      | xxxxxxxxxxx
      |xxxxxxxxxxxxx
      |xxxxxxxxxxxxx
      |xxxxxxxxxxxxx
      |xxxxxxxxxxxxx
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> BlockParty.invaderGreen)

}
