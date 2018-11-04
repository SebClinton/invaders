package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class AlienGrid(
                      x: BlockX,
                      y: BlockY,
                      columnPadding: BlockX,
                      rowPadding: BlockY,
                      columns: List[List[Option[Alien1]]]
                    )

object AlienGrid {
  def create: AlienGrid = {
    val aliens: List[List[Option[Alien1]]] = (1 to 11).map { _ =>
      List(Alien1.make, Alien1.make, Alien1.make, Alien1.make, Alien1.make).map(a => Some(a))
    }.toList

    AlienGrid(BlockX(0), BlockY(0), BlockX(2), BlockY(4), aliens)
  }

  def draw(alienGrid: AlienGrid, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    alienGrid.columns.zipWithIndex.foreach { case (column, colIndex) =>
      column.zipWithIndex.foreach { case (option, rowIndex) =>
        option.foreach { alien =>
          val alienX: BlockX = BlockX(colIndex * (11 + alienGrid.columnPadding.v))
          val alienY: BlockY = BlockY(rowIndex * (8 + alienGrid.rowPadding.v))
          Alien1.draw(alienX, alienY, alien, ctx)
        }
      }
    }

    ctx.restore()
  }
}
