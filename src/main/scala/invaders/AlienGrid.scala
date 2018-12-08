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

  val columnCount = columns.length
  val rowCount = columns.foldLeft(0) { (h, col) =>
    h.max(col.length)
  }

  def alienAt(colX: Int, rowY: Int): Option[Alien] = {
    for {
      col <- columns.drop(colX - 1).headOption
      row <- col.drop(rowY - 1).headOption.flatten
    } yield row
  }

  def bombOriginFor(col: Int, row: Int): Option[(BlockX, BlockY)] = {
    alienAt(col, row).map { alien =>
      val alienX: BlockX = x + BlockX(col * (columnWidth.v + columnPadding.v))
      val alienY: BlockY = y + BlockY(row * (rowHeight.v + rowPadding.v))

      (alienX + alien.width.v / 2, alienY + alien.height)
    }
  }
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
