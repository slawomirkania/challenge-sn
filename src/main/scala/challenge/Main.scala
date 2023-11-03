package com.example
package challenge

object Main {
  def main(args: Array[String]) = {
    val input =
      """7
        |6 3
        |3 8 5
        |11 2 10 9
        |""".stripMargin

    val result = MinPath.minPath(input)

    println(s"Minimal path is: $result")
  }
}
