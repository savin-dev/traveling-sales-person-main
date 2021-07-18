import java.util.*;

public class Dijkstra {
    public static void calculateShortestPathFromSource(Node source, Graph graph) {
        source.setDistance(0);

        /* Nodes that have been visited */
        Set<Node> settledNodes = new HashSet<>();

        /* Nodes that are being considered to visit next */
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getNearestNode(unsettledNodes);

            // remove from unsettled because we now traversed to it
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Double> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                double edgeWeight = adjacencyPair.getValue();
                if ((!settledNodes.contains(adjacentNode)) && (!graph.isVisited(adjacentNode))) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
//            System.out.println(source.getCityName()+" "+currentNode.getCityName()+" "+currentNode.getDistance() );
//
//            List<Node> shortestPath = currentNode.getShortestPath();
//            //System.out.println(shortestPath.get(0));
//            String str = "";
//            for(Node node : shortestPath){
//                //System.out.println(node.getCityName());
//                str = str + " " + node.getCityName();
//            }
//
//            System.out.println(str);

        }

        //System.out.println("--------------------------");
    }

    /**
     * Selects the nearest node that can be visited. This method picks the
     * first found nearest node if there are two nodes with the shortest distance.
     * */
    private static Node getNearestNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node: unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Updates the distance to the total distance currently traversed till this point.
     * */
    private static void calculateMinimumDistance(Node evaluationNode, double edgeWeigh, Node sourceNode) {
        double sourceDistance = sourceNode.getDistance();

        // checks if we have reached the evaluation node prior to this attempt and if it has a shorter distance
        if ((sourceDistance + edgeWeigh) < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);

            // Retrieve the shortest path till the source node
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);

            // Updates the evaluationNode with the shortest possible path to the evaluation Node
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
