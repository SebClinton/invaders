package invaders

import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLCanvasElement, KeyboardEvent}
import org.scalajs.dom.{document, window}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

case class GameState(
  base: Base,
  drawGuides: Boolean,
  alienGrid: AlienGrid,
  moveLeft: Boolean,
  moveRight: Boolean,
  gridDirectionLeft: Boolean,
  gridTickDelay: Double,
  bullet: Option[Bullet]
)

object Invaders {

  @JSExportTopLevel("invaders.Invaders")
  protected def getInstance(): this.type = this

  import UpdateFunctions._

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Starting 'invaders'...")

    val canvas: HTMLCanvasElement = document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
    canvas.setAttribute("id", "invaders")
    canvas.setAttribute("width", BlockParty.canvasWidth.toString)
    canvas.setAttribute("height", BlockParty.canvasHeight.toString)
    canvas.setAttribute("imageSmoothingEnabled", "false")

    document.body.appendChild(canvas)

    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    // here is the mutable state for the game
    var gameState = GameState(
      Base.make(BlockX(BlockParty.screenWidth.v / 2 - Base.sprite.blockWidth.v / 2)),
      drawGuides = false,
      AlienGrid.create,
      moveLeft = false,
      moveRight = false,
      gridDirectionLeft = false,
      gridTickDelay = 850.0,
      bullet = None
    )


    val bulletLoop: () => Any = { () =>
      gameState = updateBullet(gameState)
    }

    val baseLoop: () => Any = { () =>
      gameState = updateBase(gameState)
    }

    lazy val gridLoop: () => Any = { () =>
      gameState = updateGrid(gameState)
      window.setTimeout(gridLoop, gameState.gridTickDelay)
    }

    def frame(elapsed: Double): Unit = {
      draw(gameState, ctx)
      window.requestAnimationFrame(frame)
    }

    def keyDown(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = true)

    def keyUp(e: KeyboardEvent): Unit =
      gameState = keyHandler(gameState, e, value = false)

    val baseTickDelay: Double = 20
    val bulletTickDelay: Double = 5

    canvas.focus()
    window.requestAnimationFrame(frame)
    window.setInterval(baseLoop, baseTickDelay)
    window.setInterval(bulletLoop, bulletTickDelay)
    window.setTimeout(gridLoop, gameState.gridTickDelay)
    window.onkeydown = keyDown
    window.onkeyup = keyUp
  }

  def keyHandler(gs: GameState, e: KeyboardEvent, value: Boolean): GameState = {
    e.key match {
      case "ArrowLeft" | "a" =>
        e.preventDefault()
        gs.copy(moveLeft = value)

      case "ArrowRight" | "d" =>
        e.preventDefault()
        gs.copy(moveRight = value)

      case " " | "ArrowDown" if gs.bullet.isEmpty =>
        gs.copy(bullet = Some(Bullet.make(gs.base.centreX, gs.base.blockY - 2)))

      case _ => gs
    }
  }

  def draw(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height)
    if (gameState.drawGuides) Grid.draw(ctx)
    Base.draw(gameState.base, ctx)
    AlienGrid.draw(gameState.alienGrid, ctx)
    gameState.bullet.foreach(Bullet.draw(_, ctx))
  }
}
