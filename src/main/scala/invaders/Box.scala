package invaders


case class Point(x: BlockX, y: BlockY) {
  def +(p: Point): Point = Point(x + p.x, y + p.y)
}

object Point {
  def of(x: Int, y: Int): Point = Point(BlockX(x), BlockY(y))
}

case class Box(topLeft: Point, bottomRight: Point) {

  def contains(point: Point): Boolean =
    point.x >= topLeft.x && point.y <= bottomRight.y &&
      point.y >= topLeft.y && point.x <= bottomRight.x

  def overlapsWith(other: Box): Boolean =
    corners.exists(other.contains) || other.corners.exists(contains)

  lazy val corners: List[Point] = List(
    topLeft,
    Point(topLeft.x, bottomRight.y),
    Point(bottomRight.x, topLeft.y),
    bottomRight
  )
}

