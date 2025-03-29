package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class Main {
    private static File file;


    public static void main(String[] args) {
        try {
            System.out.println("Ждем чтения с базы...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\GADn");
        file = new File("D:\\YO_NA\\BPExcel\\ADn.csv");
        makeOPKML("GADn");

        cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\ReADn");
        file = new File("D:\\YO_NA\\BPExcel\\READn.csv");
        makeOPKML("ReADn");

        cleanDirectory("D:\\YO_NA\\BP_KML\\BP\\Minometu");
        file = new File("D:\\YO_NA\\BPExcel\\Minometu.csv");
        makeOPKML("Minometu");
    }

    private static void cleanDirectory(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            Logger.getLogger(Main.class.getName()).info(e.getMessage());
        }
    }
}