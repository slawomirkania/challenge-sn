package com.example
package challenge

import scala.annotation.tailrec

object MinPath {
  def minPath(input: String): Option[(Int, Vector[Int])] = {
    val triangle       = stringAsVector(input)
    val triangleLevels = triangle.length

    (triangle match {
      case head +: tail =>
        head.zipWithIndex.foldLeft(Vector.empty[(Int, Vector[Int])]) { case (acc, (digit, index)) =>
          val leftIndex  = index - 1
          val rightIndex = index
          val startLevel = ActualLevel(1)

          def calculate =
            calculateTail((digit, Vector(digit)), _: Index, startLevel, tail, Levels(triangleLevels)).toVector

          val left: Vector[(Int, Vector[Int])] =
            if (leftIndex >= 0) calculate(Index(leftIndex)) else Vector.empty[(Int, Vector[Int])]
          val right: Vector[(Int, Vector[Int])] = calculate(Index(rightIndex))

          acc ++ left ++ right
        }
      case IndexedSeq() => Vector.empty[(Int, Vector[Int])]
    }).groupBy(_._1).minBy(_._1)._2.headOption
  }

  @tailrec
  private def calculateTail(
      acc: (Int, Vector[Int]),
      index: Index,
      actualLevel: ActualLevel,
      levelsTail: Vector[Vector[Int]],
      levels: Levels
  ): Option[(Int, Vector[Int])] = {
    lazy val result: Option[(Int, Vector[Int])] = Option.when(actualLevel.value == levels.value)(acc)

    levelsTail match {
      case head +: tail =>
        if (index.value > 0 && index.value < head.length) {
          val digit = head(index.value)
          calculateTail(
            (acc._1 + digit, digit +: acc._2),
            index.decrement,
            actualLevel.increment,
            tail,
            levels
          )
        } else if (index.value == 0) {
          val digit = head(index.value)
          calculateTail(
            (acc._1 + digit, digit +: acc._2),
            index,
            actualLevel.increment,
            tail,
            levels
          )
        } else result
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
