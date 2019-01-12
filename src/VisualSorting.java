import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VisualSorting extends JPanel implements Runnable {

    private int width;
    private int height;
    private int[] array = new int[250];
    private int redBar1;
    private int redBar2;
    private int temp = 0;
    private int barStroke;
    private double barHeightMultiplier;

    public VisualSorting(int width, int height) {
        this.width = width;
        this.height = height;
        barHeightMultiplier = height / array.length;
        barStroke = width / array.length;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        array = shuffleArray(array);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //background
        g2d.translate(0, 0);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, width, height);
        //draw array
        g2d.translate(0, height);
        g2d.setStroke(new BasicStroke(barStroke));
        for (int i = 0; i < array.length; i++) {
            g2d.setColor(new Color(255, 255, 255));
            if (i == redBar1 || i == redBar2) {
                g2d.setColor(Color.red);
            }
            g2d.drawLine(i * barStroke + (barStroke / 2), 0, i * barStroke + (barStroke / 2), (int) (-barHeightMultiplier * array[i]));
        }
        redBar1 = -1;
        redBar2 = -1;
    }

    public void run() {
        long timeStarted = System.currentTimeMillis();
        bubbleSort();
//        insertionSort();
//        mergeSort(0, array.length - 1);
//        bogoSort();
        System.out.println((System.currentTimeMillis() - timeStarted) / 1000.0 + " seconds");
        redBar1 = -1;
        redBar2 = -1;
        update();
    }

    public void update() {
        repaint();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
    }

    public void bogoSort() {
        int index = 0;
        while (!checkArray()) {
            if (index % 10000 == 0) {
                update();
            }
            array = shuffleArray(array);
            index++;
        }
    }

    public boolean checkArray() {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void mergeSort(int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(l, m);
            mergeSort(m + 1, r);
            merge(l, m, r);
        }
    }

    public void merge(int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = array[l + i];
        for (int j = 0; j < n2; ++j) {
            R[j] = array[m + 1 + j];
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
                redBar1 = k;
                update();
            } else {
                array[k] = R[j];
                j++;
                redBar1 = k;
                update();
            }
            k++;
        }
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
            redBar1 = k;
            update();
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
            redBar1 = k;
            update();
        }
    }

    public void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    redBar1 = j;
                    redBar2 = j + 1;
                    update();
                }
            }
        }
    }

    public void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            temp = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
                redBar1 = j;
                update();
            }
            array[j + 1] = temp;
            redBar2 = j + 1;
            update();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public static int[] shuffleArray(int[] arr) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
        return arr;
    }
}
