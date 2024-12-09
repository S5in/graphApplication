import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonPanel extends JPanel {
    private JButton startButton;
    private GraphPanel graphPanel;
    private JLabel resultLabel;

    public StartButtonPanel(GraphPanel theGraphPanel, JLabel theResultLabel) {
        this.graphPanel = theGraphPanel;  // Store reference to the GraphPanel
        this.resultLabel = theResultLabel;  // Store reference to the resultLabel

        // Create the button
        startButton = new JButton("Okay, now press this button to start");

        // Add ActionListener to the button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the input dialog for node selection
                String source = JOptionPane.showInputDialog(null, "Enter source node (e.g., A):");
                String destination = JOptionPane.showInputDialog(null, "Enter destination node (e.g., B):");

                // Check if input is valid (we expect nodes A, B, C, D, etc.)
                if (source != null && destination != null) {
                    int startIndex = -1, endIndex = -1;

                    // Map node labels to indices (assuming nodes are labeled A, B, C, ...)
                    String[] nodes = {"A", "B", "C", "D", "E", "F", "G"};

                    for (int i = 0; i < nodes.length; i++) {
                        if (nodes[i].equalsIgnoreCase(source)) {
                            startIndex = i;
                        }
                        if (nodes[i].equalsIgnoreCase(destination)) {
                            endIndex = i;
                        }
                    }

                    // If valid nodes are selected, calculate shortest path
                    if (startIndex != -1 && endIndex != -1) {
                        String result = App.calculateShortestPath(startIndex, endIndex);
                        resultLabel.setText(result);  // Display the result
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid node selection. Please try again.");
                    }
                }
            }
        });

        // Add the button to this panel
        this.add(startButton);
    }
}
