import javax.swing.*;
import java.awt.*;

public class App {
    private static JLabel resultLabel;
    public static void main(String[] args) {
        // Create the main frame (window)
        JFrame frame = new JFrame("Graph application");//Giving name for the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);

        // Create the result label to display the shortest path
        resultLabel = new JLabel("Shortest path will appear here.");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GraphPanel graphPanel = new GraphPanel();
        FindWay buttonPanel = new FindWay(graphPanel, resultLabel);
        // Create the "Search (site)" button and associated functionality
        JButton searchButton = new JButton("Search (site)");
        searchButton.addActionListener(e -> graphPanel.showSearchSiteDialog());

        // Create the 'Add Node' button and set it up
        JButton addNodeButton = new JButton("Add Node");
        addNodeButton.addActionListener(e -> graphPanel.showAddNodeDialog());
        // Create the 'Add Edge' button and set it up
        JButton addEdgeButton = new JButton("Add Edge");
        addEdgeButton.addActionListener(e -> graphPanel.showAddEdgeDialog());
        JButton findClosestCityButton = new JButton("Find Closest City");
        findClosestCityButton.addActionListener(e -> {
            String cityLabel = JOptionPane.showInputDialog("Enter the city name:");
            if (cityLabel == null || cityLabel.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "City name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                // Call the method to find the closest city
            String result = graphPanel.findClosestCity(cityLabel.trim());
            JOptionPane.showMessageDialog(frame, result, "Closest City", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton connectedCitiesButton = new JButton("Show Connected Cities");
        connectedCitiesButton.addActionListener(e -> graphPanel.showConnectedCitiesDialog());
        // Set preferred size for consistency
        addEdgeButton.setPreferredSize(new Dimension(120, 40));

        // Set layout and add panels to the frame
        frame.setLayout(new BorderLayout());
        JPanel buttonBigPanel = new JPanel();
        buttonBigPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Center-align with spacing
        addNodeButton.setPreferredSize(new Dimension(150, 30));
        buttonBigPanel.add(addNodeButton);
        addEdgeButton.setPreferredSize(new Dimension(150, 30));
        buttonBigPanel.add(addEdgeButton);
        buttonBigPanel.add(findClosestCityButton);
        findClosestCityButton.setPreferredSize(new Dimension(150, 30));
        buttonBigPanel.add(buttonPanel);
        connectedCitiesButton.setPreferredSize(new Dimension(200, 30));
        buttonBigPanel.add(connectedCitiesButton);
        searchButton.setPreferredSize(new Dimension(150, 30));
        buttonBigPanel.add(searchButton);
        //
        frame.add(graphPanel, BorderLayout.CENTER);
        frame.add(buttonBigPanel,BorderLayout.NORTH);
        frame.add(resultLabel, BorderLayout.SOUTH); // Label at the top for result
        frame.setVisible(true);
    }
    public static String calculateShortestPath(GraphPanel graphPanel, int startIndex, int endIndex) {
        // Call Dijkstra's algorithm
        return Dijkstra.calculate(graphPanel, startIndex, endIndex);
    }
}    
