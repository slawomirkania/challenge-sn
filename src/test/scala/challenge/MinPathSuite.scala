package com.example
package challenge

import munit.FunSuite

import scala.io.Source

class MinPathSuite extends FunSuite {
  test("Min path basic example") {
    val input =
      """7
        |6 3
        |3 8 5
        |11 2 10 9
        |""".stripMargin

    val result = MinPath.minPath(input)

    assertEquals(result.map(_._1), Some(18))
    assertEquals(result.map(_._2), Some(Vector(7, 6, 3, 2)))
  }

  test("data_small.txt") {
    val input = Source.fromResource("data_small.txt").getLines.mkString("\n")

    assertEquals(MinPath.minPath(input).map(_._1), Some(50))
  }

  test("data_big.txt") {
    val input = Source.fromResource("data_big.txt").getLines.mkString("\n")

    assertEquals(MinPath.minPath(input).map(_._1), Some(2000))
  }
}
