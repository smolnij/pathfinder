# Pathfinder

A short example of path finding algorithms implemented in java.

You can try it live here.

Try out different algorithms to see how it works.

## Algorithms

### Breadth First Search
Is a "blind" algorithm, may use more memory, but always gives the shortest way.

### Greedy Best First 
Is one of the Best First algorithms, but uses "greedy" approach to find path. In practice that means that it tries to move towards the goal even if the path is not right. As far as it ignores the cost of path, the solution is not always optimal under certain conditions. Greedy best first is fast and efficient and work nicely in a simple path finding environments, could be also considered for pathfinding in certain types of mazes. 

### A*
Is another Best First algorithm, very popular in gamedev because it is very flexible and applicable in many cases.
It consider exact cost of the path, so the path found is guaranteed to be shortest (i.e. cheapeset according to your heuristic functions).

## Heuristics

### Manhattan
### Euclidian
### Chebyshev
