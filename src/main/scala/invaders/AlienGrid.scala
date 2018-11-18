package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class AlienGrid(
                      x: BlockX,
                      y: BlockY,
                      columnPadding: BlockX,
                      rowPadding: BlockY,
                      columnWidth: BlockX,
                      rowHeight: BlockY,
                      drawSprite1: Boolean,
                      columns: List[List[Option[Alien]]]
                    ) {
  val width: BlockX = BlockX(columnWidth.v * columns.length + columnPadding.v * (columns.length - 1))
}

object AlienGrid {
  def create: AlienGrid = {
    val aliens: List[List[Option[Alien]]] = (1 to 11).map { _ =>
      List(Alien.type3, Alien.type2, Alien.type2, Alien.type1, Alien.type1).map(a => Some(a))
    }.toList

    AlienGrid(BlockX(0), BlockY(0), BlockX(2), BlockY(4), BlockX(12), BlockY(8), true, aliens)
  }

  def draw(alienGrid: AlienGrid, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    alienGrid.columns.zipWithIndex.foreach { case (column, colIndex) =>
      column.zipWithIndex.foreach { case (option, rowIndex) =>
        option.foreach { alien =>
          val alienX: BlockX = alienGrid.x + BlockX(colIndex * (alienGrid.columnWidth.v + alienGrid.columnPadding.v))
          val alienY: BlockY = alienGrid.y + BlockY(rowIndex * (alienGrid.rowHeight.v + alienGrid.rowPadding.v))
          Alien.draw(alienX, alienY, alien, alienGrid.drawSprite1, ctx)
        }
      }
    }

    ctx.restore()
  }
}
