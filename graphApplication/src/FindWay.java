import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class FindWay extends JPanel {
    private JButton startButton;
    private GraphPanel graphPanel;
    private JLabel resultLabel;

    public FindWay(GraphPanel theGraphPanel, JLabel theResultLabel) {
        this.graphPanel = theGraphPanel;  // Reference to the GraphPanel
        this.resultLabel = theResultLabel;  // Reference to the resultLabel

        // Create the button
        startButton = new JButton("Find Out Path Between Nodes");

        // Add ActionListener to the button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the input dialog for node selection
                String source = JOptionPane.showInputDialog(null, "Enter source node (e.g., Carlow):");
                String destination = JOptionPane.showInputDialog(null, "Enter destination node (e.g., Waterford):");

                // Check if input is valid
                if (source != null && destination != null) {
                    int startIndex = -1, endIndex = -1;

                    // Use the dynamic list of nodes from GraphPanel
                    java.util.List<Node> nodes = graphPanel.getNodes(); // Access nodes from GraphPanel

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).getLabel().equalsIgnoreCase(source)) {
                            startIndex = i;
                        }
                        if (nodes.get(i).getLabel().equalsIgnoreCase(destination)) {
                            endIndex = i;
                        }
                    }

                    // If valid nodes are selected, calculate shortest path
                    if (startIndex != -1 && endIndex != -1) {
                        String result = App.calculateShortestPath(graphPanel,startIndex, endIndex);
                        resultLabel.setText(result);  // Display the result
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid node selection. Please try again.");
                    }
                }
            }
        });

        // Add the button to this panel
        // Set the layout to GridBagLayout for more control over components' sizes
        this.add(startButton);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Ensure the button will take up all available space
        gbc.fill = GridBagConstraints.BOTH;  // Make the button fill the space both horizontally and vertically
        gbc.weightx = 1.0;  // Allow the button to expand horizontally
        gbc.weighty = 1.0;  // Allow the button to expand vertically

        // Add the button to the panel with the constraints
        this.add(startButton, gbc);

        // Set the preferred size for the panel
        this.setPreferredSize(new Dimension(200, 30));
    }
}
