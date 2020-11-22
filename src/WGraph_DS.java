package ex1.src;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents an undirectional weighted graph.
 *
 * @author Liel.Vaknin
 */
public class WGraph_DS implements weighted_graph, Serializable {

    private int MC;
    private int nodeSize;
    private int edgeSize;

    private HashMap<Integer, node_info> nodes;
    private HashMap<Integer, HashMap<Integer, Double>> neighbors; // Represents the neighbors for each node by its key and each neighbor contains the weight of the edge between the neighbor and the node itself.

    /**
     * Default constructor.
     */
    public WGraph_DS() {
        this.MC = 0;
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.nodes = new HashMap<>();
        this.neighbors = new HashMap<>();
    }

    /**
     * Private class which represents a node in a graph.
     */
    private class NodeInfo implements node_info, Serializable {

        private int key;
        private String info;
        private double tag;

        /**
         * Default constructor.
         */
        public NodeInfo(int key) {
            this.key = key;
            this.info = "";
            this.tag = 0;
        }

        /**
         * Copy constructor - Builds a new node from a given node.
         *
         * @param node represents the given node.
         */
        public NodeInfo(node_info node) {
            this.key = node.getKey();
            this.info = node.getInfo();
            this.tag = node.getTag();
        }

        /**
         * Returns the key (id) associated with this node
         * (each node_info should have a unique key).
         *
         * @return key.
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * Returns the remark (meta data) associated with this node.
         *
         * @return info.
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s represents the given info.
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data which can be used by algorithms.
         *
         * @return tag.
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Allows setting the "tag" value of a node.
         *
         * @param t represents the new value of a tag.
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * ToString method.
         *
         * @return String which represents the key of this node.
         */
        @Override
        public String toString() {
            return "" + this.key;
        }

        /**
         * Equals method.
         *
         * @param o represents a given object.
         * @return true if this object and a given object are equals, false if not.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }

        /**
         * HashCode method.
         *
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hash(key, info, tag);
        }
    }

    /**
     * Returns the node_info by the node_id.
     *
     * @param key represents the node_id.
     * @return the node_info by the given node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * Returns true iff (if and only if) there is an edge between node1 and node2.
     *
     * @param node1 represents the key of the first node.
     * @param node2 represents the key of the second node.
     * @return true if there is an edge between the 2 given nodes, false if none.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!this.nodes.containsKey(node1) || !this.nodes.containsKey(node2) || node1 == node2)
            return false;
        return this.neighbors.get(node1).containsKey(node2);
    }

    /**
     * Returns the weight if there is an edge between node1 and node2.
     * In case there is no such edge - should return -1.
     *
     * @param node1 represents the key of the first node.
     * @param node2 represents the key of the second node.
     * @return the weight of the edge between the 2 given nodes, else -1.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!this.nodes.containsKey(node1) || !this.nodes.containsKey(node2) || node1 == node2)
            return -1;
        if (hasEdge(node1, node2)) {
            return this.neighbors.get(node1).get(node2);
        } else {
            return -1;
        }
    }

    /**
     * Adds a new node to the graph with a given key.
     * If there is already a node with this given key -> no action should be performed.
     *
     * @param key represents a given key for adding a new node.
     */
    @Override
    public void addNode(int key) {
        if (!this.nodes.containsKey(key)) {
            node_info n = new NodeInfo(key);
            this.nodes.put(key, n);
            HashMap<Integer, Double> innerHashMap1 = new HashMap<>();
            neighbors.put(key, innerHashMap1);
            MC++;
            nodeSize++;
        }
    }

    /**
     * Connects an edge between node1 and node2, with an edge with weight >=0.
     * If the edge between node1 and node2 is already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (!this.nodes.containsKey(node1) || !this.nodes.containsKey(node2) || node1 == node2 || w < 0)
            return;
        if(!hasEdge(node1, node2)) {
            this.neighbors.get(node1).put(node2, w);
            this.neighbors.get(node2).put(node1, w);
            edgeSize++;
            MC++;
        } else if (hasEdge(node1, node2) && w != neighbors.get(node1).get(node2)){
            this.neighbors.get(node1).replace(node2, w);
            this.neighbors.get(node2).replace(node1, w);
            MC++;
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     *
     * @return Collection<node_info> nodes.
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * This method returns a collection containing all the
     * nodes connected to node_id.
     *
     * @return Collection<node_info> neighbors.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        HashMap<Integer, node_info> neighbors = new HashMap<>();
        Iterator it = this.neighbors.get(node_id).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry mapElement = (Map.Entry)it.next();
            if(mapElement != null)
           neighbors.put((Integer)mapElement.getKey(), nodes.get(mapElement.getKey()));
        }
        return neighbors.values();
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *
     * @param key represents the node_id.
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!this.nodes.containsKey(key))
            return null;
        Iterator<node_info> it = this.getV(key).iterator();
        while(it.hasNext()){
            node_info neighbor = it.next();
            removeEdge(key, neighbor.getKey());
        }
        node_info n = nodes.get(key);
        this.nodes.remove(key);
        nodeSize--;
        MC++;
        return n;
    }

    /**
     * Deletes the edge between node1 and node2 from the graph.
     *
     * @param node1 represents the key of the first node.
     * @param node2 represents the key of the second node.
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!this.nodes.containsKey(node1) || !this.nodes.containsKey(node2))
            return;
        if (hasEdge(node1, node2)) {
            this.neighbors.get(node1).remove(node2);
            this.neighbors.get(node2).remove(node1);
            edgeSize--;
            MC++;
        }
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return nodeSize.
     */
    @Override
    public int nodeSize() {
        return this.nodeSize;
    }

    /**
     * Returns the number of edges in the graph (assume undirectional graph).
     *
     * @return edgeSize.
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph
     * (Any change in the inner state of the graph should cause an increment in the ModeCount).
     *
     * @return MC.
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * ToString method.
     *
     * @return String which represents all the nodes in this graph by their keys and the neighbors for each one of them.
     */
    @Override
    public String toString() {
        String s = "";
        Iterator<node_info> it1 = this.getV().iterator();
        while (it1.hasNext()) {
            node_info n = it1.next();
            s = s + n.toString() + " --> ";
            s = s + this.getV(n.getKey()).toString() + ", ";
        }
        return s;
    }

    /**
     * Equals method.
     *
     * @param o represents a given object.
     * @return true if this object and a given object are equals, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return nodeSize == wGraph_ds.nodeSize &&
                edgeSize == wGraph_ds.edgeSize &&
                Objects.equals(nodes, wGraph_ds.nodes) &&
                Objects.equals(neighbors, wGraph_ds.neighbors);
    }

    /**
     * HashCode method.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(nodeSize, edgeSize, nodes, neighbors);
    }

    /**
     * Class which implements the Comparator<T> interface,
     * used for PriorityQueue in ShortestPathDist method in WGraph_Algo class.
     */
    static class nodeComparator implements Comparator<node_info>{

        /**
         * Overriding compare() method of Comparator.
         *
         * @return
         */
        @Override
        public int compare(node_info o1, node_info o2) {
            return Double.compare(o1.getTag(), o2.getTag());
            }
    }
}
