# Acyclicity-checker-algorithm
# Acyclicity Checker

## Overview

This program determines whether a directed graph is **acyclic (a DAG)** or **contains a cycle**, using the **sink-elimination algorithm**. If a cycle is found, it also identifies and prints the cycle path using a **Depth First Search (DFS)**.

---

## Project Structure

```
acyclicity_source/
├── src/
│   ├── Main.java                  # Entry point – runs the algorithm
│   ├── SimpleDirectedGraph.java   # Graph data structure + algorithm logic
│   └── GraphParser.java           # Reads graph input from a file
├── .idea/
│   ├── misc.xml
│   ├── modules.xml
│   └── workspace.xml
└── acyclicity_source.iml
```

---

## How It Works

### 1. Graph Representation
The graph is stored as an **adjacency list** using an array of `LinkedList<Integer>` objects. Each index represents a vertex, and its list holds all outgoing neighbors. A vertex is marked as deleted by setting its list to `null`.

| Operation        | Complexity | Description                              |
|------------------|------------|------------------------------------------|
| `addEdge(u, v)`  | O(1)       | Appends v to adj[u]                      |
| `findSink()`     | O(V)       | Scans all lists for an empty one         |
| `removeVertex(v)`| O(V + E)   | Nulls adj[v], removes v from all lists   |
| `countRemaining()`| O(V)      | Counts non-null entries                  |
| `findCycle()`    | O(V + E)   | Recursive DFS with path tracking         |

---

### 2. Sink Elimination Algorithm
A **sink** is a vertex with no outgoing edges. The algorithm exploits the property that every finite acyclic graph must have at least one sink.

```
while true:
    sink = findSink()
    if sink == -1:
        if vertices remain  → CYCLE EXISTS (return NO)
        else                → GRAPH EMPTY  (return YES)
    removeVertex(sink)
```

**Three outcomes:**
- Sink found → remove it and repeat
- No sink, vertices remain → cycle exists ❌
- No sink, graph empty → graph is acyclic ✅

---

### 3. Cycle Detection (DFS)
When a cycle is detected, a backup copy of the original graph (preserved before sink elimination) is searched using a **recursive DFS**. The DFS tracks the current path and reports a cycle when it revisits a vertex already on that path.

---

## Input File Format

```
<number of vertices>
<u1> <v1>
<u2> <v2>
...
```

**Example (`graph.txt`):**
```
4
0 1
1 2
2 3
```

---

## How to Compile & Run

### Compile
```bash
javac Main.java SimpleDirectedGraph.java GraphParser.java
```

### Run
```bash
java Main <path-to-input-file>
```

**Example:**
```bash
java Main benchmarks/acyclic/a_40_0.txt
```

---

## Sample Output

### Acyclic Graph
```
Graph loaded from: a_40_0.txt
Vertices: 40

=== Sink-elimination algorithm ===
Starting vertex count: 40
Removing sink: 3  (vertices remaining after removal: 39)
Removing sink: 7  (vertices remaining after removal: 38)
...
Graph is now empty.
Result: YES (acyclic)

Answer: YES - the graph is acyclic.
```

### Cyclic Graph
```
Graph loaded from: c_40_0.txt
Vertices: 40

=== Sink-elimination algorithm ===
Starting vertex count: 40
Removing sink: 27  (vertices remaining after removal: 39)
...
No sink found - graph contains a cycle.
Result: NO (not acyclic)

=== Cycle detection (DFS) ===
Cycle found: 0 -> 6 -> 29 -> 4 -> 13 -> 32 -> 0

Answer: NO - the graph contains a cycle.
```

---

## Complexity Summary

| Aspect           | Complexity       | Notes                                              |
|------------------|------------------|----------------------------------------------------|
| Time (overall)   | O(V² + VE)       | Dominated by repeated findSink + removeVertex      |
| Time (sparse)    | O(V²)            | When E = O(V)                                      |
| Space            | O(V + E)         | Adjacency list stores each vertex and edge once    |
| DFS cycle detect | O(V + E)         | Runs once on the backup copy                       |

**Empirical doubling ratios** (averaged over 5 benchmark files per size):

| Vertices | Acyclic avg (ms) | Cyclic avg (ms) | Doubling Ratio |
|----------|------------------|-----------------|----------------|
| 40       | 0.11             | 0.06            | —              |
| 80       | 0.20             | 0.13            | 1.74           |
| 160      | 0.47             | 0.28            | 2.34           |
| 320      | 1.40             | 1.21            | 3.00           |
| 640      | 4.39             | 2.93            | 3.14           |

The ratio approaching **4** at larger sizes confirms **O(V²)** quadratic growth, consistent with theoretical analysis.

---

## Possible Improvement

The implementation could be improved to **O(V + E)** overall by maintaining a **queue of sinks** updated incrementally (similar to Kahn's topological sort algorithm), eliminating the repeated full scan in `findSink()`.

---

## Dependencies

- Java 8 or higher
- No external libraries required
