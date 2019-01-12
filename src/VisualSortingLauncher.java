import javax.swing.*;

public class VisualSortingLauncher {

    public static void main(String[] args){
        int width = 500;
        int height = 500;

        JFrame frame = new JFrame("Visual sorting");
        VisualSorting game = new VisualSorting(width, height);
        frame.setVisible(true);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        game.run();
    }
}
