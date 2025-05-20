package com.example.projectgraphic;

import java.util.ArrayList;

public class Map {
    private static int MapMatrix[][] = FileManager.getMapMatrix();
    private static double MapMatrixX[] = FileManager.getMapX();
    public static double[] getMapMatrixX() {
        return MapMatrixX;
    }
    private static double MapMatrixY[] = FileManager.getMapY();

    public static double[] getMapMatrixY() {
        return MapMatrixY;
    }

    public static int[][] getMapMatrix() {
        return MapMatrix;
    }

    static final int V=134;
    public static int[] previousNode = new int[134];
    public static int[][] graphMatrix = new int[134][134];
    public static ArrayList<Integer> ShortestPath = new ArrayList<>();
    public static int minDistance(int distance[], Boolean sptSet[])
    {
        int min = Integer.MAX_VALUE, min_index=-1;
        for (int v = 0; v < V; v++){
            if (sptSet[v] == false && distance[v] <= min)
            {
                min = distance[v];
                min_index = v;
            }
        }
        return min_index;
    }
    public static void Path(int[] array, int src, int end){
        ShortestPath = new ArrayList<>();
        int i = end;
        ShortestPath.add(i);
        while(true){
            ShortestPath.add(array[i]);
            i = array[i];
            if(i==src){
                break;
            }
        }
    }
    public static ArrayList<Integer> printSolution(int distance[], int src, int End) {
        String returner="";
        returner+="The length of the shortest path is: "+distance[End]+"\nThe Path: ";
        Path(previousNode,src,End);
        for(int i = ShortestPath.size()-1; i>=0; i--){
            returner+=ShortestPath.get(i)+"";
            if(i!=0)
                returner +=">>";
        }
        System.out.println(returner);
        return ShortestPath;
    }
    public static int dijkstra(int start, int end) {
        int distance[] = new int[V];
        Boolean sptSet[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        distance[start] = 0;
        for (int count = 0; count < V-1; count++) {
            int u = minDistance(distance, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++){
                if (!sptSet[v] && MapMatrix[u][v]!=0 && distance[u] != Integer.MAX_VALUE && distance[u]+MapMatrix[u][v] < distance[v]){
                    distance[v] = distance[u] + MapMatrix[u][v];
                    previousNode[v]=u;
                }
            }
        }
        return distance[end];

    }
    public static ArrayList<Integer> dijkstraPrinter(int start, int end) {
        int distance[] = new int[V];
        Boolean sptSet[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        distance[start] = 0;
        for (int count = 0; count < V-1; count++) {
            int u = minDistance(distance, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++){
                if (!sptSet[v] && MapMatrix[u][v]!=0 && distance[u] != Integer.MAX_VALUE && distance[u]+MapMatrix[u][v] < distance[v]){
                    distance[v] = distance[u] + MapMatrix[u][v];
                    previousNode[v]=u;
                }
            }
        }

        return printSolution(distance, start, end);

    }



}
