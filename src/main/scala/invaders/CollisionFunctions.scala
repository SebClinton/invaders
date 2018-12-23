package invaders

object CollisionFunctions {

  def checkBulletAndAliens(gameState: GameState): GameState = {
    val grid = gameState.alienGrid

    gameState.bullet.map { b =>
      if (grid.box.overlapsWith(b.box)) {
        findHitAlien(grid, b).map { case (a, row, col) =>
          Sounds.explosion.play()
          gameState.copy(
            bullet = None,
            alienGrid = gameState.alienGrid.removeAlienAt(row, col),
            splats = gameState.splats :+ Splat.make(a.pos.x, a.pos.y))
        }.getOrElse(gameState)
      }
      else gameState
    }.getOrElse(gameState)
  }

  def findHitAlien(grid: AlienGrid, bullet: Bullet): Option[(PositionedAlien, Row, Col)] = {
    val bulletPS = PositionedSprite(Point(bullet.x, bullet.y), bullet.sprite)
    findOverlappingAlien(grid, bullet).flatMap { case (a, row, col) =>
      val alienPS = PositionedSprite(a.pos, grid.spriteFor(a.alien))
      if (alienPS.collidesWith(bulletPS)) Some((a, row, col)) else None
    }
  }

  def findOverlappingAlien(grid: AlienGrid, bullet: Bullet): Option[(PositionedAlien, Row, Col)] = {
    grid.positionedAliens.find(_._1.box.overlapsWith(bullet.box))
  }
}
