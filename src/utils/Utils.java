package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static BufferedImage resize(BufferedImage image, int w, int h){
        BufferedImage scaledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        scaledImage.getGraphics().drawImage(image, 0, 0, w, h, null);
        return scaledImage;
    }

    public static int[][] levelParser(String filePath){
        int[][] result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            List<int[]> lvlLines = new ArrayList<>();
            while ((line = reader.readLine()) != null){
                lvlLines.add(strToInt_arrays(line.split(" ")));
            }

            result = new int[lvlLines.size()][lvlLines.get(0).length];
            for (int i = 0; i < lvlLines.size(); i++) {
                result[i] = lvlLines.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


    public static int[] strToInt_arrays(String[] sArr){
        int[] result = new int[sArr.length];

        for (int i = 0; i < sArr.length; i++) {
            result[i] = Integer.parseInt(sArr[i]);
        }

        return result;
    }

    public static BufferedImage mirrorEntity(BufferedImage image){
        BufferedImage mirrored = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = (Graphics2D)mirrored.getGraphics();
        AffineTransform transform = new AffineTransform();
        transform.setToScale(-1, 1);
        transform.translate(-image.getWidth(), 0);
        graphics.setTransform(transform);
        graphics.drawImage(image, 0, 0, null);
        return mirrored;
    }


}
