package invaders

import org.scalatest.{FreeSpec, Matchers}

class AlienTest extends FreeSpec with Matchers {

  "the width of an alien" - {
    "should be the width of the wider of the two sprites" in {
      val s1 =
        """
          |xxxxx
        """.stripMargin
      val s2 =
        """
          |xxxxxx
        """.stripMargin

      val alien = Alien.of(s1, s2, 0)

      alien.width shouldBe BlockX(6)
    }
  }

}
