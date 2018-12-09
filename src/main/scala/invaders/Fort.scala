package invaders

import invaders.Base.{blockString, sprite}
import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Fort(x: BlockX, sprite: Sprite) {
  val blockY = BlockY(BlockParty.screenHeight.v - sprite.blockHeight.v - 60)
}


object Fort {

  def make(x: BlockX): Fort = invaders.Fort(x, sprite)

  def draw(fort: Fort, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(fort.x, fort.blockY, fort.sprite, ctx)
    ctx.restore()
  }


  val blockString: String =
    """
      |   xxxxxxxxxxxxx
      |  xxxxxxxxxxxxxxx
      | xxxxxxxxxxxxxxxxx
      |xxxxxxxxxxxxxxxxxxx
      |xxxxxxxxxxxxxxxxxxx
      |xxxxxxxx   xxxxxxxx
      |xxxxxxx     xxxxxxx
      |xxxxxx       xxxxxx
      |xxxxx         xxxxx
      |xxxx           xxxx
      |xxxx           xxxx
      |xxxx           xxxx
      |xxxx           xxxx
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> BlockParty.invaderGreen)

}