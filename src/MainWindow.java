import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 640);
        add(new GameField());
        setVisible(true);
        setLocation(440, 220);
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }

}
