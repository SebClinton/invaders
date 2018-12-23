package invaders

object BlockParty {
  val pixelFactor = 3
  val canvasWidth = 640
  val canvasHeight = 640

  val screenWidth: BlockX = BlockX(canvasWidth / pixelFactor)
  val screenHeight: BlockY = BlockY(canvasHeight / pixelFactor)

  val arenaTopLeft: Point = Point.of(2, 25)
  val arenaWidth: BlockX = screenWidth - 4
  val arenaHeight: BlockY = screenHeight - 20

  val invaderGreen = "#40f62e"
}
