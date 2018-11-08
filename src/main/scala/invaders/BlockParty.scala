package invaders

object BlockParty {
  val pixelFactor = 3
  val canvasWidth = 640
  val canvasHeight = 640

  val screenWidth: BlockX = BlockX(canvasWidth / pixelFactor)
  val screenHeight: BlockY = BlockY(canvasHeight / pixelFactor)

  val invaderGreen = "#40f62e"
}
