package ex1.src;

import java.io.*;
import java.util.*;

/**
 * This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_info> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author Liel.Vaknin
 *
 */
public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph g;

    /**
     * This method initializes the graph with the set of algorithms operates on.
     *
     * @param g represents the given graph.
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * Returns the underlying graph of which this class works.
     *
     * @return g.
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Computes a deep copy of this graph.
     *
     * @return copied graph.
     */
    @Override
    public weighted_graph copy() {
        weighted_graph g_deepCopy = new WGraph_DS();
        Iterator<node_info> it1 = g.getV().iterator(); // Iterates over all nodes in graph g and adds them to a new graph.
        while (it1.hasNext()) {
            node_info n1 = it1.next();
            g_deepCopy.addNode(n1.getKey());
        }
        Iterator<node_info> it2 = g.getV().iterator(); // For each node - iterates over all its neighbors and connects an edge between a neighbor to the node itself.
        while (it2.hasNext()) {
            node_info n2 = it2.next();
            Iterator<node_info> it3 = g.getV(n2.getKey()).iterator();
            while (it3.hasNext()) {
                node_info n3 = it3.next();
                g_deepCopy.connect(n2.getKey(), n3.getKey(), g.getEdge(n2.getKey(), n3.getKey()));
            }
        }
        return g_deepCopy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from every node to each
     * other node (assume undirectional graph).
     *
     * @return true if this graph is connected, false if not.
     */
    @Override
    public boolean isConnected() {
        int temporaryKey = 0;
        if (g == null || g.getV().size() < 2)
            return true;
        Iterator<node_info> it1 = g.getV().iterator(); // Search for a source node to be the start point in the bfs method.
        while (it1.hasNext()) {
            node_info n = it1.next();
            temporaryKey = n.getKey();
        }
        bfs(g.getNode(temporaryKey)); // Calls the bfs method with the source node we found.
        Iterator<node_info> it2 = g.getV().iterator();
        while (it2.hasNext()) {
            node_info n = it2.next();
            if (n.getInfo().equals("white")) // If at least one of the node's info is "white" it means we didn't reached to this node in the bfs method so the graph is not connected.
                return false;
        }
        return true; // All the nodes are "black" - it means we reached to all the nodes in the bfs method so the graph is connected.
    }

    /**
     * This method uses the BFS (Breadth-first search) algorithm,
     * private method which helps with the implementation of isConnected method in WGraph_Algo class.
     * Uses "info" field for representing colors : "white", "grey" and "black" -
     * each color represents a different status of a node.
     *
     * @param src represents the source node - the start point.
     */
    private void bfs(node_info src) {
        Iterator<node_info> it1 = g.getV().iterator();
        while (it1.hasNext()) {
            node_info n = it1.next();
            n.setInfo("white");
        }
        Queue<node_info> q = new LinkedList<>();
        src.setInfo("grey");
        q.add(src);
        while (!q.isEmpty()) {
            node_info n = q.poll();
            n.setInfo("black");
            Iterator<node_info> it2 = g.getV(n.getKey()).iterator();
            while (it2.hasNext()) {
                node_info node = it2.next();
                if (node.getInfo().equals("white")) {
                    node.setInfo("grey");
                    q.add(node);
                }
            }
        }
    }

    /**
     * Returns the length of the shortest path between src to dest,
     * if there is no such path --> returns -1, using Dijkstra's algorithm.
     *
     * @param src represents the starting point.
     * @param dest represents the ending point.
     * @return length of shortest path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (!g.getV().contains(g.getNode(src)) || !g.getV().contains(g.getNode(dest)))
            return -1;
        if (src == dest)
            return 0;
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new WGraph_DS.nodeComparator());
        Iterator<node_info> it1 = g.getV().iterator();
        while (it1.hasNext()) { // Iterates all nodes in the graph and initializes initial values for tag and info fields for each one of them.
            node_info n1 = it1.next();
            n1.setTag(Double.POSITIVE_INFINITY); // Tag represents for each node the length of shortest path from src to the node itself by the weight of edges in the graph.
            n1.setInfo("unVisited"); // Info represents for each node if visited or unvisited.
        }
        node_info srcNode = g.getNode(src);
        srcNode.setTag(0); // The length of shortest path from src node to itself is 0.
        q.add(srcNode);
        while (!q.isEmpty()) {
            node_info smallest = q.poll();
            Iterator<node_info> it3 = g.getV(smallest.getKey()).iterator();
            while (it3.hasNext()) {
                node_info n3 = it3.next();
                if (n3.getInfo().equals("unVisited")) {
                    double weight = g.getEdge(smallest.getKey(), n3.getKey());
                    if ((smallest.getTag() + weight) < n3.getTag()) {
                        n3.setTag((smallest.getTag() + weight));
                        if (!q.contains(n3))
                            q.add(n3);
                    }
                }
            }
            smallest.setInfo("visited");
            q.remove(smallest);

        }
        return g.getNode(dest).getTag();
    }

    /**
     * Returns the shortest path between src to dest - as an ordered List of nodes:
     * src --> n1 --> n2 -->...dest,
     * if no such path --> returns null.
     * Using Dijkstra's algorithm (shortestPathDist).
     *
     * @param src represents the starting point.
     * @param dest represents the ending point.
     * @return the shortest path as an ordered list of nodes.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (!g.getV().contains(g.getNode(src)) || !g.getV().contains(g.getNode(dest)))
            return null;
        this.shortestPathDist(src, dest);
        if(g.getNode(dest).getTag() == Double.POSITIVE_INFINITY) // Dest node is not connected to the graph so there is no such path.
            return null;
        LinkedList<node_info> path = new LinkedList<>();
        if (src == dest) {
            path.addFirst(g.getNode(dest));
            return path;
        } else {
            node_info n = g.getNode(dest);
            path.addFirst(n);
            while (n.getTag() > 0) {
                Iterator<node_info> it = g.getV(n.getKey()).iterator();
                while (it.hasNext()) {
                    node_info n1 = it.next();
                    if (Double.compare(n1.getTag() + g.getEdge(n.getKey(), n1.getKey()), n.getTag()) == 0) {
                        n = n1;
                        path.addFirst(n);
                        break;
                    }
                }
            }
        }
        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name.
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved.
     */
    @Override
    public boolean save(String file){
        try {
            System.out.println("Starting serialization");
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(myFile);
            oos.writeObject(this.g);
            oos.close();
            myFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Ending serialization");
        return true;
    }

    /**
     * Loads a graph to this graph algorithm.
     * If the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name.
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file){
      try {
          System.out.println("Starting deserialization");
          FileInputStream myFile = new FileInputStream(file);
          ObjectInputStream ois = new ObjectInputStream(myFile);
          this.g = (WGraph_DS) ois.readObject();
          ois.close();
          myFile.close();
      } catch (Exception e){
          e.printStackTrace();
      }
        System.out.println("Ending deserialization");
       return true;
    }
}
