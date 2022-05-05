import javax.swing.*;


public class Main extends JFrame {

    public Main(){
        setTitle("JavaSnake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(365,450);
        setLocation(500,300);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mw = new Main();
    }

    public static int getColor(int a, int r, int g, int b) {
        return ((a * 256 + r) * 256 + g) * 256 + b;
    }
}
