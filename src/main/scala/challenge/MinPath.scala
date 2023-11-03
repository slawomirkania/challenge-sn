package com.example
package challenge

import scala.annotation.tailrec

object MinPath {
  def minPath(input: String): Int = {
    val triangle       = stringAsVector(input)
    val triangleLevels = triangle.length
    val triangleTail   = triangle.dropRight(1)

    val result = triangle.last.zipWithIndex
      .foldLeft(Vector.empty[Int]) { case (acc, (digit, digitIndex)) =>
        val leftParentIndex  = digitIndex - 1
        val rightParentIndex = digitIndex

        val startLevel = 1

        val left =
          if (leftParentIndex >= 0)
            calculateTail(
              digit,
              Index(leftParentIndex),
              ActualLevel(startLevel),
              triangleTail,
              Levels(triangleLevels)
            ).toVector
          else Vector.empty[Int]

        val right = calculateTail(
          digit,
          Index(rightParentIndex),
          ActualLevel(startLevel),
          triangleTail,
          Levels(triangleLevels)
        ).toVector

        acc ++ left ++ right
      }

    result.min
  }

  case class ActualLevel(value: Int) extends AnyVal {
    def increment: ActualLevel = ActualLevel(value + 1)
  }

  case class Index(value: Int)  extends AnyVal
  case class Levels(value: Int) extends AnyVal

  @tailrec
  private def calculateTail(
      acc: Int,
      index: Index,
      actualLevel: ActualLevel,
      tail: Vector[Vector[Int]],
      levels: Levels
  ): Option[Int] = {
    lazy val result: Option[Int] =
      if (actualLevel.value == levels.value) Some(acc)
      else None

    if (tail.nonEmpty) {
      lazy val nextLevel = tail.takeRight(1).head
      lazy val tailLeft  = tail.dropRight(1)

      if (index.value > 0 && index.value < nextLevel.length)
        calculateTail(
          acc + nextLevel(index.value),
          Index(index.value - 1),
          actualLevel.increment,
          tailLeft,
          levels
        )
      else if (index.value == 0)
        calculateTail(
          acc + nextLevel(index.value),
          index,
          actualLevel.increment,
          tailLeft,
          levels
        )
      else result
    } else result
  }

  private def stringAsVector(input: String): Vector[Vector[Int]] =
    input.split("\n").toVector.map(_.split(" ").toVector.map(_.toInt))
}
