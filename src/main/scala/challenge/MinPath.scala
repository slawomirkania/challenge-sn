package com.example
package challenge

import scala.annotation.tailrec
import scala.util.Try

object MinPath {
  def minPath(input: String): Option[(Int, Vector[Int])] =
    stringAsVector(input).flatMap(findMinPath)

  private def findMinPath(triangle: Vector[Vector[Int]]): Option[(Int, Vector[Int])] = {
    val allLevelsAmount = Levels(triangle.length)
    val startingLevel   = ActualLevel(1)

    (triangle match {
      case head +: tail =>
        head.zipWithIndex.foldLeft(Vector.empty[(Int, Vector[Int])]) { case (acc, (digit, index)) =>
          val leftIndex  = Index(index - 1)
          val rightIndex = Index(index)

          val calculateF =
            calculateTail((digit, Vector(digit)), _: Index, startingLevel, tail, allLevelsAmount).toVector

          val left: Vector[(Int, Vector[Int])] =
            if (leftIndex.value >= 0) calculateF(leftIndex) else Vector.empty[(Int, Vector[Int])]
          val right: Vector[(Int, Vector[Int])] = calculateF(rightIndex)

          acc ++ left ++ right
        }
      case _ => Vector.empty[(Int, Vector[Int])]
    }).groupBy(_._1).minBy(_._1)._2.headOption
  }

  @tailrec
  private def calculateTail(
      acc: (Int, Vector[Int]),
      index: Index,
      actualLevel: ActualLevel,
      levelsTail: Vector[Vector[Int]],
      allLevelsAmount: Levels
  ): Option[(Int, Vector[Int])] = {
    lazy val result: Option[(Int, Vector[Int])] = Option.when(actualLevel.value == allLevelsAmount.value)(acc)
    val (accSum, accAddends)                    = acc

    levelsTail match {
      case head +: tail =>
        if (index.value > 0 && index.value < head.length) {
          val digit = head(index.value)
          calculateTail(
            (accSum + digit, digit +: accAddends),
            index.decrement,
            actualLevel.increment,
            tail,
            allLevelsAmount
          )
        } else if (index.value == 0) {
          val digit = head(index.value)
          calculateTail(
            (accSum + digit, digit +: accAddends),
            index,
            actualLevel.increment,
            tail,
            allLevelsAmount
          )
        } else result
      case _ => result
    }
  }

  private def stringAsVector(input: String): Option[Vector[Vector[Int]]] =
    Try(input.split("\n").toVector.map(_.split(" ").toVector.map(_.toInt)).reverse).toOption

  final case class ActualLevel(value: Int) extends AnyVal {
    def increment: ActualLevel = ActualLevel(value + 1)
  }

  final case class Index(value: Int) extends AnyVal {
    def decrement: Index = Index(value - 1)
  }

  final case class Levels(value: Int) extends AnyVal
}
