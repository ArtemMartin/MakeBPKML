package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class Main {
    private static File file;


    public static void main(String[] args) {
        try {
            System.out.println("Ждем чтения с базы...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            myLogger(e.getMessage());
        }

        try {
            cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\GADn");
            file = new File("D:\\YO_NA\\BPExcel\\ADn.csv");
            makeOPKML("GADn");

            cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\ReADn");
            file = new File("D:\\YO_NA\\BPExcel\\READn.csv");
            makeOPKML("ReADn");

            cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\Minometu");
            file = new File("D:\\YO_NA\\BPExcel\\Minometu.csv");
            makeOPKML("Minometu");
        } catch (Exception e) {
            myLogger(e.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                myLogger(ex.getMessage());
            }
        }
        bingo("Успешно!!!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            myLogger(e.getMessage());
        }
    }

    private static void bingo(String x) {
        System.out.println(x);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            myLogger(ex.getMessage());
        }
    }

    private static void myLogger(String e) {
        Logger.getLogger(Main.class.getName()).info(e);
    }

    private static void cleanDirectory(String dirName) {
        Path pathToDelete = Paths.get(dirName);
        try {
            Files.walkFileTree(pathToDelete, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) throw exc;
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            myLogger(ex.getMessage());
        }
    }

    private static void makeOPKML(String papka) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            String[] mas;
            while ((line = reader.readLine()) != null) {
                line = line.replace("\"", "");
                mas = line.split(",");
                new MakeKML(papka + "\\" + mas[0], new OP(mas[0], Double.parseDouble(mas[1])
                        , Double.parseDouble(mas[2])));
            }

        } catch (IOException e) {
            myLogger(e.getMessage());
        }
    }
}