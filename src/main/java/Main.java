import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main extends JFrame {

    public Main(){
        setTitle("JavaSnake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(345,365);//задает размер окна
        setLocation(600,200);//задает размер окна
        add(new GameField());//сылка на класс
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mw = new Main();
        GameField game_field = new GameField();


            }
        }


