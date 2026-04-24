// Student ID: w2151908
// Name:       Pasindu Jayasinghe
// Module:     5SENG003W Algorithms – Coursework 2025/26
// File:       Main.java – entry point for the acyclicity checker

import java.util.*; // Import for List
import java.io.*;   // Import for IOException

public class Main { // Main class to run the program

    public static void main(String[] args) throws IOException { // Main execution method

        // Check if the user provided a file path as a command line argument
        if (args.length < 1) {
            System.out.println("Usage: java Main <inputfile>"); // Print usage instructions
            return; // Exit if no file was given
        }

        String path = args[0]; // Get the file path from the first argument

        // Task 3: Use GraphParser to read the graph from the input file
        SimpleDirectedGraph[] graphs = GraphParser.parse(path);
        SimpleDirectedGraph graph     = graphs[0]; // Main graph used for sink elimination
        SimpleDirectedGraph cycleCopy = graphs[1]; // Backup copy used for cycle detection

        // Print basic info about the loaded graph
        System.out.println("Graph loaded from: " + path);
        System.out.println("Vertices: " + graph.numVertices);
        System.out.println();

        // Print header for the sink elimination section
        System.out.println("=== Sink-elimination algorithm ===");
        System.out.println("Starting vertex count: " + graph.numVertices);

        // Task 4: Main loop – repeatedly find and remove sinks
        while (true) {

            // Try to find a sink (a vertex with no outgoing edges)
            int sink = graph.findSink();

            // If no sink is found, the graph either has a cycle or is empty
            if (sink == -1) {

                // Check if any vertices are still remaining
                if (graph.countRemaining() > 0) {

                    // Vertices remain but no sink – a cycle must exist
                    System.out.println("No sink found - graph contains a cycle.");
                    System.out.println("Result: NO (not acyclic)");
                    System.out.println();

                    // Task 5: Use DFS on the backup copy to find and print the cycle
                    System.out.println("=== Cycle detection (DFS) ===");
                    List<Integer> cycle = cycleCopy.findCycle(); // Find the cycle path

                    // Print the cycle vertices separated by arrows
                    System.out.print("Cycle found: ");
                    for (int i = 0; i < cycle.size(); i++) {
                        System.out.print(cycle.get(i) + (i < cycle.size() - 1 ? " -> " : ""));
                    }

                    // Print the final answer
                    System.out.println("\n\nAnswer: NO - the graph contains a cycle.");

                } else {

                    // No vertices remaining – the graph was fully emptied, so it is acyclic
                    System.out.println("Graph is now empty.");
                    System.out.println("Result: YES (acyclic)");
                    System.out.println("\nAnswer: YES - the graph is acyclic.");
                }

                break; // Exit the loop – algorithm is complete
            }

            // Remove the found sink from the graph
            graph.removeVertex(sink);

            // Print progress showing which sink was removed and how many vertices remain
            System.out.println("Removing sink: " + sink +
                    "  (vertices remaining after removal: " + graph.countRemaining() + ")");
        }
    }
}
