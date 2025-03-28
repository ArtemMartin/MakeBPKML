package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Main {
    private static File file;

    public static void main(String[] args) {
        file = new File("D:\\YO_NA\\BPExcel\\ADn.csv");
        makeOPKML("GADn");

        file = new File("D:\\YO_NA\\BPExcel\\READn.csv");
        makeOPKML("ReADn");

        file = new File("D:\\YO_NA\\BPExcel\\Minometu.csv");
        makeOPKML("Minometu");
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