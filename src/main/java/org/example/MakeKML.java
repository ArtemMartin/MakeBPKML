package org.example;

import org.osgeo.proj4j.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MakeKML {
    private final File filePath;

    public MakeKML(String fileName, OP op) {
        this.filePath = new File("D:\\YO_NA\\BP_KML\\BP\\" + fileName + ".kml");
        generate(op);
    }

    public static List<Double> refactorXYtoBL(OP op) {
        double x = op.getX();
        double y = op.getY();

        List<Double> list = new ArrayList();

        // Создаем исходную и целевую системы координат
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:28407");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:4326");

        // Создаем объект для преобразования координат
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(srcCRS, dstCRS);

        // Преобразуем координаты
        //сначала вводим долготу потом широту
        ProjCoordinate srcCoord = new ProjCoordinate(y, x);
        ProjCoordinate dstCoord = new ProjCoordinate();
        transform.transform(srcCoord, dstCoord);

        // Выводим результат(наоборот x->y)
        list.add(dstCoord.y);
        list.add(dstCoord.x);
        return list;
    }

    public void generate(OP op) {
        List<Double> bl = refactorXYtoBL(op);
        double b = bl.get(0);
        double l = bl.get(1);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
                    + "  <Document>\n"
                    + "    <Placemark>\n");
            writer.write("<name>" + op.getName() + "</name>\n");
            writer.write("<Style>\n"
                    + "        <LabelStyle>\n"
                    + "          <color>FF00FFFF</color>\n"
                    + "          <scale>1.36363636363636</scale>\n"
                    + "        </LabelStyle>\n"
                    + "        <IconStyle>\n"
                    + "          <scale>0.625</scale>\n"
                    + "          <hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n"
                    + "        </IconStyle>\n"
                    + "      </Style>\n"
                    + "      <Point>\n"
                    + "        <extrude>1</extrude>\n");
            writer.write("<coordinates>" + l + "," + b + "," + 0 + "</coordinates>\n");
            writer.write(" </Point>\n"
                    + "  </Placemark>\n"
                    + " </Document>\n"
                    + "</kml>");
        } catch (IOException e) {
            System.out.println("Шляпа: " + MakeKML.class.getName() + e.getMessage());
        }
    }
}
