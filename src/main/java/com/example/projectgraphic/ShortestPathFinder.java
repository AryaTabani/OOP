package com.example.projectgraphic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShortestPathFinder {
    private List<List<Edge>> graph;
    private int[] dist;
    private int[] prev;
    private PriorityQueue<Node> heap;

    public ShortestPathFinder(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] parts = br.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);

        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]) - 1;
            int v = Integer.parseInt(parts[1]) - 1;
            int w = Integer.parseInt(parts[2]);
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        br.close();
    }

    public int findShortestPath(int src, int dest) {
        int n = graph.size();

        dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        prev = new int[n];
        Arrays.fill(prev, -1);

        heap = new PriorityQueue<>();
        heap.add(new Node(src, 0));

        while (!heap.isEmpty()) {
            Node curr = heap.poll();
            if (curr.id == dest) break;

            for (Edge edge : graph.get(curr.id)) {
                int altDist = dist[curr.id] + edge.weight;
                if (altDist < dist[edge.to]) {
                    dist[edge.to] = altDist;
                    prev[edge.to] = curr.id;
                    heap.add(new Node(edge.to, altDist));
                }
            }
        }

        return dist[dest];
    }


    public List<Integer> getShortestPath(int src, int dest) {
        ArrayList<Integer> path = new ArrayList<>();
        int curr = dest;
        while (curr != src) {
            path.add(curr);
            curr = prev[curr];
        }
        path.add(src);
        Collections.reverse(path);
        return path;
    }

}