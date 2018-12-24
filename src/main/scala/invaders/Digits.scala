package invaders

object Digits {

  def spritesFor(n: Int): List[Sprite] = {
    val sprite: Sprite = spriteFor(n % 10)
    val sprites: List[Sprite] = if (n < 10) List() else spritesFor(n / 10)

    sprites :+ sprite
  }

  def spriteFor(digit: Int): Sprite = sprites(digit)

  val sprites: List[Sprite] =
    List(s0, s1, s2, s3, s4, s5, s6, s7, s8, s9)
      .map(s => Sprite.fromString(s, 'x' -> BlockParty.invaderGreen))

  lazy val s0: String =
    """
      | xxx
      |x   x
      |x   x
      |x   x
      | xxx
    """.stripMargin

  lazy val s1: String =
    """
      |xx
      | x
      | x
      | x
      | x
    """.stripMargin

  lazy val s2: String =
    """
      |xxx
      |   x
      | xxx
      |x
      |xxxx
    """.stripMargin

  lazy val s3 =
    """
      |xxx
      |   x
      | xxx
      |   x
      |xxx
    """.stripMargin

  lazy val s4 =
    """
      |x  x
      |x  x
      |xxxx
      |   x
      |   x
    """.stripMargin

  lazy val s5 =
    """
      |xxxx
      |x
      |xxx
      |   x
      |xxx
    """.stripMargin

  lazy val s6 =
    """
      | xxx
      |x
      |xxxx
      |x   x
      | xxx
    """.stripMargin

  lazy val s7 =
    """
      |xxxx
      |   x
      |   x
      |  x
      |  x
    """.stripMargin

  lazy val s8 =
    """
      | xxx
      |x   x
      | xxx
      |x   x
      | xxx
    """.stripMargin

  lazy val s9 =
    """
      | xxx
      |x   x
      | xxxx
      |    x
      | xxx
    """.stripMargin
}
