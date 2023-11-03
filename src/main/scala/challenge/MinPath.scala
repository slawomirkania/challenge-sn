package com.example
package challenge

import scala.annotation.tailrec

object MinPath {
  def minPath(input: String): Int = {
    val triangle       = stringAsVector(input)
    val triangleLevels = triangle.length

    (triangle match {
      case head +: tail =>
        head.zipWithIndex.foldLeft(Vector.empty[Int]) { case (acc, (digit, index)) =>
          val leftIndex  = index - 1
          val rightIndex = index
          val startLevel = ActualLevel(1)

          def calculate = calculateTail(digit, _: Index, startLevel, tail, Levels(triangleLevels)).toVector

          val left  = if (leftIndex >= 0) calculate(Index(leftIndex)) else Vector.empty[Int]
          val right = calculate(Index(rightIndex))

          acc ++ left ++ right
        }
      case IndexedSeq() => Vector.empty[Int]
    }).min
  }

  @tailrec
  private def calculateTail(
      acc: Int,
      index: Index,
      actualLevel: ActualLevel,
      levelsTail: Vector[Vector[Int]],
      levels: Levels
  ): Option[Int] = {
    lazy val result: Option[Int] = Option.when(actualLevel.value == levels.value)(acc)

    levelsTail match {
      case head +: tail =>
        if (index.value > 0 && index.value < head.length)
          calculateTail(
            acc + head(index.value),
            index.decrement,
            actualLevel.increment,
            tail,
            levels
          )
        else if (index.value == 0)
          calculateTail(
            acc + head(index.value),
            index,
            actualLevel.increment,
            tail,
            levels
          )
        else result
      case IndexedSeq() => result
    }
  }

  private def stringAsVector(input: String): Vector[Vector[Int]] =
    input.split("\n").toVector.map(_.split(" ").toVector.map(_.toInt)).reverse

  final case class ActualLevel(value: Int) extends AnyVal {
    def increment: ActualLevel = ActualLevel(value + 1)
  }

  final case class Index(value: Int) extends AnyVal {
    def decrement: Index = Index(value - 1)
  }

  final case class Levels(value: Int) extends AnyVal
}
