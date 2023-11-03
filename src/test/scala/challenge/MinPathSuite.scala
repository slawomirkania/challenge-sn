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

    assertEquals(MinPath.minPath(input), 18)
  }

  test("data_small.txt") {
    val input = Source.fromResource("data_small.txt").getLines.mkString("\n")

    assertEquals(MinPath.minPath(input), 50)
  }
}
