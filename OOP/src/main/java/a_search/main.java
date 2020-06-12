package a_search;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class main extends JPanel implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
    private Timer timer;
    private int index = 0;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 150;   
    
    JFrame frame;
    pathFinder pF;
    
    private Font arcadeFont;
    
    private boolean isReady;
  
    private List<Node> obstacles,parentL;
   
    private Node startCell, endCell;
    
    private Integer GRID_SIZE, FRAME_WIDTH, FRAME_LENGTH, NUM_OF_COL, NUM_OF_ROW;

    public static void main(String[] args) {
        new main();
    }

    private void initTimer() {
 
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }
    
    public main() {
        
        GRID_SIZE = 15;
        NUM_OF_COL = 61;
        NUM_OF_ROW = 61;
        FRAME_WIDTH = 916;
        FRAME_LENGTH = 916;
       
        isReady = false;
       

        pF = new pathFinder();
        obstacles = pF.getObstaclesList();
        parentL = pF.getParentList();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        frame = new JFrame();
        frame.setContentPane(this);
        frame.setTitle("A* Algorithm");
        frame.getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_LENGTH));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public int xInBorder(int X) {
        if (X < 0) {
            X = 0;
        } else {
            X = X + GRID_SIZE - X % GRID_SIZE;
        }
        return X;
    }
    public int yInBorder(int Y) {
        if (Y < 0) {
            Y = 0;
        } else {
            Y = Y + GRID_SIZE - Y % GRID_SIZE;
        }
        return Y;
    }

    public void createStartAndEndNode(MouseEvent e) {
        int x = e.getX() - GRID_SIZE;
        int y = e.getY() - GRID_SIZE;
        if (e.getButton() == 1 && isReady) {
            if (startCell == null) {
                x = xInBorder(x);
                y = yInBorder(y);
                startCell = new Node(x, y);
                System.out.println(startCell.getX() + " " + startCell.getY());
            } else if (startCell != null) {
                startCell = null;
            }
            repaint();
        }
        if (e.getButton() == 3 && isReady) {
            if (endCell == null) {
                x = xInBorder(x);
                y = yInBorder(y);
                endCell = new Node(x, y);
                System.out.println(endCell.getX() + " " + endCell.getY());
            } else if (endCell != null) {
                endCell = null;
            }
            repaint();
        }
    }

    public void createObstacles(MouseEvent e) {
        int x = e.getX() - GRID_SIZE;
        int y = e.getY() - GRID_SIZE;
        x = xInBorder(x);
        y = yInBorder(y);
        if (!isReady) {

            if (pF.isInList(pF.getObstaclesList(), new Node(x, y))) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    pF.removeObstacle(new Node(x, y));
                    repaint();
                }
            } else {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pF.addObstacle(new Node(x, y));
                    repaint();
                }
            }
        }
    }
    public void runPathFinder() {
        if (startCell != null && endCell != null) {
            pF.setStartCell(startCell);
            pF.setEndCell(endCell);
            pF.setCOL(FRAME_WIDTH);
            pF.setROW(FRAME_LENGTH);
            pF.setGRID_SIZE(GRID_SIZE);
            pF.start();
            pF.findNextPath();
            repaint();
        }
    }

    public void clearBoard() {
        endCell = null;
        startCell = null;
        isReady = false;
        pF = new pathFinder();
        pF.setComplete(false);
        pF.setObstaclesList(new ArrayList<>());
        obstacles = pF.getObstaclesList();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(47, 59, 79));
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        
        g.setColor(new Color(158, 143, 79));
       // g.drawString(index +"",GRID_SIZE ,GRID_SIZE);
       // pF.drawNode(g,index);
       // g.fillRect(obstacles.get(index).getX(), obstacles.get(index).getY(), GRID_SIZE, GRID_SIZE);
        //pF.drawNode(g, index+5);


        pF.drawParentList(g,index); // ->  draw how to find path
        for (int i = 0; i < pF.getObstacleListSize(); i++) {
            g.setColor(new Color(38, 38, 44));
            g.fillRect(obstacles.get(i).getX(), obstacles.get(i).getY(), GRID_SIZE, GRID_SIZE);
        }
    
        if (startCell != null) {
            g.setColor(new Color(158, 143, 115));
            g.fillRect(startCell.getX(), startCell.getY(), GRID_SIZE, GRID_SIZE);
        }


        if (endCell != null) {
            g.setColor(new Color(158, 143, 115));
            g.fillRect(endCell.getX(), endCell.getY(), GRID_SIZE, GRID_SIZE);
        }

            if(index>= pF.getParentList().size()){
               if (startCell != null && endCell != null  ) {
            g.setColor(new Color(209, 140, 2));
            pF.drawPath(g);
        }
    }

        for (int y = 0; y < NUM_OF_ROW + 1; y++) {
            for (int x = 0; x < NUM_OF_COL + 1; x++) {
                g.setColor(new Color(95, 95, 95));
                g.drawRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            }
        }       
        spaceIndicator(g);
        if(index>= pF.getParentList().size()){
        if (pF.isComplete()) {
            rainbowEffect(g);
            font(g, 45f, "PATH FOUND!", 225, 450);
            font(g, 25f, "IT TOOK " + pF.getNumberOfNode() + " CELLS TO REACH GOAL!", 85, 500);
        } else if (pF.isNoPath()) {
            g.setColor(new Color(219, 219, 219, 180));
            font(g, 45f, "NO PATH FOUND!", 165, 450);
        }
    }
}
    public void font(Graphics g, float fontSize, String message, int xPos, int yPos) {
       
        InputStream is = main.class.getResourceAsStream("ARCADE_N.TTF");
        try {
           
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        g.setFont(arcadeFont);
        g.drawString(message, xPos, yPos);
    }
    public void spaceIndicator(Graphics g) {
        if (!isReady) {
            g.setColor(new Color(221, 221, 221, 100));
            repaint();
        } else {
            g.setColor(new Color(255, 0, 0, 150));
            repaint();
        }
        font(g, 25f, "SPACE", 25, 900);
    }

   
    public void rainbowEffect(Graphics g) {
        int red = (int) (Math.random() * 225);
        int green = (int) (Math.random() * 225);
        int blue = (int) (Math.random() * 225);
        g.setColor(new Color(red, green, blue, 195));
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        createObstacles(e);
        createStartAndEndNode(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        createObstacles(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (!isReady && e.getKeyCode() == KeyEvent.VK_SPACE) {
            isReady = true;
        } else if (isReady && e.getKeyCode() == KeyEvent.VK_SPACE) {
            isReady = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_R) {
            runPathFinder();
            initTimer();
        }
       
        if (e.getKeyCode() == e.VK_BACK_SPACE) {
            clearBoard();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
       
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.index++;
        repaint();
     

    }
        }

