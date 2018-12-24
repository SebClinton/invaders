package invaders

case class GameState(
  base: Base,
  drawGuides: Boolean,
  alienGrid: AlienGrid,
  moveLeft: Boolean,
  moveRight: Boolean,
  gridDirectionLeft: Boolean,
  gridTickDelay: Double,
  bullet: Option[Bullet],
  bombs: List[Bomb],
  forts: List[Fort],
  splats: List[Splat],
  score: Int
)
