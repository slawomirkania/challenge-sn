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
- added some basic validation if input can not be decoded into the Vector

## Algorithm description

```
7
6 3
3 8 5
11 2 10 9
```

### Read string to Vector[Vector[Int]] by splitting it via new line and then by space 

`Vector(Vector(7), Vector(6, 3), Vector(3, 8, 5), Vector(11, 2, 10, 9))`

### Reverse vector elements

`Vector(Vector(11, 2, 10, 9), Vector(3, 8, 5), Vector(6, 3), Vector(7))`


### Take head Vector

`11 2 10 9`

### Each element has its index

`11(0) 2(1) 10(2) 9(3)`

### Now we calculate indexes of element left (element index - 1) and right parent (element index, has to exists on checking level, if not just skip the path)

```
11(0)   2(1)   10(2)  9(3)
 L  R   L  R   L  R   L  R
-1  0   0  1   1  2   2  3
```

### while traversing into low levels we are decrementing parent indexes if they are greater than 0

```
11(0)   2(1)   10(2)  9(3)
L   R   L  R   L  R   L  R
-1  0   0  1   1  2   2  3
---------------------------
-1  0   0  0   0  1   1  2
---------------------------
-1  0   0  0   0  0   0  1
---------------------------
-1  0   0  0   0  0   0  0
```

### Some emulation

```
11 2 10 9
3 8 5
6 3
7

11 _ _ _
3 _ _
6 _
7

_ 2 _ _
3 _ _
6 _
7

_ 2 _ _
_ 8 _
6 _
7

_ 2 _ _
_ 8 _
_ 3
7

_ _ 10 _
_ 8 _
6 _
7

_ _ 10 _
_ 8 _
_ 3
7

_ _ 10 _
_ _ 5
_ 3
7

_ _ _ 9
_ _ 5
_ 3
7

```