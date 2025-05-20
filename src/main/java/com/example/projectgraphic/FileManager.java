package com.example.projectgraphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private static FileManager fileManagerInstance;
    public static FileManager getFileManagerInstance(){
        if(fileManagerInstance==null){
            fileManagerInstance= new FileManager();
        }
        return fileManagerInstance;
    }
    public static void setFileManagerInstance(FileManager a){
        fileManagerInstance=a;
    }

    public static int[][] getMapMatrix(){
        File file = new File("F:/Projects/Intellij IDEA/ProjectGraphic/src/main/java/com/example/projectgraphic/mapNode.txt");
        int[][] MapMatrix = new int[134][134];
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            ArrayList<String> MapS = new ArrayList<>();
            while (scanner.hasNextLine()){
                MapS.add(scanner.nextLine().trim());
            }
            MapS.remove(0);

            for(int i=0; i<134; i++){
                for(int j=0; j<134; j++){
                    MapMatrix[i][j] = 0;
                }
            }
            for(int i=0; i<169; i++){
                String[] parts = MapS.get(i).split("\\s");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int length = Integer.parseInt(parts[2]);
                MapMatrix[x][y] = length;
                MapMatrix[y][x] = length;
            }
            return MapMatrix;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[] getMapX(){
        File file = new File("F:/Projects/Intellij IDEA/ProjectGraphic/src/main/java/com/example/projectgraphic/map.txt");
        double[] MapMatrixX = new double[133];
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            ArrayList<String> MapS = new ArrayList<>();
            while (scanner.hasNextLine()){
                MapS.add(scanner.nextLine().trim());
            }
            MapS.remove(0);

            for(int i=0; i<133; i++){
                MapMatrixX[i] = 0;
            }
            for(int i=0; i<133; i++){
                String[] parts = MapS.get(i).split("\\s");
                double length = Double.parseDouble(parts[1]);
                MapMatrixX[i] = length;
            }
            return MapMatrixX;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static double[] getMapY(){
        File file = new File("F:/Projects/Intellij IDEA/Maptest/src/main/java/com/example/maptest/map.txt");
        double[] MapMatrixY = new double[133];
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            ArrayList<String> MapS = new ArrayList<>();
            while (scanner.hasNextLine()){
                MapS.add(scanner.nextLine().trim());
            }
            MapS.remove(0);

            for(int i=0; i<133; i++){
                    MapMatrixY[i] = 0;
            }
            for(int i=0; i<133; i++){
                String[] parts = MapS.get(i).split("\\s");
                double length = Double.parseDouble(parts[2]);
                MapMatrixY[i] = length;
            }
            return MapMatrixY;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
