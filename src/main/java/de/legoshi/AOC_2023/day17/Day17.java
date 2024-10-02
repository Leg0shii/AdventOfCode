package de.legoshi.AOC_2023.day17;

import de.legoshi.FileHandler;

import java.util.*;

public class Day17 {

    // too high 834, 818
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day17/input.txt");
        String[] values = input.split("\n");

        int xLength = values[0].length();
        int yLength = values.length;
        int[][] map = new int[yLength][xLength];

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                map[y][x] = values[y].charAt(x) - 48;
            }
        }

        Map<String, AStarNode> nodes = new HashMap<>();
        Queue<AStarNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));

        AStarNode currNode = new AStarNode(null, null, new int[]{0, 0}, 0, yLength + xLength - 2);
        openSet.add(currNode);
        nodes.put(Arrays.toString(currNode.position), currNode);

        while (!openSet.isEmpty()) {
            currNode = openSet.poll();

            if (calculateHCost(currNode.position, xLength, yLength) == 0) {
                break;
            }

            for (int[] neighborPos : getNeighbours(currNode, xLength, yLength)) {

                /*String direction = getDirection(currNode.position, neighborPos);
                double tentativeGCost = currNode.gCost + map[neighborPos[1]][neighborPos[0]];

                if (!nodes.containsKey(Arrays.toString(neighborPos))) {
                    double hCost = calculateHCost(neighborPos, xLength, yLength);

                    AStarNode neighborNode = new AStarNode(currNode, direction, neighborPos, tentativeGCost, hCost);
                    nodes.put(Arrays.toString(neighborPos), neighborNode);
                    openSet.add(neighborNode);
                } else {
                    AStarNode neighborNode = nodes.get(Arrays.toString(neighborPos));
                    if (tentativeGCost < neighborNode.gCost) {
                        if (direction.equals(currNode.direction) && (currNode.countDirection + 1) == 3) continue;
                        if (direction.equals(currNode.direction)) {
                            neighborNode.direction = direction;
                            neighborNode.countDirection = currNode.countDirection + 1;
                        } else {
                            neighborNode.direction = direction;
                            neighborNode.countDirection = 1;
                        }

                        neighborNode.parent = currNode;
                        neighborNode.gCost = tentativeGCost;
                        neighborNode.fCost = tentativeGCost + neighborNode.hCost;
                    }
                }*/
            }
        }

        List<AStarNode> solution = new ArrayList<>();
        while (currNode.parent != null) {
            solution.add(currNode);
            currNode = currNode.parent;
        }
        solution.add(currNode);

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                int finalX = x;
                int finalY = y;
                if (solution.stream().anyMatch(n -> n.position[0] == finalX && n.position[1] == finalY)) {
                    String s = solution.stream().filter(n -> n.position[0] == finalX && n.position[1] == finalY).findFirst().get().direction;
                    System.out.print(Objects.requireNonNullElse(s, map[0][0]));
                } else {
                    System.out.print(map[y][x]);
                }
            }
            System.out.println();
        }

        int result = 0;
        for (AStarNode node : solution) {
            result += map[node.position[1]][node.position[0]];
        }

        System.out.println(result - map[0][0]);

    }

    public static String getDirection(int[] posStart, int[] posEnd) {
        if (posStart[0] > posEnd[0]) return "<";
        if (posStart[0] < posEnd[0]) return ">";
        if (posStart[1] > posEnd[1]) return "^";
        if (posStart[1] < posEnd[1]) return "v";
        return "";
    }

    public static double calculateHCost(int[] pos, int xLength, int yLength) {
        return Math.abs(pos[0] - (xLength - 1)) + Math.abs(pos[1] - (yLength - 1));
    }

    public static List<int[]> getNeighbours(AStarNode node, int xLength, int yLength) {
        List<int[]> neighbours = new ArrayList<>();
        neighbours.add(new int[]{node.position[0] - 1, node.position[1]});
        neighbours.add(new int[]{node.position[0] + 1, node.position[1]});
        neighbours.add(new int[]{node.position[0], node.position[1] - 1});
        neighbours.add(new int[]{node.position[0], node.position[1] + 1});

        neighbours.removeIf(n -> n[0] < 0 || n[1] < 0);
        neighbours.removeIf(n -> n[0] >= xLength || n[1] >= yLength);

        if (node.countDirection == 3) {
            switch (node.direction) {
                case "<" -> neighbours.removeIf(neighbor -> neighbor[0] < node.position[0]);
                case ">" -> neighbours.removeIf(neighbor -> neighbor[0] > node.position[0]);
                case "^" -> neighbours.removeIf(neighbor -> neighbor[1] < node.position[1]);
                case "v" -> neighbours.removeIf(neighbor -> neighbor[1] > node.position[1]);
            }
        }

        if (node.direction != null) {
            switch (node.direction) {
                case "<" -> neighbours.removeIf(neighbor -> neighbor[0] > node.position[0]);
                case ">" -> neighbours.removeIf(neighbor -> neighbor[0] < node.position[0]);
                case "^" -> neighbours.removeIf(neighbor -> neighbor[1] > node.position[1]);
                case "v" -> neighbours.removeIf(neighbor -> neighbor[1] < node.position[1]);
            }
        }

        return neighbours;
    }

    public static class AStarNode {
        public AStarNode parent;

        public double hCost;
        public double fCost;
        public double gCost;

        public String direction;
        public int countDirection;
        public int[] position;

        public AStarNode(AStarNode parent, String direction, int[] position, double gCost, double hCost) {
            this.parent = parent;
            this.direction = direction;
            this.position = position;

            this.hCost = hCost;
            this.gCost = gCost;
            this.fCost = gCost + hCost;

            if (parent != null) {
                if (parent.direction != null && parent.direction.equals(direction)) {
                    this.countDirection = parent.countDirection + 1;
                } else {
                    this.countDirection = 1;
                }
            } else {
                this.countDirection = 0;
            }
        }

        @Override
        public String toString() {
            return "AStarNode{" +
                    "hCost=" + hCost +
                    ", fCost=" + fCost +
                    ", gCost=" + gCost +
                    ", direction='" + direction + '\'' +
                    ", countDirection=" + countDirection +
                    ", position=" + Arrays.toString(position) +
                    '}';
        }
    }
}