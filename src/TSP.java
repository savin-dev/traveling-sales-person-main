import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP {

    public static void getCityOrder(Graph graph, Node source) {
        List<Node> visitedCities = new ArrayList<>();
        visitedCities.add(source);

        Node selectedCity = source;
        double path = 0;
        while (visitedCities.size() != graph.getNodes().size()) {
            Dijkstra.calculateShortestPathFromSource(selectedCity,graph);

            List<Node> sortedCityList = sortByNearestCity(graph, selectedCity);

            Node next_city = null;
            for (Node a : sortedCityList) {
                if (!visitedCities.contains(a)) {
                    next_city = a;
                    break;
                }
            }
            path = path + next_city.getDistance();
            visitedCities.add(next_city);
            selectedCity = next_city;

            graph.getNodes().forEach(node -> node.setDistance(Double.MAX_VALUE));
        }

        // To travel back to start
        visitedCities.add(source);

        Node last_city = null;
        System.out.println("Visiting Order: ");
        for (Node visited_city : visitedCities) {
            System.out.println(visited_city.getCityName());
            if (visited_city != source)
                last_city = visited_city;
        }

        Double value = last_city.getAdjacentNodes().get(source);
        path = path + value;

        System.out.println("Distance: " + path);
    }

    public static List<Node> sortByNearestCity(Graph graph, Node source) {
        List<Node> cityList = new ArrayList<>(graph.getNodes());
        cityList.remove(source);
        Collections.sort(cityList);
        return cityList;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();

        File f = new File("cities.txt");
        System.out.println("Opening file: "+f.getAbsolutePath());
        BufferedReader reader=new BufferedReader(new FileReader(f));

        final Node[] firstNode = new Node[1];

        reader.lines()
                .map(s -> {
                    String[] splitStr=s.split("  ");
                    Node n=new Node(
                            splitStr[0],
                            Integer.parseInt(splitStr[1]),
                            Integer.parseInt(splitStr[2])
                    );
                    if (firstNode[0]==null)
                        firstNode[0] = n;
                    return n;
                })
                .forEach(graph::addNode);

        graph.buildGraph();

        getCityOrder(graph, firstNode[0]);
    }

}
