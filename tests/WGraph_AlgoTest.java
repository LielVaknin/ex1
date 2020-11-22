package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void copy() {
        weighted_graph g_Test1 = new WGraph_DS();
        weighted_graph_algorithms ga_Test1 = new WGraph_Algo();
        g_Test1.addNode(0);
        g_Test1.addNode(1);
        g_Test1.addNode(2);
        g_Test1.addNode(3);
        g_Test1.addNode(4);
        g_Test1.connect(0, 1, 6);
        g_Test1.connect(0, 2, 1);
        g_Test1.connect(1, 2, 2);
        g_Test1.connect(1, 3, 2);
        g_Test1.connect(1, 4, 5);
        g_Test1.connect(2, 3, 1);
        g_Test1.connect(3, 4, 5);
        ga_Test1.init(g_Test1);
        weighted_graph ga_copy1 = ga_Test1.copy();
        assertEquals(g_Test1, ga_copy1);
        System.out.println(g_Test1);
        System.out.println(ga_copy1);
        g_Test1.removeNode(3);
        assertNotEquals(g_Test1, ga_copy1);
        System.out.println(g_Test1);
        System.out.println(ga_copy1);
    }

    @Test
    void isConnected() {
        weighted_graph g_Test2 = new WGraph_DS();
        weighted_graph_algorithms ga_Test2 = new WGraph_Algo();
        g_Test2.addNode(0);
        g_Test2.addNode(1);
        g_Test2.addNode(2);
        g_Test2.addNode(3);
        g_Test2.addNode(4);
        g_Test2.connect(0, 1, 6);
        g_Test2.connect(0, 2, 1);
        g_Test2.connect(1, 2, 2);
        g_Test2.connect(1, 3, 2);
        g_Test2.connect(1, 4, 5);
        g_Test2.connect(2, 3, 1);
        g_Test2.connect(3, 4, 5);
        ga_Test2.init(g_Test2);
        assertTrue(ga_Test2.isConnected());
        g_Test2.removeNode(1);
        g_Test2.removeNode(3);
        assertFalse(ga_Test2.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g_Test3 = new WGraph_DS();
        weighted_graph_algorithms ga_Test3 = new WGraph_Algo();
        g_Test3.addNode(0);
        g_Test3.addNode(1);
        g_Test3.addNode(2);
        g_Test3.addNode(3);
        g_Test3.addNode(4);
        g_Test3.connect(0, 1, 6);
        g_Test3.connect(0, 2, 1);
        g_Test3.connect(1, 2, 2);
        g_Test3.connect(1, 3, 2);
        g_Test3.connect(1, 4, 5);
        g_Test3.connect(2, 3, 1);
        g_Test3.connect(3, 4, 5);
        ga_Test3.init(g_Test3);
        assertEquals(7, ga_Test3.shortestPathDist(0, 4));
        assertEquals(-1, ga_Test3.shortestPathDist(6, 10)); // Tries to find the length of shortest path between 2 non-existent nodes.
        assertEquals(-1, ga_Test3.shortestPathDist(6, 4)); // Tries to find the length of shortest path between 2 nodes which the first is not exists in the graph.
        assertEquals(-1, ga_Test3.shortestPathDist(4, 10)); // Tries to find the length of shortest path between 2 nodes which the second is not exists in the graph.
        assertEquals(0, ga_Test3.shortestPathDist(1, 1)); // The length of shortest path from a node to itself is 0.

        g_Test3.removeNode(1);
        g_Test3.removeNode(3);
        assertEquals(Double.POSITIVE_INFINITY, ga_Test3.shortestPathDist(0, 4)); // The length is infinity because 4 is not connected to the graph.
    }

    @Test
    void shortestPath() {
        weighted_graph g_Test4 = new WGraph_DS();
        weighted_graph_algorithms ga_Test4 = new WGraph_Algo();
        g_Test4.addNode(0);
        g_Test4.addNode(1);
        g_Test4.addNode(2);
        g_Test4.addNode(3);
        g_Test4.addNode(4);
        g_Test4.connect(0, 1, 6);
        g_Test4.connect(0, 2, 1);
        g_Test4.connect(1, 2, 2);
        g_Test4.connect(1, 3, 2);
        g_Test4.connect(1, 4, 5);
        g_Test4.connect(2, 3, 1);
        g_Test4.connect(3, 4, 5);
        ga_Test4.init(g_Test4);
        LinkedList <node_info> path_Test = (LinkedList<node_info>) ga_Test4.shortestPath(0, 4);
        assertEquals(0, path_Test.get(0).getKey());
        assertEquals(2, path_Test.get(1).getKey());
        assertEquals(3, path_Test.get(2).getKey());
        assertEquals(4, path_Test.get(3).getKey());
        System.out.println(ga_Test4.shortestPath(0, 4));

        assertNull(ga_Test4.shortestPath(6, 10)); // Tries to find the shortest path between 2 non-existent nodes.
        assertNull(ga_Test4.shortestPath(6, 4)); // Tries to find the shortest path between 2 nodes which the first is not exists in the graph.
        assertNull(ga_Test4.shortestPath(4, 10)); // Tries to find the shortest path between 2 nodes which the second is not exists in the graph.
        path_Test = (LinkedList<node_info>) ga_Test4.shortestPath(1, 1); // src == node so might return a List with one node.
        assertEquals(1, path_Test.get(0).getKey());
        assertEquals(1, path_Test.size());
        System.out.println(ga_Test4.shortestPath(1, 1));

        g_Test4.removeNode(1);
        g_Test4.removeNode(3);
        path_Test = (LinkedList<node_info>) ga_Test4.shortestPath(0, 4);
        assertNull(path_Test); // 4 is not connected to the graph so there is no such path.
        System.out.println(ga_Test4.shortestPath(0, 4));
    }

    @Test
    void saveAndLoad() {
        weighted_graph g_Test5 = new WGraph_DS();
        weighted_graph_algorithms ga_Test5 = new WGraph_Algo();
        g_Test5.addNode(0);
        g_Test5.addNode(1);
        g_Test5.addNode(2);
        g_Test5.addNode(3);
        g_Test5.addNode(4);
        g_Test5.connect(0, 1, 6);
        g_Test5.connect(0, 2, 1);
        g_Test5.connect(1, 2, 2);
        g_Test5.connect(1, 3, 2);
        g_Test5.connect(1, 4, 5);
        g_Test5.connect(2, 3, 1);
        g_Test5.connect(3, 4, 5);
        ga_Test5.init(g_Test5);
        ga_Test5.save("SaveAndLoad_Test");
        weighted_graph ga_copy5 = ga_Test5.copy();
        ga_Test5.load("SaveAndLoad_Test");
        assertEquals(g_Test5, ga_copy5);
        g_Test5.removeNode(0);
        assertNotEquals(g_Test5, ga_copy5);
    }
}