# Description
### Author : Liel Vaknin

This project implements algorithms for developing a data structure type of an undirected weighted graph.

---
 There are 3 classes in this project which each class implements a given interface.

## NodeInfo class
This class implements the node_info interface.
This internal class of WGraph_DS class, represents the set of operations applicable on a node in an undirected weighted graph.
There are several fields in this class:
 key (each node in the graph has a unique key), info and tag.
This class implements the methods:
Default constructor, copy constructor, set & get methods, equals and hashCode methods.

## WGraph_DS class 
This class implements the weighted_graph interface.
It represents an undirected weighted graph.
There are several fields in this class: 
MC (Mode Count - counts changes in the graph), nodeSize, edgeSize,
a list of all nodes in the graph and a list of nodes that each one contains its list of neighbors and each neighbor contains the weight of the edge between the neighbor and the node itself. 
Both lists which mentioned above implemented in a data structure - HashMap.
The first one implemented with a HashMap and the second with a HashMap in a HashMap.
The reason for using this data structure is because operations like adding / removing / searching executes at time complexity O(1).
This class implements the methods:
Default constructor, set & get methods, methods for adding / removing nodes and edges to / from the graph, a method for checking if there is an edge between 2 given nodes and a method for returning the weight of the edge between them if there is one. 

## WGraph_Algo class
This class implements the weighted_graph_algorithms interface.
It represents an Undirected (positive) Weighted Graph Theory algorithms.
There is 1 field in this class called g which its type is weighted_graph interface.
The class includes a set of operations applicable on the graph g - 
initialization of graph g with a given graph, a getGraph method, a deep copy method, 
a method which uses the [BFS algorithm](https://en.wikipedia.org/wiki/Breadth-first_search)  for the implementation of method which checks
if the graph is connected or not, finding the shortest path in the graph between a given source and destination and a method for finding its length - using  [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm), a method which saves this graph to a given file name and a method which loads a graph to this graph algorithm.

## Tests
It includes 2 JUNIT tests :

 -  WGraph_DSTest - for testing class WGraph_DS algorithms.
 -  WGraph_AlgoTest - for testing class WGraph_Algo algorithms. 
 
#### An example of an undirected weighted graph:

![An example of graph](https://github.com/LielVaknin/Java-OOP/blob/master/src/ex1/Images/Undirected%20weighted%20graph%20example.png?raw=true)
