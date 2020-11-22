package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void WGraph_DS() {
        weighted_graph g_Test1 = new WGraph_DS();
        assertEquals(0, g_Test1.nodeSize());
        assertEquals(0, g_Test1.edgeSize());
        assertEquals(0, g_Test1.getMC());
    }

    @Test
    void getNode() {
        weighted_graph g_Test2 = new WGraph_DS();
        g_Test2.addNode(0);
        assertEquals(1, g_Test2.nodeSize());
        assertEquals(0, g_Test2.edgeSize());
        assertEquals(1, g_Test2.getMC());
        assertNotNull(g_Test2.getNode(0));
        assertNull(g_Test2.getNode(1));
    }

    @Test
    void hasEdge() {
        weighted_graph g_Test3 = new WGraph_DS();
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
        assertTrue(g_Test3.hasEdge(1, 4));
        assertTrue(g_Test3.hasEdge(2, 0));
        assertFalse(g_Test3.hasEdge(3, 0));
        assertFalse(g_Test3.hasEdge(6, 7));
        assertFalse(g_Test3.hasEdge(6, 4));
        assertFalse(g_Test3.hasEdge(4, 7));
        assertFalse(g_Test3.hasEdge(1, 1));
    }

    @Test
    void getEdge() {
        weighted_graph g_Test4 = new WGraph_DS();
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
        assertEquals(5, g_Test4.getEdge(1, 4));
        assertEquals(1, g_Test4.getEdge(2, 0));
        assertEquals(-1, g_Test4.getEdge(3, 0));
        assertEquals(-1, g_Test4.getEdge(6, 7));
        assertEquals(-1, g_Test4.getEdge(6, 4));
        assertEquals(-1, g_Test4.getEdge(4, 7));
        assertEquals(-1, g_Test4.getEdge(1, 1));
    }

    @Test
    void addNode() {
        weighted_graph g_Test5 = new WGraph_DS();
        assertEquals(0, g_Test5.nodeSize());
        assertEquals(0, g_Test5.getMC());
        g_Test5.addNode(0);
        g_Test5.addNode(1);
        assertEquals(2, g_Test5.nodeSize());
        assertEquals(2, g_Test5.getMC());
        g_Test5.addNode(0);
        assertEquals(2, g_Test5.nodeSize());
        assertEquals(2, g_Test5.getMC());
    }

    @Test
    void connect() {
        weighted_graph g_Test6 = new WGraph_DS();
        assertEquals(0, g_Test6.edgeSize());
        assertEquals(0, g_Test6.getMC());
        g_Test6.addNode(0);
        g_Test6.addNode(1);
        g_Test6.addNode(2);
        g_Test6.addNode(3);
        g_Test6.addNode(4);
        g_Test6.connect(0, 1, 6);
        g_Test6.connect(0, 2, 1);
        g_Test6.connect(1, 2, 2);
        g_Test6.connect(1, 3, 2);
        g_Test6.connect(1, 4, 5);
        g_Test6.connect(2, 3, 1);
        g_Test6.connect(3, 4, 5);
        assertEquals(7, g_Test6.edgeSize());
        assertEquals(12, g_Test6.getMC());
        assertEquals(6, g_Test6.getEdge(0,1));
        g_Test6.connect(0, 4, 3); // Connects 2 nodes which there is no edge between them.
        assertEquals(8, g_Test6.edgeSize());
        assertEquals(13, g_Test6.getMC());
        assertEquals(3, g_Test6.getEdge(0,4));
        g_Test6.connect(6, 7, 6); // Tries to connect 2 nodes which don't exists in the graph.
        g_Test6.connect(6, 4, 5); // Tries to connect 2 nodes which only the first exists in the graph.
        g_Test6.connect(4, 7, 4); // Tries to connect 2 nodes which only the second exists in the graph.
        g_Test6.connect(2, 4, -5); // Tries to connect 2 nodes with weight < 0.
        g_Test6.connect(1, 1, 5); // Tries to connect a node to itself.
        assertEquals(8, g_Test6.edgeSize()); // The connections failed as expected.
        assertEquals(13, g_Test6.getMC());
        g_Test6.connect(0, 1, 5); // Only updates the weight because the edge is already exists.
        assertEquals(8, g_Test6.edgeSize());
        assertEquals(14, g_Test6.getMC());
        assertEquals(5, g_Test6.getEdge(0,1)); // Updated.
    }

    @Test
    void getV() {
        weighted_graph g_Test7 = new WGraph_DS();
        g_Test7.addNode(0);
        g_Test7.addNode(1);
        g_Test7.addNode(2);
        g_Test7.addNode(3);
        g_Test7.addNode(4);
        int colNodeSize = 0;
        Iterator<node_info> it = g_Test7.getV().iterator();
        while(it.hasNext()) {
            node_info n = it.next();
            assertNotNull(n);
            colNodeSize++;
        }
        assertEquals(colNodeSize, g_Test7.nodeSize());
    }

    @Test
    void testGetV() {
        weighted_graph g_Test8 = new WGraph_DS();
        g_Test8.addNode(0);
        g_Test8.addNode(1);
        g_Test8.addNode(2);
        g_Test8.addNode(3);
        g_Test8.addNode(4);
        g_Test8.connect(0, 1, 6);
        g_Test8.connect(0, 2, 1);
        g_Test8.connect(0, 3, 2);
        g_Test8.connect(0, 4, 5);
        int colNeiSize = 0;
        Iterator<node_info> it = g_Test8.getV(0).iterator();
        while(it.hasNext()) {
            node_info n = it.next();
            assertNotNull(n);
            colNeiSize++;
        }
        assertEquals(4, colNeiSize);
    }

    @Test
    void removeNode() {
        weighted_graph g_Test9 = new WGraph_DS();
        g_Test9.addNode(0);
        g_Test9.addNode(1);
        g_Test9.addNode(2);
        g_Test9.addNode(3);
        g_Test9.addNode(4);
        g_Test9.connect(0, 1, 6);
        g_Test9.connect(0, 2, 1);
        g_Test9.connect(1, 2, 2);
        g_Test9.connect(1, 3, 2);
        g_Test9.connect(1, 4, 5);
        g_Test9.connect(2, 3, 1);
        g_Test9.connect(3, 4, 5);
        assertEquals(5, g_Test9.nodeSize());
        assertEquals(12, g_Test9.getMC());
        g_Test9.removeNode(0);
        assertEquals(4, g_Test9.nodeSize());
        assertEquals(15, g_Test9.getMC()); // The increase of MC also caused because of edges deletion.
        assertNull(g_Test9.removeNode(0)); // Tries to remove node which no longer exists in the graph.
        assertEquals(4, g_Test9.nodeSize());
        assertEquals(15, g_Test9.getMC());
    }

    @Test
    void removeEdge() {
        weighted_graph g_Test10 = new WGraph_DS();
        g_Test10.addNode(0);
        g_Test10.addNode(1);
        g_Test10.addNode(2);
        g_Test10.addNode(3);
        g_Test10.addNode(4);
        g_Test10.connect(0, 1, 6);
        g_Test10.connect(0, 2, 1);
        g_Test10.connect(1, 2, 2);
        g_Test10.connect(1, 3, 2);
        g_Test10.connect(1, 4, 5);
        g_Test10.connect(2, 3, 1);
        g_Test10.connect(3, 4, 5);
        assertEquals(7, g_Test10.edgeSize());
        assertEquals(12, g_Test10.getMC());
        g_Test10.removeEdge(1, 4); // Removes the edge between this 2 nodes which has an edge between them.
        assertEquals(6, g_Test10.edgeSize());
        assertEquals(13, g_Test10.getMC());
        g_Test10.removeEdge(1, 4); // Tries to remove a non-existent edge.
        g_Test10.removeEdge(6, 7); // Tries to remove an edge between non-existent nodes.
        g_Test10.removeEdge(6, 4); // Tries to remove an edge between 2 nodes which the first is not exists in the graph.
        g_Test10.removeEdge(4, 7); // Tries to remove an edge between 2 nodes which the second is not exists in the graph.
        assertEquals(6, g_Test10.edgeSize()); // The removals failed as expected.
        assertEquals(13, g_Test10.getMC());
    }
}