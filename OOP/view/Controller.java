package view;
import binary.*;
import a_search.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
public class Controller {
	
	public JFrame f = new JFrame();

    JButton b1 = new JButton("Sequential Search");
    JButton b2 = new JButton("Binary Search");
    JButton b3 = new JButton("A* Search");
	
	public Controller() {
		
	}
	
	public void initialize() {
		b1.setBounds(150, 100, 250, 40);
        b2.setBounds(150, 150, 250, 40);
        b3.setBounds(150, 200, 250, 40);

        f.add(b1);
        f.add(b2);
        f.add(b3);

        f.setSize(550, 600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b2.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
                BinaryController binary = new BinaryController();
		        binary.initialize();
			}
        });
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ASearchController asearch = new ASearchController();
            }
        });
	}

}