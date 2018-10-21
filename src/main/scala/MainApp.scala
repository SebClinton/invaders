import org.scalajs.dom.document
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement}

import scala.scalajs.js.JSApp

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.getElementById("invaders").asInstanceOf[HTMLCanvasElement]
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    canvas.focus()


  }
}
