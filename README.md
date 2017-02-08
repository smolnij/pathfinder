# Pathfinder

A short example of path finding algorithms implemented in java.

**You can try it live here: [link](pathfinder-smolnij.rhcloud.com) **

Try out different algorithms to see how it works.

## Algorithms
If one take a look at the current implementation of algorithms, one can clearly see how similar they are.

Going purely Java, in fact, the only difference between Breadth First Search, Greedy Best First and A\* in current implementation is a *data structure* which holds nodes-candidates for the next expansion step. And passing h(n) Heuristic strategy (strategy pattern used to get different heuristics) to A\* which always returns 0 turns A\* into Dijkstra's algorithm, so even data structure modification is not needed.

Initial algorithms description of course knows nothing about Java data structures, **but speaking architecture-wise it is a data-structure responsibility to know which nodes are neighbors of the given one.** And we also can use nice and clean approach of automatic by-comparator sort in Java, so we just get the best node from the Queue without looking for the best ourselves. **Speaking about high performance computing, consider better structures (like TreeNode) for such tasks.**

**Generally speaking about the algorithms and perfomance, - as of my experience in any kind of data, - you should consider the data set size when choosing the right algorithm. The more instructions in the algorithm - the more complex it itself. If algorithm spends some time in evaluation part trying to find most optimal solution, than gains would be visible only on a big datasets. Good idea also analyze the dataset itself before choosing an algorithm, you may be suprised, but bubble sort is much faster than a quicksort in certain datasets.**


### Breadth First Search
Is a "blind" algorithm, may use more memory, but always gives the shortest way.

### Greedy Best First 
Is one of the Best First algorithms, but uses "greedy" approach to find path. In practice that means that it tries to move towards the goal even if the path is not right. As far as it ignores the cost of path, the solution is not always optimal under certain conditions. Greedy best first is fast and efficient and work nicely in a simple path finding environments, could be also considered for pathfinding in certain types of mazes. 

### A*
Is another Best First algorithm, very popular in gamedev because it is very flexible and applicable in many cases.
It consider exact cost of the path, so the path found is guaranteed to be shortest (i.e. *cheapest* according to your heuristic functions).
The A\* selects the path based on sum of two functions, - so called g(n), and h(n): f(n)=g(n)+h(n).
Where n is the last node on the path, g(n) is the cost of the path from the start node to n, and h(n) is a heuristic that estimates the cost of the cheapest path from n to the goal.
If h(n) is always zero, then only g(n) is considered, which turns A\* so Dijkstra's algorithm.



## Heuristics
Heuristic term itself means good enough solution for immideiate goal. The Heuristic in pathfinding context selects the closest node to goal and there are many ways of how to determine which one is closer.
Heuristic is a problem specific, so should be chosen according to your particular case.

Although you can pass any evaluation function to the algorithm, the reasonable heuristic should consider graph configuration, e.g. on a square grid that allows 4 directions of movement, use Manhattan distance, but for graph with allowed diagonal moves it is better to use of course heuristic which is able to calculate diagonal movement cost, like Euclidian.


### Manhattan
### Euclidian
### Chebyshev
