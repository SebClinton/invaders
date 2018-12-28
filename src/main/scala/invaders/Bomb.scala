package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Bomb(x: BlockX, y: BlockY, sprite: Sprite) {
  val pos = Point(x, y)
}


object Bomb {

  def make(x: BlockX, y: BlockY): Bomb = Bomb(x, y, sprite)

  def draw(bomb: Bomb, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(bomb.x, bomb.y, bomb.sprite, ctx)
    ctx.restore()
  }


  val blockString: String =
    """
      | x
      |x
      | x
      |  x
      | x
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> "red")

}
