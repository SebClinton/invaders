package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

object DrawFunctions {

  import BlockParty._

  def clearRect(x: BlockX, y: BlockY, width: BlockX, height: BlockY, ctx: CanvasRenderingContext2D): Unit =
    ctx.clearRect(x.pixelX, y.pixelY, width.pixelX, height.pixelY)

  def drawArena(gameState: GameState, ctx: CanvasRenderingContext2D): Unit = {

    ctx.save()
    ctx.translate(arenaTopLeft.x.pixelX, arenaTopLeft.y.pixelY)
    ctx.clearRect(0, 0, arenaWidth.pixelX, arenaHeight.pixelY)
    drawBase(gameState.base, ctx)
    AlienGrid.draw(gameState.alienGrid, ctx)
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
    Sprite.draw(base.blockX, base.blockY, base.sprite, ctx)
    ctx.restore()
  }

}
