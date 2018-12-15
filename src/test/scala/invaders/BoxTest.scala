package invaders

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpecLike, Matchers}

class BoxTest extends FreeSpecLike with Matchers with TableDrivenPropertyChecks {

  "contains" in {
    val table = Table(
      ("point", "contains"),
      (Point.of(0, 0), false),
      (Point.of(5, 5), true),
      (Point.of(10, 10), true),
      (Point.of(11, 6), false),
      (Point.of(7, 6), true),
      (Point.of(7, 11), false),
    )
    val box = Box(Point.of(5, 5), Point.of(10, 10))

    forAll(table) { (point, expected) => box.contains(point) shouldBe expected }
  }

  "corners" in {
    val box = Box(Point.of(0, 0), Point.of(19, 22))
    box.corners shouldBe List(Point.of(0, 0), Point.of(0, 22), Point.of(19, 0), Point.of(19, 22))
  }

}
