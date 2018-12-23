package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Splat(x: BlockX, y: BlockY, sprite: Sprite) {

}


object Splat {

  def make(x: BlockX, y: BlockY): Splat = Splat(x, y, sprite)

  def draw(splat: Splat, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(splat.x, splat.y, splat.sprite, ctx)
    ctx.restore()
  }


  val blockString: String =
    """
      |    x   x
      | x   x x   x
      |  x       x
      |   x     x
      |xx         xx
      |   x     x
      |  x  x x  x
      | x  x   x  x
    """.stripMargin

  val sprite: Sprite = Sprite.fromString(blockString, 'x' -> "red")

}
