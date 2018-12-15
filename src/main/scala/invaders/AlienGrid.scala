package invaders

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Row(v: Int)

case class Col(v: Int)

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

  val topLeft: Point = Point(x, y)

  lazy val box: Box =
    Box(Point(x, y), Point(x + width - 1, y + height - 1))

  lazy val width : BlockX = BlockX(columnWidth.v * columnCount + columnPadding.v * (columnCount - 1))
  lazy val height: BlockY = BlockY(rowHeight.v * rowCount + rowPadding.v * (rowCount - 1))

  lazy val columnCount: Int = columns.length
  lazy val rowCount   : Int = columns.map(_.length).max

  def topLeftOf(row: Row, col: Col): Point = {
    topLeft + Point(BlockX(col.v * (columnWidth + columnPadding).v), BlockY(row.v * (rowHeight + rowPadding).v))
  }

  lazy val positionedAliens: List[(PositionedAlien, Row, Col)] = {
    columns.zipWithIndex.flatMap { case (cols, colNum) =>
      cols.zipWithIndex.flatMap { case (maybeA, rowNum) =>
        maybeA.map(a => (PositionedAlien(topLeftOf(Row(rowNum), Col(colNum)), a), Row(rowNum), Col(colNum)))
      }
    }
  }

  def removeAlienAt(row: Row, col: Col): AlienGrid = {
    val newAliens = columns.zipWithIndex.map { case (column, c) =>
      column.zipWithIndex.map { case (a, r) =>
        if (row.v == r && col.v == c) None else a
      }
    }

    copy(columns = newAliens)
  }

  def spriteFor(alien: Alien): Sprite = if (drawSprite1) alien.sprite1 else alien.sprite2

  def alienAt(row: Row, col: Col): Option[Alien] = {
    for {
      column <- columns.drop(col.v).headOption
      a <- column.drop(row.v).headOption.flatten
    } yield a
  }

  def bombOriginFor(row: Row, col: Col): Option[(BlockX, BlockY)] = {
    alienAt(row, col).map { alien =>
      val alienX: BlockX = x + BlockX(col.v * (columnWidth.v + columnPadding.v))
      val alienY: BlockY = y + BlockY(row.v * (rowHeight.v + rowPadding.v))

      (alienX + alien.width.v / 2, alienY + alien.height)
    }
  }
}

object AlienGrid {
  def create: AlienGrid = {
    val aliens: List[List[Option[Alien]]] = (1 to 11).map { _ =>
      List(Alien.type3, Alien.type2, Alien.type2, Alien.type1, Alien.type1).map(a => Some(a))
    }.toList

    val alienTypes = List(Alien.type1, Alien.type2, Alien.type3)
    val columnWidth = alienTypes.map(_.width).max
    val rowHeight = alienTypes.map(_.height).max

    AlienGrid(BlockX(0), BlockY(0), BlockX(2), BlockY(4), columnWidth, rowHeight, drawSprite1 = true, aliens)
  }

  def draw(grid: AlienGrid, ctx: CanvasRenderingContext2D): Unit = {
    ctx.save()

    grid.columns.zipWithIndex.foreach { case (column, colIndex) =>
      column.zipWithIndex.foreach { case (option, rowIndex) =>
        option.foreach { alien =>
          val pos = grid.topLeftOf(Row(rowIndex), Col(colIndex))
          Alien.draw(pos.x, pos.y, grid.spriteFor(alien), ctx)
        }
      }
    }

    ctx.restore()
  }
}
