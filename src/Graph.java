import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    private List<Node> visited = new ArrayList<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isVisited(Node node) {
        return visited.contains(node);
    }

    public void visited(Node node) {
        visited.add(node);
    }

    /**
     * Builds graph with matching adjcent nodes and weighting edges
     */
    public void buildGraph() {
        for (Node node : nodes) {
            for (Node node1 : nodes) {
                // to not add current node as it's node
                if (node1 != node) {
                    Double distance = node1.getAdjacentNodes().get(node);

                    // To avoid recalculation of edge if it was previously calculated
                    if (distance == null)
                        node.addDestination(node1, node.getDistanceTo(node1));
                    else
                        node.addDestination(node1, distance);
                }
            }
        }
    }

}