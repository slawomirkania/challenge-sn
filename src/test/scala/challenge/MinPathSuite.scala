package com.example
package challenge

import munit.FunSuite

class MinPathSuite extends FunSuite {
  test("Min path") {
    assertEquals(MinPath.minPath(Vector(Vector(7), Vector(6, 3), Vector(3, 8, 5), Vector(11, 2, 10, 9))), 18)
  }
}
