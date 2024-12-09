// Class to represent an edge
class Edge {
    private int start, end; // Indices of the start and end nodes
    private int weight;
    private String weightText;

    public Edge(int theStart, int theEnd) {
        start = theStart;
        end = theEnd;
    }
    public Edge(int theStart, int theEnd, int theWeight) {
        start = theStart;
        end = theEnd;
        weight = theWeight;
        weightText = Integer.toString(theWeight);
    }
    //Setters
    //Getters
    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
    public int getWeight() {
        return weight;
    }
    public String getWeightText() {
        return String.valueOf(weight);
    }
}