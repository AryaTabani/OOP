package com.example.projectgraphic;

class Node implements Comparable<Node> {
    public int id, dist;

    public Node(int id, int dist) {
        this.id = id;
        this.dist = dist;
    }

    public int compareTo(Node other) {
        return Integer.compare(dist, other.dist);
    }
}