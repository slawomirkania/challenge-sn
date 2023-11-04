package com.example
package challenge

object Main {
  def main(args: Array[String]) =
    MinPath.minPath(args(0)) match {
      case Some((result, addends)) => println(s"Minimal path is: ${addends.mkString(" + ")} = $result")
      case None                     => println("not found")
    }

}
