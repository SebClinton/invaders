package invaders

object CollisionFunctions {

  def checkBulletAndAliens(gameState: GameState): GameState = {
    gameState.bullet.map { b =>
      val bulletPS = PositionedSprite(Point(b.x, b.y), b.sprite)

      if (gameState.alienGrid.box.overlapsWith(b.box)) {
        val maybeAlien: Option[(PositionedAlien, Row, Col)] = gameState.alienGrid.positionedAliens.find { case (a, _, _) =>
          a.box.overlapsWith(b.box)
        }.flatMap { case (a, row, col) =>
          println(s"hit alien at row $row, col $col")
          val alienSprite = if (gameState.alienGrid.drawSprite1) a.alien.sprite1 else a.alien.sprite2
          val alienPS = PositionedSprite(a.pos, alienSprite)

          if (alienPS.collidesWith(bulletPS)) Some((a, row, col)) else None
        }

        maybeAlien.map { case (a, row, col) =>
          Sounds.explosion.play()
          gameState.copy(bullet = None)
        }.getOrElse(gameState)
      }
      else gameState
    }.getOrElse(gameState)
  }

}
