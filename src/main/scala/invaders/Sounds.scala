package invaders

import scala.scalajs.js
import scala.scalajs.js.annotation._

object Sounds {
  private def sound(src: String): Howl = new Howl(js.Dictionary("src" -> js.Array(src)))

  val shoot: Howl = sound("sounds/shoot.wav")

  val explosion: Howl = sound("sounds/explosion.wav")
  val invaderKilled: Howl = sound("sounds/invaderkilled.wav")

  val chant1: Howl = sound("sounds/fastinvader1.wav")
  val chant2: Howl = sound("sounds/fastinvader2.wav")
  val chant3: Howl = sound("sounds/fastinvader3.wav")
  val chant4: Howl = sound("sounds/fastinvader4.wav")

  val chants = List(chant1, chant2, chant3, chant4)
}

@js.native
@JSGlobal
class Howl(params: js.Dictionary[js.Any]) extends js.Object {
  def play(): Unit = js.native
}