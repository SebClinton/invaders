package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class BlockX(v: Int) {
  def pixelX: Int = v * BlockParty.pixelFactor

  def -(i: Int): BlockX = BlockX(v - i)

  def -(i: BlockX): BlockX = BlockX(v - i.v)

  def +(i: Int): BlockX = BlockX(v + i)

  def +(i: BlockX): BlockX = BlockX(v + i.v)

  def >=(x: BlockX): Boolean = v >= x.v

  def <(x: BlockX): Boolean = ! >=(x)

  def <=(x: BlockX): Boolean = v <= x.v

  def >(x: BlockX): Boolean = ! <=(x)
}

object BlockX {
  implicit def ord: Ordering[BlockX] =
    (a: BlockX, b: BlockX) => implicitly[Ordering[Int]].compare(a.v, b.v)
}

case class BlockY(v: Int) {
  def pixelY: Int = v * BlockParty.pixelFactor

  def +(i: Int): BlockY = BlockY(v + i)

  def -(i: Int): BlockY = BlockY(v - i)

  def -(i: BlockY): BlockY = BlockY(v - i.v)

  def +(i: BlockY): BlockY = BlockY(v + i.v)

  def >=(y: BlockY): Boolean = v >= y.v

  def <(y: BlockY): Boolean = ! >=(y)

  def <=(y: BlockY): Boolean = v <= y.v

  def >(y: BlockY): Boolean = ! <=(y)
}

object BlockY {
  implicit def ord: Ordering[BlockY] =
    (a: BlockY, b: BlockY) => implicitly[Ordering[Int]].compare(a.v, b.v)
}

case class Base(x: BlockX, sprite: Sprite) {
  val y = BlockY(BlockParty.arenaHeight.v - sprite.blockHeight.v - 40)
  val blockHeight: BlockY = sprite.blockHeight
  val blockWidth: BlockX = sprite.blockWidth
  val centreX: BlockX = x + sprite.blockWidth.v / 2

  val pos = Point(x, y)
}

object Base {



  def make(x: BlockX): Base = Base(x, sprite)



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
