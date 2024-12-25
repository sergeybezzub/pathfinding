package org.testtask;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class FindShortestPathBFS {


    public static void main(String[] args) {
        // Find the shortest path by BFS and list of connections
        List<String> connections = Arrays.asList("RoomA-RoomB", "RoomB-RoomC", "RoomA-RoomD", "RoomD-RoomC", "RoomC-RoomE");
        List<String> shortestPath3 = FindShortestPathBFS.findShortestPath(connections, "RoomA", "RoomE");
        System.out.println("Shortest Path Test3: " + shortestPath3);
    }

    public static List<String> findShortestPath(List<String> connections, String startNode, String endNode) {
        Set<String> roomSet = new HashSet<>();
        Map<String, GraphNode> roomMap = new HashMap<>();
        List<GraphNode> defaultNeighbors = new ArrayList<>();

        for (String roomConnection : connections) {
            String[] rooms = roomConnection.split("-");
            roomSet.add(rooms[0]);
            roomSet.add(rooms[1]);

            roomMap.computeIfAbsent(rooms[0], k -> new GraphNode(rooms[0], defaultNeighbors));
            roomMap.computeIfAbsent(rooms[1], k -> new GraphNode(rooms[1], defaultNeighbors));

            roomMap.computeIfPresent(rooms[0], (k, v) -> {
                v.addNeighbor(roomMap.get(rooms[1]));
                return v;
            });
            roomMap.computeIfPresent(rooms[1], (k, v) -> {
                v.addNeighbor(roomMap.get(rooms[0]));
                return v;
            });

        }

        return FindShortestPathBFS.findShortestPath(roomMap.get(startNode), startNode, endNode);
    }


    public static List<String> findShortestPath(GraphNode graph, String startNode, String endNode) {

        // Perform BFS to find the shortest path
        Queue<GraphNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> paths = new HashMap<>();

        queue.add(graph);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            GraphNode graphNode = queue.poll();
            String currentNodeName = graphNode.name;

            // Check if we found the end room
            if (currentNodeName.equals(endNode)) {
                return generatePath(paths, graphNode.name);
            }

            // Explore neighbors
            List<GraphNode> neighbors = new ArrayList<>();
            neighbors.addAll(graphNode.neighbors);
            for (GraphNode neighbor : neighbors) {
                if (!visited.contains(neighbor.name)) {
                    paths.put(neighbor.name, currentNodeName);
                    visited.add(neighbor.name);
                    queue.add(neighbor);
                }
            }
        }

        // No path found
        return new ArrayList<>();
    }

    // Generate the path afte the target node has been found
    private static List<String> generatePath(Map<String, String> paths, String nodeName) {
        List<String> pathList = new ArrayList<>();
        pathList.add(nodeName);
        String key = nodeName;
        while (paths.get(key) != null) {
            String path = paths.get(key);
            pathList.add(path);
            key = path;
        }
        Collections.reverse(pathList);
        return pathList;
    }

    @Test
    public void findTheShortestRoomPathFromConnections() {
        // Input for test
        List<String> connections = Arrays.asList("RoomA-RoomB", "RoomB-RoomC", "RoomA-RoomD", "RoomD-RoomC", "RoomC-RoomE");

        // find the shortest path by BFS
        List<String> shortestPath = FindShortestPathBFS.findShortestPath(connections, "RoomA", "RoomE");
        System.out.println("Shortest Path2: " + shortestPath);

        String[] expetedPath = {"RoomA", "RoomB", "RoomC", "RoomE"};
        Assert.assertArrayEquals(expetedPath, shortestPath.toArray());
    }

    @Test
    public void findTheShortestRoomPath() {
        // Input for test
        List<String> connections = Arrays.asList("RoomA-RoomB", "RoomB-RoomC", "RoomA-RoomD", "RoomD-RoomC", "RoomC-RoomE");

        List<GraphNode> defaultNeighbors = new ArrayList<>();
        GraphNode graph = new GraphNode("RoomA", defaultNeighbors);
        GraphNode graphB = new GraphNode("RoomB", defaultNeighbors);
        GraphNode graphC = new GraphNode("RoomC", defaultNeighbors);
        GraphNode graphD = new GraphNode("RoomD", defaultNeighbors);
        GraphNode graphE = new GraphNode("RoomE", defaultNeighbors);

        graph.addNeighbor(graphB);
        graph.addNeighbor(graphD);

        graphB.addNeighbor(graphC);
        graphB.addNeighbor(graph);

        graphD.addNeighbor(graphC);
        graphC.addNeighbor(graphE);
        graphC.addNeighbor(graphD);
        graphC.addNeighbor(graphB);

        graphE.addNeighbor(graphC);

        // find the shortest path by BFS
        List<String> shortestPath = FindShortestPathBFS.findShortestPath(graph, "RoomA", "RoomE");
        System.out.println("Shortest Path2: " + shortestPath);

        String[] expetedPath = {"RoomA", "RoomB", "RoomC", "RoomE"};
        Assert.assertArrayEquals(expetedPath, shortestPath.toArray());
    }

    @Test
    public void findShortestPath() {
        List<GraphNode> defaultNeighbors = new ArrayList<>();
        GraphNode graph = new GraphNode("A", defaultNeighbors);
        GraphNode graphB = new GraphNode("B", defaultNeighbors);
        GraphNode graphC = new GraphNode("C", defaultNeighbors);
        GraphNode graphD = new GraphNode("D", defaultNeighbors);
        GraphNode graphF = new GraphNode("F", defaultNeighbors);
        GraphNode graphG = new GraphNode("G", defaultNeighbors);
        GraphNode graphH = new GraphNode("H", defaultNeighbors);
        GraphNode graphJ = new GraphNode("J", defaultNeighbors);

        graphJ.addNeighbor(graphH);
        graphJ.addNeighbor(graphG);
        graphJ.addNeighbor(graphF);

        graphF.addNeighbor(graphC);
        graphF.addNeighbor(graphJ);

        graphH.addNeighbor(graphD);
        graphH.addNeighbor(graphJ);

        graphG.addNeighbor(graph);
        graphG.addNeighbor(graphC);
        graphG.addNeighbor(graphD);
        graphG.addNeighbor(graphJ);

        graphC.addNeighbor(graph);
        graphC.addNeighbor(graphG);
        graphC.addNeighbor(graphF);

        graphD.addNeighbor(graphB);
        graphD.addNeighbor(graphG);

        graphD.addNeighbor(graph);
        graphD.addNeighbor(graphD);

        graph.addNeighbor(graphC);
        graph.addNeighbor(graphG);
        graph.addNeighbor(graphB);

        // find the shortest path by BFS
        String[] expetedPath = {"A", "C", "F"};
        List<String> shortestPath = findShortestPath(graph, "A", "F");
        System.out.println("Shortest Path1: " + shortestPath);

        Assert.assertArrayEquals(expetedPath, shortestPath.toArray());    }
}
class GraphNode {
    String name;
    List<GraphNode> neighbors = new ArrayList<>();

    public GraphNode(String name, List<GraphNode> neighbors) {
        this.name = name;
        this.neighbors.addAll(neighbors);
    }

    public void addNeighbor(GraphNode neighbor) {
        this.neighbors.add(neighbor);
    }

}
