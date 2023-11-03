package com.example
package challenge

object Main {
  def main(args: Array[String]) = {
    val result = MinPath.minPath(args(0))

    println(s"Minimal path is: $result")
  }
}
