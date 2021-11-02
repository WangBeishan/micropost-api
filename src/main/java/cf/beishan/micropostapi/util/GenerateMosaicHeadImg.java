package cf.beishan.micropostapi.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateMosaicHeadImg {
//    背景顏色
    private final static Color BACK_GROUND_COLOR = new Color(238, 238, 238);
//    圖片寬
    private final static int IMG_WIDTH = 360;
//    圖片高
    private final static int IMG_HEIGHT = 360;
//    圖片邊緣內邊距
    private final static int PADDING = 30;
//    填充比率，越接近1，有色色塊出現頻率越高
    private final static double RADIO = 0.45;
//    每邊矩形數量，建議 >= 5
   private final static int BLOCK_NUM = 11;
//    顏色差值評價值（越大顏色越鮮豔）
    private final static int COLOR_DIFF_EVALUATION = 10;
//    基色階數極限
    private final static int COLOR_LIMIT = 256;

    private final static String dir = "/home/wbs/Program_Project/Vue/micropost-app/src/assets/HeadImg/";

    public static String generator(String email) throws IOException {

        BufferedImage bi = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) bi.getGraphics();
        graphics2D.setColor(BACK_GROUND_COLOR);
        graphics2D.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        Color color = getRandomColor();
        List<Point> points = getRandomPointList(RADIO);
        fillGraph(graphics2D, points, color);

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageIO.write(bi, "jpg", new File(dir + email + ".jpg"));
        String path = dir + email + ".jpg";
        return path;
    }

    /**
     * 填充圖形
     * @param graphics2D    畫筆
     * @param pointList     填充塊座標
     * @param mainColor     填充顏色
     */
    private static void fillGraph(Graphics2D graphics2D, List<Point> pointList, Color mainColor) {
        int rowBlockLength = (IMG_WIDTH - 2 * PADDING) / BLOCK_NUM;
        int colBlockLength = (IMG_HEIGHT - 2 * PADDING) / BLOCK_NUM;
        graphics2D.setColor(mainColor);
        for (Point point : pointList) {
            graphics2D.fillRect(
                    PADDING + point.x * rowBlockLength,
                    PADDING + point.y * colBlockLength,
                    rowBlockLength, colBlockLength);
        }
    }

    /**
     * 獲取隨機顏色位置列表
     * @param radio     填充色塊機率
     * @return
     */
    private static List<Point> getRandomPointList(double radio) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < BLOCK_NUM / 2; i++) {
            for (int j = 0; j < BLOCK_NUM /2; j++) {
                if (Math.random() < radio) {
                    points.add(new Point(i, j));
                }
            }
        }
        addReversePoints(points);
        if (BLOCK_NUM % 2 == 1) {
            for (int i = 0; i < BLOCK_NUM; i++) {
                if (Math.random() < radio) {
                    points.add(new Point(BLOCK_NUM / 2, i));
                }
            }
        }
        return points;
    }

    /**
     * 隨機生成顏色
     * @return
     */
    private static Color getRandomColor() {
        int r, g, b;
        do {
            r = new Random().nextInt(COLOR_LIMIT);
            g = new Random().nextInt(COLOR_LIMIT);
            b = new Random().nextInt(COLOR_LIMIT);
        } while (evaluateColor(r, g, b));
        return new Color(r, g, b);
    }

    /**
     * 評價顏色品質，只需任意兩種顏色差值大於某個規定值即可
     * @return
     */
    private static boolean evaluateColor(int r, int g, int b) {
        int rg = Math.abs(r - g);
        int rb = Math.abs(r - b);
        int gb = Math.abs(g - b);
        int max = rg > rb ? (rg > gb ? rg : gb) : (rb > gb ? rb : gb);
        return max < COLOR_DIFF_EVALUATION;
    }

    /**
     * 添加對稱座標
     * @param points
     */
    private static void addReversePoints(List<Point> points) {
        ArrayList<Point> pointListCopy = new ArrayList<>(points);
        for (Point point : pointListCopy) {
            points.add(new Point((BLOCK_NUM - 1 - point.x), point.y));
        }
    }

    /**
     * 封裝了座標的內部類
     */
    private static class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
