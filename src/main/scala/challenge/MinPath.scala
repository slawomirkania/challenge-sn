package com.example
package challenge

import scala.annotation.tailrec

object MinPath {
  def minPath(input: String): Int = {
    val triangle = input.split("\n").toVector.map(_.split(" ").toVector.map(_.toInt))
    val levels   = triangle.length

    @tailrec
    def calcTail(acc: Int, level: Int, index: Int, tail: Vector[Vector[Int]]): Option[Int] =
      tail match {
        case Nil =>
          if (level == levels) Some(acc)
          else None
        case _ =>
          val last = tail.takeRight(1).last
          if (index > 0 && index < last.length)
            calcTail(acc + last(index), level + 1, index - 1, tail.dropRight(1))
          else if (index == 0) calcTail(acc + last(index), level + 1, index, tail.dropRight(1))
          else if (level == levels) Some(acc)
          else None
      }

    val tail = triangle.dropRight(1)
    val result = triangle
      .takeRight(1)
      .last
      .zipWithIndex
      .foldLeft(List.empty[Int]) { case (acc, (elem, index)) =>
        val leftParentIndex  = index - 1
        val rightParentIndex = index

        val left = if (leftParentIndex >= 0) {
          calcTail(elem, 1, leftParentIndex, tail).toList
        } else List.empty[Int]

        val right = calcTail(elem, 1, rightParentIndex, tail).toList

        acc.appendedAll(left ::: right)
      }

    result.min
  }
}
