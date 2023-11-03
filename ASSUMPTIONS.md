1. building "tree" should be faster on Vector than on linked objects (but finding min path on objects would be easier) so instead of that:
  
```
case class Leaf(value: Int, left: Leaf, right: Leaf)

Leaf(
  value = 7,
  left = Leaf(
    value = 6,
    left =  ???,
    right = ???
  ),
  right = ???
)
```

I do that on Vector because it has effective constant time for append new elements, and it is good for rapid access random elements

```
Vector(Vector(7), Vector(6, 3), Vector(3, 8, 5), Vector(11, 2, 10, 9))
```

I assume that input is correct and non-empty, so I skip validation

2. to find the minimal path we should check all possibilities from last row and choose the minimal at the end

## After the competition
- Improved readability a little bit
- List instead of Vector took 17 seconds to complete
- Vector reverse with using head instead of tail reduced execution time to 800ms
- some refactoring took another 100ms acceleration
- I may lose few milliseconds here ```.groupBy(_._1).minBy(_._1)._2.head``` but I wanted to complete the task fully