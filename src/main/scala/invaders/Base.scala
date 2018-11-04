package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class BlockX(v: Int) extends AnyVal {
  def pixelX: Int = v * BlockParty.pixelFactor
}

case class BlockY(v: Int) extends AnyVal {
  def pixelY: Int = v * BlockParty.pixelFactor
}

case class Base(blockX: BlockX)


object Base {
  def draw(base: Base, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    Sprite.draw(base.blockX, BlockY(BlockParty.screenHeight.v - sprite.blockHeight.v), sprite, ctx)

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
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> BlockParty.invaderGreen)

}
