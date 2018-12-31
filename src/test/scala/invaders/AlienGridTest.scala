package invaders

import org.scalatest.{FreeSpec, Matchers}

class AlienGridTest extends FreeSpec with Matchers {


  "dropEmptyColsFromLeft" - {
    "should adjust the grid when the left col is empty by" - {

      val grid = makeGridWithColumns(List(
        List(None),
        List(Some(Alien.type1)))
      )

      val result = AlienGrid.dropEmptyColsFromLeft(grid)

      "removing the empty column from the left" in {
        result.columns.length shouldBe 1
      }

      "and updating the x value" in {
        result.x shouldBe grid.columnWidth + grid.columnPadding
      }
    }
    "should adjust the grid when the left two cols are empty by" - {

      val grid = makeGridWithColumns(List(
        List(None),
        List(None),
        List(Some(Alien.type1)))
      )

      val result = AlienGrid.dropEmptyColsFromLeft(grid)

      "removing the empty columns from the left" in {
        result.columns.length shouldBe 1
      }

      "and updating the x value by two column widths with padding" in {
        result.x.v shouldBe (grid.columnWidth + grid.columnPadding).v * 2
      }
    }
  }

  private def makeGridWithColumns(cols: List[List[Option[Alien]]]): AlienGrid = {
    AlienGrid(
      BlockX(0),
      BlockY(0),
      BlockX(5),
      BlockY(3),
      BlockX(10),
      BlockY(5),
      false,
      cols)
  }
}
