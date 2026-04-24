// Student ID: w2151908
// Name:       Pasindu Jayasinghe
// Module:     5SENG003W Algorithms – Coursework 2025/26
// File:       GraphParser.java – reads a graph from an input file

import java.util.*; // Import for Scanner
import java.io.*;   // Import for File and IOExceptionchrom

public class GraphParser { // Task 3: Dedicated parser class for reading graph input files

    // Static method that reads a file and returns two populated SimpleDirectedGraph objects
    // Returns an array: [0] = main graph for sink elimination, [1] = copy for cycle detection
    public static SimpleDirectedGraph[] parse(String path) throws IOException {

        File file = new File(path); // Create a File object from the given path
        Scanner sc = new Scanner(file); // Open the file with a Scanner for reading

        int n = sc.nextInt(); // Read the first line – the total number of vertices

        // Create the main graph used for sink elimination
        SimpleDirectedGraph graph = new SimpleDirectedGraph(n);

        // Create a second identical copy kept for cycle detection after sink elimination destroys the first
        SimpleDirectedGraph cycleCopy = new SimpleDirectedGraph(n);

        // Read edge pairs line by line until end of file
        while (sc.hasNextInt()) { // Keep reading as long as there are integers left
            int u = sc.nextInt(); // Read the source vertex of the edge
            int v = sc.nextInt(); // Read the destination vertex of the edge
            graph.addEdge(u, v);     // Add the directed edge to the main graph
            cycleCopy.addEdge(u, v); // Add the same edge to the cycle-detection copy
        }

        sc.close(); // Close the scanner to release the file resource

        // Return both graphs together in an array
        return new SimpleDirectedGraph[]{graph, cycleCopy};
    }
}
