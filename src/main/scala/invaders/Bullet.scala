package invaders

import invaders.Base.{blockString, sprite}
import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Bullet(x: BlockX, y: BlockY, sprite: Sprite) {

}


object Bullet {

  def make(x: BlockX, y: BlockY): Bullet = invaders.Bullet(x, y, sprite)

  def draw(bullet: Bullet, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(bullet.x, bullet.y, bullet.sprite, ctx)
    ctx.restore()
  }


  val blockString: String =
    """
      |x
      |x
      |x
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> "blue")

}