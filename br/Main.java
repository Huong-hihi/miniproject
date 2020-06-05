
package br;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;

public class Main {
	JFrame frame;
	public Timer timer;
	private final int DELAY = 30;
    private final int INITIAL_DELAY = 150;   
	private Graphics2D g2;
	private int cell = 10;
	private int valueX = -1;
    private int index = 1;
	private int valueY = -1;
	private int WIDTH = 950;
	private final int HEIGHT = 650;
	private final int MSIZE = 700;
	private int CSIZE = 65;
	private boolean solving = false;
	public Timer tmr;
	private insertSort isSort = new insertSort();
	private Random r = new Random();
	private Node[][] map;
	private Node searchNode;
	private ArrayList<Node> arrNode = new ArrayList<Node>();
	
	
	JSlider size = new JSlider(1,5,2);
	JSlider speed = new JSlider(0,500,30);
	JSlider obstacles = new JSlider(1,100,50);
	
	JTextField txtsearch = new JTextField();
	JTextField add = new JTextField();
	
	JButton buttsearch = new JButton("Search");
	JButton buttadd = new JButton("Add");
	JButton buttaddrd = new JButton("Add Random");
	JButton buttresetall = new JButton("Reset All");
	JButton buttreset = new JButton("Reset");
	
	JPanel toolP = new JPanel();
	
	Map canvas;
	
	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	public boolean addNodeplus(ArrayList<Node> arrNode1,int n) {
		Node node1 = new Node(valueX,valueY);
		node1.setValue(n);
		if(!checkList(arrNode1, node1)) {
		isSort.insertionSort(arrNode1, node1);
		setXYNode(arrNode1);
		add.setText("");
		delay();
		update();
		return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Is present");
			return false;
		}
	}
	
	public boolean checkList(ArrayList<Node> arrNode1, Node node) {
		if(arrNode1.size()==0) return false;
		for(Node node1 : arrNode1) {
			if(node.getValue() == node1.getValue()) return true;
		}
		return false;
	}
	public boolean checkList(ArrayList<Node> arrNode1, int n) {
		if(arrNode1.size()==0) return false;
		for(Node node1 : arrNode1) {
			if(n == node1.getValue()) return true;
		}
		return false;
	}
	public void reset() {
		for(Node node : arrNode) {
			node.setNodetype(0);
		}
		update();
	}
	public void resetAll() {
		ArrayList<Node> newArr = new ArrayList<Node>();
		arrNode = newArr;
		update();
	}
	
    boolean binarySearch(ArrayList<Node> arrNode, int l, int r, int x) 
    { 
       
        while(l<=r){
             int mid = (l + r) / 2;
            arrNode.get(mid).setNodetype(2);
            if (arrNode.get(mid).getValue() == x) {
            	arrNode.get(mid).setNodetype(3);
            	solving = true;
                return solving;
            }
            if(arrNode.get(mid).getValue()>x) {
                
                for(int i = mid ;i<=r;i++) arrNode.get(i).setNodetype(1);
                r = mid -1;
            }
            if(arrNode.get(mid).getValue()<x) {
               
                for(int i = l ;i<=mid;i++) arrNode.get(i).setNodetype(1);
                 l = mid + 1;
            }
        }
        return solving;

    }
	public void drawNode(Graphics g,Node node) {
		switch(node.getNodetype()) {
		case 0:
			g.setColor(Color.WHITE);
			break;
		case 1:
			g.setColor(Color.RED);
			break;
		case 2:
			g.setColor(Color.YELLOW);
			break;
		case 3:
			g.setColor(Color.GREEN);
			break;
	}
        final int red = (int) (Math.random() * 225);
        final int green = (int) (Math.random() * 225);
        final int blue = (int) (Math.random() * 225);
       
		int x = node.getX();
		int y = node.getY();
		g.fillRect(x,y,CSIZE,CSIZE);
		g.setColor(Color.BLACK);
		g.drawString(node.getValue()+"", x + 25, y + 35);
		g.setColor(new Color(red, green, blue, 195));
		g.drawRect(x,y,CSIZE,CSIZE);

	}
	public void drawNode(Graphics g,ArrayList<Node> arrNode) {
		for(int i = 0; i < arrNode.size(); i++) {
			drawNode(g,arrNode.get(i));
		}
	}
	
	public void setXYNode(ArrayList<Node> arrNode) {
		if(arrNode.size()>0) {
			for(int i = 0; i < arrNode.size(); i++) {
				arrNode.get(i).setX( 20 + ((i)%10) * CSIZE);
				arrNode.get(i).setY( 30 + (i/10)*CSIZE);
			}
		}
	}
	
	public void update() {
		setXYNode(arrNode);
		canvas.repaint();
	}
	public void delay() {	//DELAY METHOD
		try {
			Thread.sleep(3);
		} catch(Exception e) {}
	}
	public void delay1() {	//DELAY METHOD
		try {
			Thread.sleep(300);
		} catch(Exception e) {}
	}
	private void initialize() {	//INITIALIZE THE GUI ELEMENTS
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(WIDTH,HEIGHT);
		frame.setTitle("Binary Search");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		toolP.setBorder(BorderFactory.createTitledBorder(loweredetched,"Controls"));
		int space = 25;
		int buff = 45;
		
		toolP.setLayout(null);
		toolP.setBounds(10,10,210,600);
		
		txtsearch.setBounds(40,space, 120, 25);
		toolP.add(txtsearch);
		space+=buff;
		

		
		buttsearch.setBounds(40, space, 120, 25);
		toolP.add(buttsearch);
		space+=buff;
		
		add.setBounds(40,space, 120, 25);
		toolP.add(add);
		space+=buff;
				
		buttadd.setBounds(40,space, 120, 25);
		toolP.add(buttadd);
		space+=buff;
		
		buttaddrd.setBounds(40,space,120,25);
		toolP.add(buttaddrd);
		space+=buff;
		
		buttreset.setBounds(40, space, 120, 25);
		toolP.add(buttreset);
		space+=buff;
		
		buttresetall.setBounds(40, space, 120, 25);
		toolP.add(buttresetall);
		space+=buff;
		
		frame.getContentPane().add(toolP);
		
		canvas = new Map();
		canvas.setBounds(230, 10, MSIZE+1, MSIZE+1);
		frame.getContentPane().add(canvas);
		tmr = new Timer (1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            index++;
				update();
			}
		});
		
		buttsearch.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
                            	tmr.start();
                                    if(index == 100) tmr.stop();
                                

				if(txtsearch.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please input your value");
				}
				else {
					searchNode = new Node(valueX,valueY);
					int n = Integer.parseInt(txtsearch.getText());
					searchNode.setValue(n);
					txtsearch.setText("");	
					if(binarySearch(arrNode, 0, arrNode.size()-1, searchNode.getValue()))
						binarySearch(arrNode, 0, arrNode.size()-1, searchNode.getValue());
				}
			}
		});

		buttadd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(add.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please input your value");
				}
				else if(arrNode.size() == 80) {
					JOptionPane.showMessageDialog(null, "MAX");
				} 
				else{
				int n = Integer.parseInt(add.getText());
				addNodeplus(arrNode,n);
				add.setText("");
				}
			}
		});
		
		buttaddrd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arrNode.size() == 80) {
					JOptionPane.showMessageDialog(null, "MAX");
				}
				else {
					int n;
					do{n = r.nextInt(100);}while(checkList(arrNode, n));
					addNodeplus(arrNode, n);
					
			}
				}
		});
		buttreset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				reset();
				
			}
		});
		
		buttresetall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();
				
			}
		});
		
		}
	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
	}
	
	class Map extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
		
		private static final long serialVersionUID = 1L;
               // private int index = 0;
		public Map() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		public void paintComponent(Graphics g) {	//REPAINT
			super.paintComponent(g);
                       // g.drawString(index+"",200,200);
			if(arrNode == null) {}
			else {
				drawNode(g,arrNode);
			}
		}


		

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

        @Override
        public void actionPerformed(ActionEvent ae) {
          ////  index++;
            canvas.repaint();
//To change body of generated methods, choose Tools | Templates.
//if(index == 100) tmr.stop();
        }
    }
}