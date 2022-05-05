import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;//размер окна
    private final int DOT_SIZE = 25;//размер ячеек
    private final int ALL_DOTS = 196;//количество яблок
    private Image dot;
    private Image apple;
    private static Image Restart;
    private static Image game_over;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];//хранть положение змейки
    private int[] y = new int[ALL_DOTS];
    private int dots;///размер змеи
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame;
    private static int score = 0;


    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        timer = new Timer(250, this);
        timer.start();
    }


    public void initGame() {//начальное полочение игры
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }
        createApple();
        inGame = true;
    }

    public void createApple() {//создает яблоки
        appleX = new Random().nextInt(13) * DOT_SIZE;
        appleY = new Random().nextInt(13) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.clipRect(0, 0, getWidth(), getHeight());
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {

            ImageIcon iid = new ImageIcon("src/main/resources/Restart.png");
            Restart = iid.getImage();
            ImageIcon iia = new ImageIcon("src/main/resources/game_over.png");
            game_over = iia.getImage();
            g.drawImage(game_over, 40, 0, this);
            score = dots - 3;

            JLabel record = new JLabel("Рекорд" + score);
            record.setSize(100, 20);
            record.setPreferredSize(new Dimension(100, 35));
            record.setFont(new Font("Рекорд: " + score, Font.PLAIN, 19));
            record.setOpaque(true);
            record.setBackground(Color.white);
            record.setText("Рекорд: " + score);
            add(record);


            JLabel label = new JLabel(iid);
            label.setSize(100, 100);
            label.setLocation(100, 200);
            add(label);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    initGame();
                }
            });
        }
    }


    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {//удлинять змейку
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }


    public void checkCollisions() {//проверяет на столкновение
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE || y[0] < 0 || x[0] > SIZE || x[0] < 0) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {//управление

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }


}
