// Class to represent a node
class Node {
    private String label; // Node label (e.g., "A")
    private int x, y; // Node position

    public Node(String theLabel, int theX, int theY) {
        label = theLabel;
        x = theX;
        y = theY;
    }
    //Setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    //Getters
    public String getLabel() {
        return label;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}