package invaders

import scala.util.Random

object UpdateFunctions {
  def updateGrid(gameState: GameState): GameState = {
    // When the grid reaches the edge of the screen it will move down this number of blocks
    val stepDownBlockCount = 4

    // Each time the grid moves it will move this number of blocks left or right
    val stepAcrossBlockCount = 2

    val oldGrid = gameState.alienGrid
    val drawSprite1 = !oldGrid.drawSprite1

    val newState = if (gameState.gridDirectionLeft) {
      if (oldGrid.x.v <= 0) {
        gameState.copy(gridDirectionLeft = false, alienGrid = oldGrid.copy(y = oldGrid.y + stepDownBlockCount, drawSprite1 = drawSprite1))
      } else {
        gameState.copy(alienGrid = oldGrid.copy(x = oldGrid.x - stepAcrossBlockCount, drawSprite1 = drawSprite1))
      }
    } else {
      if (oldGrid.x + oldGrid.width + stepAcrossBlockCount >= BlockParty.arenaWidth) {
        gameState.copy(gridDirectionLeft = true, alienGrid = oldGrid.copy(y = oldGrid.y + stepDownBlockCount, drawSprite1 = drawSprite1))
      } else {
        gameState.copy(alienGrid = oldGrid.copy(x = oldGrid.x + stepAcrossBlockCount, drawSprite1 = drawSprite1))
      }
    }
    newState.copy(splats = List())
  }

  def updateBombs(gameState: GameState): GameState = {
    val newBombs: List[Bomb] = gameState.bombs.flatMap { b =>
      if (b.y.v > BlockParty.screenHeight.v) None
      else Some(b.copy(y = b.y + 1))
    }

    val createdBomb: Option[Bomb] = if (Random.nextFloat() <= 0.005) createBomb(gameState)
    else None

    val newState = gameState.copy(bombs = newBombs ++ createdBomb)
    CollisionFunctions.checkBombsAndBase(newState)
  }

  private def createBomb(gameState: GameState): Option[Bomb] = {
    val col = Col(Random.nextInt(gameState.alienGrid.columnCount))
    val row = Row(Random.nextInt(gameState.alienGrid.rowCount))

    gameState.alienGrid.bombOriginFor(row, col).map { case (x, y) =>
      Bomb.make(x, y)
    }
  }

  def updateBullet(gameState: GameState): GameState = {
    val gs = gameState.bullet.map { b =>
      val newBullet =
        if (b.y.v <= 0) None
        else {
          val newY = b.y - 1
          Some(b.copy(y = newY))
        }

      gameState.copy(bullet = newBullet)
    }.getOrElse(gameState)

    CollisionFunctions.checkBulletAndAliens(gs)
  }

  def updateBase(gameState: GameState): GameState = {
    val oldBase = gameState.base

    if (gameState.moveLeft) {
      val newX = 2.max(oldBase.x.v - 2)
      val newBase = oldBase.copy(x = BlockX(newX))
      gameState.copy(base = newBase)
    } else if (gameState.moveRight) {
      val newX = (oldBase.x.v + 2).min(BlockParty.arenaWidth.v - oldBase.blockWidth.v - 2)
      val newBase = oldBase.copy(x = BlockX(newX))
      gameState.copy(base = newBase)
    } else gameState
  }

}
