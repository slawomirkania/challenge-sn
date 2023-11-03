package com.example
package challenge

object Main {
  def main(args: Array[String]) =
    MinPath.minPath(args(0)) match {
      case Some(result) =>
        val calculations = result._2.mkString(" + ")
        println(s"Minimal path is: $calculations = ${result._1}")
      case None => println("not found")
    }

}
