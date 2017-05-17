package basicgraph;

import java.util.*;

/**
 * A class that implements a directed graph.
 * The graph may have self-loops, parallel edges.
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via adjacency lists.
 *
 * @author UCSD MOOC development team and YOU
 */
public class GraphAdjList extends Graph {
    private Map<Integer, ArrayList<Integer>> adjListsMap;

    /**
     * Create a new empty Graph
     */
    public GraphAdjList() {
        adjListsMap = new HashMap<Integer, ArrayList<Integer>>();
    }

    /**
     * Implement the abstract method for adding a vertex.
     */
    public void implementAddVertex() {
        int v = getNumVertices();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        adjListsMap.put(v, neighbors);
    }

    /**
     * Implement the abstract method for adding an edge.
     *
     * @param v the index of the start point for the edge.
     * @param w the index of the end point for the edge.
     */
    public void implementAddEdge(int v, int w) {
        (adjListsMap.get(v)).add(w);
    }

    /**
     * Implement the abstract method for finding all
     * out-neighbors of a vertex.
     * If there are multiple edges between the vertex
     * and one of its out-neighbors, this neighbor
     * appears once in the list for each of these edges.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getNeighbors(int v) {
        return new ArrayList<Integer>(adjListsMap.get(v));
    }

    /**
     * Implement the abstract method for finding all
     * in-neighbors of a vertex.
     * If there are multiple edges from another vertex
     * to this one, the neighbor
     * appears once in the list for each of these edges.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getInNeighbors(int v) {
        List<Integer> inNeighbors = new ArrayList<Integer>();
        for (int u : adjListsMap.keySet()) {
            //iterate through all edges in u's adjacency list and
            //add u to the inNeighbor list of v whenever an edge
            //with startpoint u has endpoint v.
            for (int w : adjListsMap.get(u)) {
                if (v == w) {
                    inNeighbors.add(u);
                }
            }
        }
        return inNeighbors;
    }


    /**
     * Implement the abstract method for finding all
     * vertices reachable by two hops from v.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getDistance2(int v) {
        LinkedList<Object> q = new LinkedList() {{
            add(v);
            add(new Object());
        }};
        return new ArrayList<>(new LinkedHashSet(bfs(q, 2)));
    }

    private List<Integer> bfs(LinkedList<Object> q, int depth) {
        if (depth <= 0) {
            List<Integer> ls = new ArrayList<>();
            for (Object obj : q)
                if (obj instanceof Integer)
                    ls.add((Integer) obj);
            return ls;
        }
        LinkedList<Object> copyQ = new LinkedList<>(q);
        for (Object obj : copyQ) {
            q.removeFirst();
            if (obj instanceof Integer)
                q.addAll(getNeighbors((Integer) obj));
            else break;
        }
        q.add(new Object());
        return bfs(q, depth - 1);
    }

    /**
     * Generate string representation of adjacency list
     *
     * @return the String
     */
    public String adjacencyString() {
        String s = "Adjacency list";
        s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

        for (int v : adjListsMap.keySet()) {
            s += "\n\t" + v + ": ";
            for (int w : adjListsMap.get(v)) {
                s += w + ", ";
            }
        }
        return s;
    }

    public static void main(String[] args) {
        GraphAdjList adjList = new GraphAdjList();
        adjList.addVertex();
        adjList.addVertex();
        adjList.addVertex();
        adjList.addEdge(1, 2);
        List<Integer> ls = adjList.getDistance2(0);
        System.out.println();
    }
}
