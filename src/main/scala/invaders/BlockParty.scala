package invaders

object BlockParty {
  val pixelFactor = 3
  val canvasWidth = 640
  val canvasHeight = 640

  val screenWidth: BlockX = BlockX(canvasWidth / pixelFactor)
  val screenHeight: BlockY = BlockY(canvasHeight / pixelFactor)

  val scoreBoardTopLeft: Point = Point.of(0, 0)
  val scoreBoardWidth: BlockX = screenWidth
  val scoreBoardHeight: BlockY = BlockY(30)

  private val arenaMargin = BlockX(2)
  val arenaTopLeft: Point = Point(arenaMargin, scoreBoardHeight)
  val arenaWidth: BlockX = screenWidth - arenaMargin + arenaMargin
  val arenaHeight: BlockY = screenHeight - scoreBoardHeight

  val invaderGreen = "#40f62e"
}
