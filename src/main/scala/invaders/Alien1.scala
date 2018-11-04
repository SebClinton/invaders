package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Alien1(sprite1: Sprite, sprite2: Sprite)

object Alien1 {
  def draw(x: BlockX, y: BlockY, alien: Alien1, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    Sprite.draw(x, y, alien.sprite1, ctx)

    ctx.restore()
  }

  def make:Alien1 = of(blockString1, blockString2)

  def of(sprite1String:String, sprite2String:String):Alien1 = {
    val sprite1 = Sprite.fromString(sprite1String, 'y'->"white")
    val sprite2 = Sprite.fromString(sprite2String, 'y'->"white")
    Alien1(sprite1, sprite2)
  }


  val blockString1: String =
    """
      |  y     y
      |   y   y
      |  yyyyyyy
      | yy yyy yy
      |yyyyyyyyyyy
      |y yyyyyyy y
      |y y     y y
      |   yy yy
    """.stripMargin

  val blockString2: String =
    """
      |  y     y
      |y  y   y  y
      |y yyyyyyy y
      |yyy yyy yyy
      |yyyyyyyyyyy
      | yyyyyyyyy
      |  y     y
      | y       y
    """.stripMargin

  val sprite1: Sprite = Sprite.fromString(blockString1, 'y' -> "white")
}