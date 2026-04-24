import java.util.*; // Import utilities like List and LinkedList

public class SimpleDirectedGraph { // Define the graph class
    int numVertices; // Variable to store total number of vertices
    LinkedList<Integer>[] adj; // Array of LinkedLists to store edges

    public SimpleDirectedGraph(int n) { // Constructor to initialize the graph
        this.numVertices = n; // Set the vertex count
        adj = new LinkedList[n]; // Create the array for the adjacency list
        for (int i = 0; i < n; i++) { // Loop through each index in the array
            adj[i] = new LinkedList<>(); // Create an empty list for each vertex
        }
    }

    public void addEdge(int u, int v) { // Method to add a directed edge from u to v
        adj[u].add(v); // Add vertex v to the list of neighbors for vertex u
    }

    public int findSink() { // Task 4: Find a vertex with no outgoing edges
        for (int i = 0; i < numVertices; i++) { // Loop through all possible vertices
            if (adj[i] != null && adj[i].isEmpty()) { // If vertex exists and its list is empty
                return i; // Found a sink, return its ID
            }
        }
        return -1; // No sink found in the current graph
    }

    public void removeVertex(int v) { // Task 2/4: Remove a vertex and its edges
        adj[v] = null; // Mark vertex v as deleted by setting its list to null
        for (int i = 0; i < numVertices; i++) { // Look at all other vertices
            if (adj[i] != null) { // If the vertex hasn't been deleted yet
                adj[i].remove(Integer.valueOf(v)); // Remove any edges pointing to v
            }
        }
    }

    public int countRemaining() { // Helper to count how many vertices are still active
        int count = 0; // Initialize counter
        for (int i = 0; i < numVertices; i++) { // Loop through the array
            if (adj[i] != null) count++; // Increment if the vertex isn't null
        }
        return count; // Return total remaining vertices
    }

    public List<Integer> findCycle() { // Task 5: Find a cycle using Depth First Search
        boolean[] visited = new boolean[numVertices]; // Track vertices fully processed
        for (int i = 0; i < numVertices; i++) { // Ensure we check every part of the graph
            LinkedList<Integer> path = new LinkedList<>(); // Create a list to track current path
            if (adj[i] != null && dfs(i, visited, path)) return path; // Start DFS; return if cycle found
        }
        return null; // Return null if no cycle exists
    }

    private boolean dfs(int u, boolean[] visited, List<Integer> path) { // Recursive DFS function
        if (path.contains(u)) { // If current vertex is already in the path
            path.add(u); // Add it one last time to show the closed loop
            return true; // Cycle detected!
        }
        if (visited[u]) return false; // If already fully visited with no cycle, skip it

        visited[u] = true; // Mark vertex as visited
        path.add(u); // Add current vertex to the path trail

        for (int neighbor : adj[u]) { // Check all neighbors of the current vertex
            if (dfs(neighbor, visited, path)) return true; // Recurse; if cycle found, pass it up
        }

        path.removeLast(); // Backtrack: remove vertex from path as we go back up
        return false; // No cycle found in this branch
    }
    public boolean nodeExist(int v){
        return v>= 0 && v<numVertices && adj[v] != null;
    }
}