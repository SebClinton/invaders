package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class AlienGrid(
                      x: BlockX,
                      y: BlockY,
                      columnPadding: BlockX,
                      rowPadding: BlockY,
                      columns: List[List[Option[Alien]]]
                    )

object AlienGrid {
  def create: AlienGrid = {
    val aliens: List[List[Option[Alien]]] = (1 to 11).map { _ =>
      List(Alien.makeType3, Alien.makeType2, Alien.makeType2, Alien.makeType1, Alien.makeType1).map(a => Some(a))
    }.toList

    AlienGrid(BlockX(0), BlockY(0), BlockX(2), BlockY(4), aliens)
  }

  def draw(alienGrid: AlienGrid, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    alienGrid.columns.zipWithIndex.foreach { case (column, colIndex) =>
      column.zipWithIndex.foreach { case (option, rowIndex) =>
        option.foreach { alien =>
          val alienX: BlockX = BlockX(colIndex * (12 + alienGrid.columnPadding.v))
          val alienY: BlockY = BlockY(rowIndex * (8 + alienGrid.rowPadding.v))
          Alien.draw(alienX, alienY, alien, ctx)
        }
      }
    }

    ctx.restore()
  }
}
