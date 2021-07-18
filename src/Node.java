import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Node implements Comparable{

    private String cityName;

    //Used to store the position of the node
    private static class Position{
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getDistanceTo(Position position){
            return BigDecimal.valueOf(x-position.getX()).pow(2)
                    .add(BigDecimal.valueOf(y-position.getY()).pow(2))
                    .sqrt(new MathContext(3))
                    .doubleValue();
        }
    }

    private Position position;

    private double distance = Double.MAX_VALUE;

    private List<Node> shortestPath = new LinkedList<>();

    /*Hold the distance from this node to adjacentNodes*/
    Map<Node, Double> adjacentNodes = new HashMap<>();

    public Node(String cityName) {
        this.cityName = cityName;
    }

    public Node(String cityName, Position position) {
        this.cityName = cityName;
        this.position = position;
    }

    public Node(String cityName, int posX, int posY){
        this.cityName = cityName;
        this.position = new Position(posX, posY);
    }

    public String getCityName() {
        return cityName;
    }

    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Position getPosition() {
        return position;
    }

    /*Sets the position of the node*/
    public void setPosition(Position position) {
        this.position = position;
    }

    /*Gets the distance from this node to the given node*/
    public double getDistanceTo(Node node){
        return position.getDistanceTo(node.getPosition());
    }

    public void print_node() {
        System.out.println(getCityName() + " " + getDistance());
        for (Map.Entry<Node, Double> adjacencyPair : getAdjacentNodes().entrySet()) {
            Node adjacentNode = adjacencyPair.getKey();
            double edgeWeight = adjacencyPair.getValue();
            System.out.println(adjacentNode.getCityName() + " " + edgeWeight);
        }
        System.out.println("***");
    }

    public void clean() {
        List<Node> new_path = new LinkedList<>();
        setShortestPath(new_path);
        setDistance(Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return cityName.equals(node.cityName) &&
                position.equals(node.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, position);
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(distance,((Node) o).distance);
    }


}