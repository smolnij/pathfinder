package com.smolnij.research.pathfinding.astar;

import com.smolnij.research.pathfinding.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AStar {

    private final AStarNode[][] map;

    public AStar(final Node[][] maze) {
        map = new AStarNode[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                final Node node = maze[i][j];
                map[i][j] = new AStarNode(node.getX(), node.getY(), node.isBlocked());
            }
        }
    }

    public List<Node> search(final Node sNode, final Node eNode) {
        final List<AStarNode> openList = new LinkedList<>();
        final List<Node> closedList = new LinkedList<>();

        final AStarNode start = map[sNode.getX()][sNode.getY()];
        final AStarNode end = map[eNode.getX()][eNode.getY()];

        openList.add(start);
        boolean found = false;
        boolean noWay = false;
        while (!found && !noWay) {
            final AStarNode currentNode = findCheapest(openList);
            openList.remove(currentNode);
            closedList.add(currentNode);

            final List<AStarNode> neighbors = getNeighbors(currentNode, map);
            for (final AStarNode neighbor : neighbors) {
                if (closedList.contains(neighbor)) {
                    continue;
                }

                if (!openList.contains(neighbor)) {
                    neighbor.parent = currentNode;
                    neighbor.h = heuristic(neighbor, end);
                    neighbor.g = calcStepPrice(currentNode, start);
                    neighbor.f = neighbor.h + neighbor.g;
                    openList.add(neighbor);
                    continue;
                }

                // Если клетка уже в открытом списке, то проверяем, не дешевле ли будет путь через эту клетку.
                // Для сравнения используем стоимость G.
                if (neighbor.g + calcStepPrice(currentNode, neighbor) < currentNode.g) {
                    // Более низкая стоимость G указывает на то, что путь будет дешевле.
                    // Эсли это так, то меняем родителя клетки на текущую клетку и пересчитываем для нее стоимости G и F.
                    neighbor.parent = currentNode; // вот тут я честно хз, надо ли min.parent или нет
                    neighbor.h = heuristic(neighbor, end);
                    neighbor.g = calcStepPrice(start, currentNode);
                    neighbor.f = neighbor.h + neighbor.g;
                }
                // Если вы сортируете открытый список по стоимости F, то вам надо отсортировать свесь список
                // в соответствии с изменениями.

            }


            //d) Останавливаемся если:
            //Добавили целевую клетку в открытый список, в этом случае путь найден.
            //Или открытый список пуст и мы не дошли до целевой клетки. В этом случае путь отсутствует.

            if (openList.contains(end)) {
                found = true;
            }

            if (openList.isEmpty()) {
                noWay = true;
            }


        }

        //3) Сохраняем путь. Двигаясь назад от целевой точки, проходя от каждой точки к ее родителю до тех пор, пока не дойдем до стартовой точки. Это и будет наш путь.
        final List<Node> path = new ArrayList<>();
        if (!noWay) {
            AStarNode pathPoint = end.parent;
            while (!pathPoint.equals(start)) {
                path.add(pathPoint);
                pathPoint = pathPoint.parent;
                if (pathPoint == null) break;
            }

        } else {
            System.out.println("NO ROUTE");
        }
        return path;
    }


    public int calcStepPrice(final AStarNode a, final AStarNode b) {
        if (a.getX() == b.getX() || a.getY() == b.getY()) {
            return 10;
        } else {
            return 14;
        }
    }

    public final int heuristic(final AStarNode a, final AStarNode b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public List<AStarNode> getNeighbors(final Node node, final AStarNode[][] map) {
        final int x = node.getX();
        final int y = node.getY();
        final List<AStarNode> neighbors = new ArrayList<>(8);


        if (y - 1 >= 0) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y - 1].isBlocked()) {
                    neighbors.add(map[x - 1][y - 1]);
                }
            }
            if (!map[x][y - 1].isBlocked()) {
                neighbors.add(map[x][y - 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y - 1].isBlocked()) {
                    neighbors.add(map[x + 1][y - 1]);
                }
            }
        }
        if (x - 1 >= 0) {
            if (!map[x - 1][y].isBlocked()) {
                neighbors.add(map[x - 1][y]);
            }
        }
        if (x + 1 < map.length) {
            if (!map[x + 1][y].isBlocked()) {
                neighbors.add(map[x + 1][y]);
            }
        }

        if (y + 1 < map[0].length) {
            if (x - 1 >= 0) {
                if (!map[x - 1][y + 1].isBlocked()) {
                    neighbors.add(map[x - 1][y + 1]);
                }
            }
            if (!map[x][y + 1].isBlocked()) {
                neighbors.add(map[x][y + 1]);
            }
            if (x + 1 < map.length) {
                if (!map[x + 1][y + 1].isBlocked()) {
                    neighbors.add(map[x + 1][y + 1]);
                }
            }
        }

        return neighbors;
    }

    private AStarNode findCheapest(final List<AStarNode> list) {
        AStarNode cheapest = list.get(0);
        for (final AStarNode node : list) {
            if (node.f < cheapest.f) {
                cheapest = node;
            }
        }
        return cheapest;
    }

}
