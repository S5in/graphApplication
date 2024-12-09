import java.util.*;

class Dijkstra {
    public static String calculate(GraphPanel graph, int startIndex, int endIndex) {
        List<Node> nodes = graph.getNodes();
        Map<Node, List<Edge>> adjacencyList = graph.getAdjacencyList();
        
        // Initialize distances and priority queue
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::getDistance));

        // Initialize all nodes with "infinity" distance, except the start node
        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(nodes.get(startIndex), 0);
        pq.add(new NodeDistance(nodes.get(startIndex), 0));
        
        // Dijkstra's algorithm
        while (!pq.isEmpty()) { 
            NodeDistance currentNodeDist = pq.poll();
            Node currentNode = currentNodeDist.getNode();
            int currentDist = currentNodeDist.getDistance();
            
            if (currentNode == nodes.get(endIndex)) {
                break; // Early exit if we reached the end node
            }

            // Explore neighbors
            for (Edge edge : adjacencyList.getOrDefault(currentNode, Collections.emptyList())) {
                Node neighbor = nodes.get(edge.getEnd());
                int newDist = currentDist + edge.getWeight();
                
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    pq.add(new NodeDistance(neighbor, newDist));
                }
            }
        }

        // Reconstruct path
        List<Node> path = new ArrayList<>();
        Node current = nodes.get(endIndex);
        while (current != null) {
            path.add(current);
            current = previousNodes.get(current);
        }
        Collections.reverse(path);

        // Prepare the result string
        StringBuilder result = new StringBuilder();
        result.append("Shortest path from ")
              .append(nodes.get(startIndex).getLabel())
              .append(" to ")
              .append(nodes.get(endIndex).getLabel())
              .append(": ");
        for (Node node : path) {
            result.append(node.getLabel()).append(" -> ");
        }
        result.delete(result.length() - 4, result.length()); // Remove last arrow
        result.append("\n Distance: ").append(distances.get(nodes.get(endIndex)));
        
        return result.toString();
    }
}

