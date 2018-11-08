package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.util.Random

case class Alien(sprite1: Sprite, sprite2: Sprite)

object Alien {
  def draw(x: BlockX, y: BlockY, alien: Alien, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    Sprite.draw(x, y, if (Random.nextBoolean()) alien.sprite1 else alien.sprite2, ctx)

    ctx.restore()
  }

  def makeType1: Alien = of(typeOneSprite1, typeOneSprite2)
  def makeType2: Alien = of(typeTwoSprite1, typeTwoSprite2)
  def makeType3: Alien = of(typeThreeSprite1, typeThreeSprite2)

  def of(sprite1String: String, sprite2String: String): Alien = {
    val sprite1 = Sprite.fromString(sprite1String, 'y' -> "white")
    val sprite2 = Sprite.fromString(sprite2String, 'y' -> "white")
    Alien(sprite1, sprite2)
  }


  val typeTwoSprite1: String =
    """
      |   y     y
      |    y   y
      |   yyyyyyy
      |  yy yyy yy
      | yyyyyyyyyyy
      | y yyyyyyy y
      | y y     y y
      |    yy yy
    """.stripMargin

  val typeTwoSprite2: String =
    """
      |   y     y
      | y  y   y  y
      | y yyyyyyy y
      | yyy yyy yyy
      | yyyyyyyyyyy
      |  yyyyyyyyy
      |   y     y
      |  y       y
    """.stripMargin

  val typeOneSprite1: String =
    """
      |    yyyy
      | yyyyyyyyyy
      |yyyyyyyyyyyy
      |yyy  yy  yyy
      |yyyyyyyyyyyy
      |  yyy  yyy
      | yy  yy  yy
      |  yy    yy
    """.stripMargin

  val typeOneSprite2: String =
    """
      |    yyyy
      | yyyyyyyyyy
      |yyyyyyyyyyyy
      |yyy  yy  yyy
      |yyyyyyyyyyyy
      |   yy  yy
      |  yy yy yy
      |yy        yy
    """.stripMargin

  val typeThreeSprite1: String =
    """
      |     yy
      |    yyyy
      |   yyyyyy
      |  yy yy yy
      |  yyyyyyyy
      |   y yy y
      |  y      y
      |   y    y
    """.stripMargin

  val typeThreeSprite2: String =
    """
      |     yy
      |    yyyy
      |   yyyyyy
      |  yy yy yy
      |  yyyyyyyy
      |    y  y
      |   y yy y
      |  y y  y y
    """.stripMargin
}