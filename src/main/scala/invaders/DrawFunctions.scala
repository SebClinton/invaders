package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

object DrawFunctions {

  import BlockParty._


  def drawScoreboard(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    val sprites: List[(Sprite, Int)] = Digits.spritesFor(gameState.score).zipWithIndex

    ctx.clearRect(0,0, screenWidth.pixelX, arenaTopLeft.y.pixelY)
    sprites.foreach { case (s, i) =>
      Sprite.draw(BlockX(20 + i * 6), BlockY(15), s, ctx)
    }
  }

  def drawArena(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {

    ctx.save()
    ctx.translate(arenaTopLeft.x.pixelX, arenaTopLeft.y.pixelY)
    ctx.clearRect(0, 0, arenaWidth.pixelX, arenaHeight.pixelY)
    drawBase(gameState.base, ctx)
    drawGrid(gameState.alienGrid, ctx)
    gameState.bullet.foreach(Bullet.draw(_, ctx))
    gameState.bombs.foreach(Bomb.draw(_, ctx))
    gameState.forts.foreach(Fort.draw(_, ctx))
    gameState.splats.foreach(drawSplat(_, ctx))
    ctx.restore()
  }


  def drawSplat(splat: Splat, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(splat.x, splat.y, splat.sprite, ctx)
    ctx.restore()
  }

  def drawBase(base: Base, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()
    Sprite.draw(base.x, base.y, base.sprite, ctx)
    ctx.restore()
  }


  def drawGrid(grid: AlienGrid, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    grid.columns.zipWithIndex.foreach { case (column, colIndex) =>
      column.zipWithIndex.foreach { case (option, rowIndex) =>
        option.foreach { alien =>
          val pos = grid.topLeftOf(Row(rowIndex), Col(colIndex))
          Alien.draw(pos.x, pos.y, grid.spriteFor(alien), ctx)
        }
      }
    }

    ctx.restore()
  }

}
