package invaders

object BlockParty {
  val pixelFactor = 5
  val canvasWidth = 800
  val canvasHeight = 600

  val screenWidth: BlockX = BlockX(canvasWidth / pixelFactor)
  val screenHeight: BlockY = BlockY(canvasHeight / pixelFactor)

  val invaderGreen = "#40f62e"
}
