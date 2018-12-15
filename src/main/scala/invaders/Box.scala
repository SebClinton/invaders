package invaders


case class Point(x: BlockX, y: BlockY) {
  def +(p: Point): Point = Point(x + p.x, y + p.y)
}

case class Box(topLeft: Point, bottomRight: Point) {

  def contains(point: Point): Boolean =
    point.x >= topLeft.x && point.y <= bottomRight.y &&
      point.y >= topLeft.y && point.x <= bottomRight.x

  def overlapsWith(box: Box): Boolean =
    corners.exists(p => box.contains(p)) ||
      box.corners.exists(p => contains(p))

  lazy val corners: List[Point] = List(
    Point(topLeft.x, topLeft.y),
    Point(topLeft.x, bottomRight.y),
    Point(bottomRight.x, topLeft.y),
    Point(bottomRight.x, bottomRight.y)
  )
}
