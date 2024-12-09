import javax.swing.JOptionPane;
import javax.swing.JPanel; // For JPanel
import java.awt.Font;
import java.awt.Graphics; // For Graphics
import java.awt.Graphics2D; // For Graphics2D
import java.awt.RenderingHints; // For anti-aliasing
import java.util.List; // For List
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList; // For ArrayList
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.awt.Color; // For Color
import javax.swing.JTextField;
import javax.swing.JLabel;

class GraphPanel extends JPanel {
    private final List<Node> nodes; // List of nodes
    private final List<Edge> edges; // List of edges
     // Map to store the adjacency list
    private final Map<Node, List<Edge>> adjacencyList;

    public GraphPanel() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyList = new HashMap<>();

        // Define nodes with positions and labels
        nodes.add(new Node("Wexford", 350, 150));
        nodes.add(new Node("Carlow", 550, 100));
        nodes.add(new Node("Dublin", 750, 200));
        nodes.add(new Node("Waterford", 700, 350));
        nodes.add(new Node("Cork", 500, 400));
        nodes.add(new Node("Limerick", 325, 300));
        nodes.add(new Node("Galway", 500, 250));



        // Define edges (connect nodes by indices)
        edges.add(new Edge(0, 1, (int) Math.sqrt(Math.pow(nodes.get(0).getX() - nodes.get(1).getX(), 2) + Math.pow(nodes.get(0).getY() - nodes.get(1).getY(), 2)))); // A -> B
        edges.add(new Edge(1, 2, (int) Math.sqrt(Math.pow(nodes.get(1).getX() - nodes.get(2).getX(), 2) + Math.pow(nodes.get(1).getY() - nodes.get(2).getY(), 2)))); // B -> C
        edges.add(new Edge(2, 3, (int) Math.sqrt(Math.pow(nodes.get(2).getX() - nodes.get(3).getX(), 2) + Math.pow(nodes.get(2).getY() - nodes.get(3).getY(), 2)))); // C -> D
        edges.add(new Edge(3, 4, (int) Math.sqrt(Math.pow(nodes.get(3).getX() - nodes.get(4).getX(), 2) + Math.pow(nodes.get(3).getY() - nodes.get(4).getY(), 2)))); // D -> E
        edges.add(new Edge(4, 5, (int) Math.sqrt(Math.pow(nodes.get(4).getX() - nodes.get(5).getX(), 2) + Math.pow(nodes.get(4).getY() - nodes.get(5).getY(), 2)))); // E -> F
        edges.add(new Edge(4, 6, (int) Math.sqrt(Math.pow(nodes.get(4).getX() - nodes.get(6).getX(), 2) + Math.pow(nodes.get(4).getY() - nodes.get(6).getY(), 2)))); // E -> G
        edges.add(new Edge(5, 0, (int) Math.sqrt(Math.pow(nodes.get(5).getX() - nodes.get(0).getX(), 2) + Math.pow(nodes.get(5).getY() - nodes.get(0).getY(), 2)))); // F -> A
        edges.add(new Edge(0, 4, (int) Math.sqrt(Math.pow(nodes.get(0).getX() - nodes.get(4).getX(), 2) + Math.pow(nodes.get(0).getY() - nodes.get(4).getY(), 2)))); // A -> E
        edges.add(new Edge(6, 1, (int) Math.sqrt(Math.pow(nodes.get(6).getX() - nodes.get(1).getX(), 2) + Math.pow(nodes.get(6).getY() - nodes.get(1).getY(), 2)))); // G -> B
        edges.add(new Edge(3, 6, (int) Math.sqrt(Math.pow(nodes.get(3).getX() - nodes.get(6).getX(), 2) + Math.pow(nodes.get(3).getY() - nodes.get(6).getY(), 2)))); // D -> G

        // Build the adjacency list
        for (Edge edge : edges) {
            Node from = nodes.get(edge.getStart());
            Node to = nodes.get(edge.getEnd());
            adjacencyList.putIfAbsent(from, new ArrayList<>());
            adjacencyList.putIfAbsent(to, new ArrayList<>());
            adjacencyList.get(from).add(edge);
        }
    }

    // Get the adjacency list
    public Map<Node, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    // Get the nodes
    public List<Node> getNodes() {
        return nodes;
    }     

    public void showAddNodeDialog() {
        String nodeLabel = JOptionPane.showInputDialog("Enter the label for the new node:");
        if (nodeLabel == null || nodeLabel.trim().isEmpty()) {
            return; // Cancel if no input
        }
    
        // Check if the node already exists
        for (Node node : nodes) {
            if (node.getLabel().equalsIgnoreCase(nodeLabel.trim())) {
                JOptionPane.showMessageDialog(null, "A node with this label already exists. Please enter a different label.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if a node with the same label exists
            }
        }
    
        // Create the new node only once
        Node newNode = new Node(nodeLabel, 150, 150); // Initial position, can be dynamic
        nodes.add(newNode);  // Add the new node to the list of nodes
    
        // Show dialog to select connected nodes
        String connections = JOptionPane.showInputDialog("Enter the nodes to connect (comma separated labels):");
        
        if (connections != null && !connections.trim().isEmpty()) {
            String[] labels = connections.split(",");
            int avgX = 0, avgY = 0;
            int connectedNodesCount = 0;
    
            // Iterate through the entered labels to create edges and calculate average position
            for (String label : labels) {
                label = label.trim();  // Ensure proper trimming of label
                if (!label.isEmpty()) {  // Skip empty labels
                    for (Node node : nodes) {
                        if (node.getLabel().equals(label)) {
                            avgX += node.getX();
                            avgY += node.getY();
                            connectedNodesCount++;
                            Edge edge = new Edge(nodes.indexOf(newNode), nodes.indexOf(node), 
                                (int) Math.sqrt(Math.pow(newNode.getX() - node.getX(), 2) + Math.pow(newNode.getY() - node.getY(), 2)));
                            edges.add(edge);
                            // Update adjacency list only for the source node
                            adjacencyList.putIfAbsent(newNode, new ArrayList<>());
                            adjacencyList.get(newNode).add(edge);
                            }
                    }
                }
            }
    
            // If there are connected nodes, set the position of the new node near their average location
            if (connectedNodesCount > 0) {
                if (connectedNodesCount == 1) {
                    // For only one connected node, place the new node slightly offset from it
                    avgX += 50; // Add a small offset to the x position
                    avgY += 50; // Add a small offset to the y position
                }
                else if(connectedNodesCount == 2){
                    avgX -= 150; // Add a small offset to the x position
                    avgY -= 80; // Add a small offset to the y position
                }
                else {
                    avgX /= connectedNodesCount; // Calculate the average x position
                    avgY /= connectedNodesCount; // Calculate the average y position
                }
                newNode.setX(avgX); // Update the position of the new node
                newNode.setY(avgY);
            }
        }
    
        repaint(); // Redraw the graph with the new node and connections
    }
    public void showAddEdgeDialog() {
        // Create input fields
        JTextField startField = new JTextField(5);
        JTextField endField = new JTextField(5);
        JTextField weightField = new JTextField(5);
    
        JPanel dialogPanel = new JPanel();
        dialogPanel.add(new JLabel("Start Node:"));
        dialogPanel.add(startField);
        dialogPanel.add(new JLabel("End Node:"));
        dialogPanel.add(endField);
        dialogPanel.add(new JLabel("Weight:"));
        dialogPanel.add(weightField);
    
        int result = JOptionPane.showConfirmDialog(null,dialogPanel,"Add Edge",JOptionPane.OK_CANCEL_OPTION);
    
        if (result == JOptionPane.OK_OPTION) {
            String startLabel = startField.getText().trim();
            String endLabel = endField.getText().trim();
            String weightText = weightField.getText().trim();
    
            // Validate inputs
            if (startLabel.isEmpty() || endLabel.isEmpty() || weightText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                int weight = Integer.parseInt(weightText);
    
                // Find start and end nodes
                Node startNode = null, endNode = null;
                for (Node node : nodes) {
                    if (node.getLabel().equalsIgnoreCase(startLabel)) {  // Handle case-insensitive matching
                        startNode = node;
                    }
                    if (node.getLabel().equalsIgnoreCase(endLabel)) {  // Handle case-insensitive matching
                        endNode = node;
                    }
                }
    
                if (startNode == null || endNode == null) {
                    JOptionPane.showMessageDialog(null, "Start or End node does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Check if the edge already exists (either direction)
                boolean edgeExists = false;
                for (Edge edge : edges) {
                    if ((edge.getStart() == nodes.indexOf(startNode) && edge.getEnd() == nodes.indexOf(endNode)) || 
                        (edge.getStart() == nodes.indexOf(endNode) && edge.getEnd() == nodes.indexOf(startNode))) {
                        edgeExists = true;
                        break;
                    }
                }

            if (edgeExists) {
                // Edge exists, allow adding the reverse direction but keep the same weight
                JOptionPane.showMessageDialog(null, "This edge already exists. Adding the reverse direction with the same weight.", "Edge Exists", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Add edge if not found
                Edge edge = new Edge(nodes.indexOf(startNode), nodes.indexOf(endNode), weight);
                edges.add(edge);

                // Update adjacency list
                adjacencyList.putIfAbsent(startNode, new ArrayList<>());
                adjacencyList.get(startNode).add(edge);
            }
    
                repaint(); // Redraw the graph with the new edge
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Weight must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw edges
        for (Edge edge : edges) {
            g2d.setColor(Color.BLACK);
            int midX,midY = 0;
            Node start = nodes.get(edge.getStart());
            Node end = nodes.get(edge.getEnd());
            g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            // Draw the arrowhead at the end of the edge
            drawArrowHead(g2d, start, end);
            // Vector edge
            int dx = end.getX() - start.getX();
            int dy = end.getY() - start.getY();
            // Length of the vector
            double length = Math.sqrt(dx * dx + dy * dy);
            // Perpendicular unit vector
            double perpX = -dy / length;
            double perpY = dx / length;
            // Midpoint of the vector
            midX = (start.getX() + end.getX())/2;
            midY =(start.getY() + end.getY())/2;
             // Offset along the perpendicular vector
            int labelX = (int) (midX + perpX*5);
            int labelY = (int) (midY + perpY*5);
            g2d.setColor(Color.RED);
            g2d.drawString(edge.getWeightText(),labelX, labelY);
        }

        
        // Draw nodes
        for (Node node : nodes) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(node.getX() - 15, node.getY() - 15, 30, 30); // Draw circle for the node
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(node.getLabel(), node.getX()- 10, node.getY() - 20); // Draw label
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            
        }
    }
    // Method to draw an arrowhead
    private void drawArrowHead(Graphics2D g2d, Node from, Node to) {
        int arrowSize = 20;  // Size of the arrowhead
        int nodeRadius = 15;  // Radius of the node circle
    
        // Calculate the direction vector from 'from' to 'to'
        double angle = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
    
        // Adjust the "to" position by moving it away from the node circle
        int adjustedToX = (int) (to.getX() - nodeRadius * Math.cos(angle));
        int adjustedToY = (int) (to.getY() - nodeRadius * Math.sin(angle));
    
        // Calculate arrowhead points based on adjusted "to" position
        int x1 = (int) (adjustedToX - arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (adjustedToY - arrowSize * Math.sin(angle - Math.PI / 6));
    
        int x2 = (int) (adjustedToX - arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = (int) (adjustedToY - arrowSize * Math.sin(angle + Math.PI / 6));
    
        // Draw the arrowhead
        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(new int[]{adjustedToX, x1, x2}, new int[]{adjustedToY, y1, y2}, 3);
    }
    public String findClosestCity(String cityLabel) {
        Node startNode = findNodeByLabel(cityLabel);
        if (startNode == null) {
            return "City not found.";
        }
    
        Node closestNode = null;
        int shortestDistance = Integer.MAX_VALUE;
    
        for (Edge edge : edges) {
            Node from = nodes.get(edge.getStart());
            Node to = nodes.get(edge.getEnd());
    
            if (from.equals(startNode)) {
                if (edge.getWeight() < shortestDistance) {
                    shortestDistance = edge.getWeight();
                    closestNode = to;
                }
            } else if (to.equals(startNode)) {
                if (edge.getWeight() < shortestDistance) {
                    shortestDistance = edge.getWeight();
                    closestNode = from;
                }
            }
        }
    
        return (closestNode != null) 
            ? "Closest city to " + cityLabel + " is " + closestNode.getLabel() + " with distance " + shortestDistance 
            : "No connections found for " + cityLabel;
    }
    
    private Node findNodeByLabel(String label) {
        for (Node node : nodes) {
            if (node.getLabel().equalsIgnoreCase(label)) {
                return node;
            }
        }
        return null; // Return null if no matching node is found
    }
    public void showConnectedCitiesDialog() {
        // Ask the user for the city label
        String cityLabel = JOptionPane.showInputDialog("Enter the label of the city:");
    
        // If the user canceled or entered an empty label, return
        if (cityLabel == null || cityLabel.trim().isEmpty()) {
            return;
        }
    
        Node selectedNode = null;
        // Search for the node with the matching label
        for (Node node : nodes) {
            if (node.getLabel().equalsIgnoreCase(cityLabel.trim())) {
                selectedNode = node;
                break;
            }
        }
    
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(null, "City not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Find all connected cities
        List<String> connectedCities = getConnectedCities(selectedNode);
    
        // Show the connected cities in a dialog
        if (connectedCities.isEmpty()) {
            JOptionPane.showMessageDialog(null, cityLabel + " has no connections.", "Connected Cities", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder(cityLabel + " is connected to:\n");
            for (String city : connectedCities) {
                message.append(city).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Connected Cities", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public List<String> getConnectedCities(Node startNode) {
        List<String> connectedCities = new ArrayList<>();
        
        // Check if the start node is null
        if (startNode == null) {
            connectedCities.add("City not found.");
            return connectedCities;
        }
    
        // Get all connected edges (direct connections) for the start node
        List<Edge> connectedEdges = adjacencyList.get(startNode);
        
        // Iterate through the connected edges (edges where startNode is the source)
        if (connectedEdges != null) {
            for (Edge edge : connectedEdges) {
                Node connectedNode = nodes.get(edge.getEnd());
                if (connectedNode != null) {
                    connectedCities.add(connectedNode.getLabel()); // Add to the list of cities sent from the start node
                }
            }
        }
        
        // Now, handle reverse edges (edges where startNode is the target/end)
        for (Edge edge : edges) {
            if (edge.getEnd() == nodes.indexOf(startNode)) {
                Node connectedNode = nodes.get(edge.getStart());
                if (connectedNode != null) {
                    connectedCities.add(connectedNode.getLabel()); // Add to the list of cities received by the start node
                }
            }
        }
    
        // Remove duplicates using a HashSet
        Set<String> uniqueCities = new HashSet<>(connectedCities);
        
        // Return the unique cities as a list
        return new ArrayList<>(uniqueCities);
    }
    public void showSearchSiteDialog() {
        String siteLabel = JOptionPane.showInputDialog("Enter the label of the site:");
        if (siteLabel != null && !siteLabel.trim().isEmpty()) {
            String siteDetails = getSiteDetails(siteLabel.trim());
            JOptionPane.showMessageDialog(this, siteDetails, "Site Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // Get full details of the site (node)
    public String getSiteDetails(String site) {
        StringBuilder result = new StringBuilder();
        
        // Find the start node based on the site label
        Node startNode = null;
        for (Node node : nodes) {
            if (node.getLabel().equalsIgnoreCase(site)) {
                startNode = node;
                break;
            }
        }
        
        // If the start node is not found
        if (startNode == null) {
            return "Site not found.";
        }
        
        // Use the getConnectedCities method to get all connected cities
        List<String> connectedCities = getConnectedCities(startNode);
        
        // If no cities are connected
        if (connectedCities.isEmpty()) {
            result.append("No cities are connected to ").append(site).append(".");
            return result.toString();
        }
        result.append("City name: ").append(startNode.getLabel()).append("\n");
        result.append("Position: (").append(startNode.getX()).append(", ").append(startNode.getY()).append(")\n");
        // Append connected cities with details
        result.append("Details of cities connected to ").append(site).append(":\n");
        
        for (String connectedCity : connectedCities) {
            // Find the node for the connected city
            Node connectedNode = null;
            for (Node node : nodes) {
                if (node.getLabel().equalsIgnoreCase(connectedCity)) {
                    connectedNode = node;
                    break;
                }
            }
            
            if (connectedNode != null) {
                // Find the distance and direction from the startNode
                for (Edge edge : adjacencyList.get(startNode)) {
                    if (edge.getEnd() == nodes.indexOf(connectedNode)) {
                        result.append("- To ").append(connectedCity)
                              .append(" (Distance: ").append(edge.getWeight())
                              .append(")\n");
                    }
                }
                for (Edge edge : edges) {
                    if (edge.getEnd() == nodes.indexOf(startNode) 
                            && edge.getStart() == nodes.indexOf(connectedNode)) {
                        result.append("- From ").append(connectedCity)
                              .append(" (Distance: ").append(edge.getWeight())
                              .append(")\n");
                    }
                }
            }
        }
        
        return result.toString();
    }
    
        
    }
