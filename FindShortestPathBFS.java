package org.testtask;

import java.util.*;

public class FindShortestPathBFS {


    public static void main(String[] args) {
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

        // find shortest path by BFS
        List<String> shortestPath2 = findShortestPath(graph, "A", "H");
        System.out.println("Shortest Path2: " + shortestPath2);
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
                return generatePath(paths,graphNode.name);
            }

            // Explore neighbors
            List<GraphNode> neighbors = new ArrayList<>();
            neighbors.addAll(graphNode.neighbors);
            for (GraphNode neighbor : neighbors) {
                if (!visited.contains(neighbor.name)) {
                    paths.put( neighbor.name, currentNodeName);
                    visited.add(neighbor.name);
                    queue.add(neighbor);
                }
            }
        }

        // No path found
        return new ArrayList<>();
    }

    private static List<String> generatePath(Map<String, String> paths, String nodeName ) {
        List<String> pathList = new ArrayList<>();
        pathList.add(nodeName);
        String key = nodeName;
        while(paths.get(key)!= null) {
            String path = paths.get(key);
            pathList.add(path);
            key = path;
        }
        Collections.reverse(pathList);
        return pathList;
    }
}


class GraphNode {
    String name;
    List<GraphNode> neighbors = new ArrayList<>();

    public GraphNode(String name, List<GraphNode> neighbors) {
        this.name=name;
        this.neighbors.addAll(neighbors);
    }

    public void addNeighbor(GraphNode neighbor) {
        this.neighbors.add(neighbor);
    }

}
