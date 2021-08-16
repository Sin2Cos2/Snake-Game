import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private int SCORE;
    private JButton button;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean inGame;


    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        inGame = true;
        right = true;
        up = false;
        down = false;
        left = false;

        button = new JButton("Restart");
        button.setBounds(245, 340, 160, 40);

        dots = 3;
        SCORE = 0;
        for (int i = 0; i < dots; i++){
            x[i] = 96 - i * DOT_SIZE;
            y[i] = 96;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        boolean collision = true;

        while(collision){
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;

            for (int i = 0; i < dots; i++) {
                if(appleX != x[i] && appleY != y[i])
                    collision = false;
                if(appleX < 0 || appleY < 0 + DOT_SIZE)
                    collision = true;
                if(appleX > SIZE || appleY > SIZE)
                    collision = true;
            }
        }
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font f = new Font("Arial", Font.BOLD, 28);
        g.setColor(Color.white);
        g.setFont(f);

        if(inGame) {
            String currentScore = "Score: " + SCORE;
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            g.drawString(currentScore, 5, 30);
        } else {
            String endGame = "Game Over";
            String finalScore = "Your score: " + SCORE;
            g.drawString(endGame, 250, 270);
            g.drawString(finalScore, 240, 310);

            add(button);
            button.addActionListener(this);
            timer.stop();
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(right){
            x[0] += DOT_SIZE;
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down)
        {
            y[0] += DOT_SIZE;
        }

    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            SCORE += 10;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
        }

        if(x[0] + DOT_SIZE > SIZE || y[0] + DOT_SIZE > SIZE)
                inGame = false;
        if(x[0] < 0 || y[0] < 0)
                inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        } else
        {
            if(e.getSource() == button) {
                remove(button);
                initGame();
            }
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                left = false;
                right = false;
                down = true;
            }
        }
    }
}
