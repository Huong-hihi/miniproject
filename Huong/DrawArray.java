/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huong;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
public class DrawArray extends JPanel implements ActionListener{
    private Graphics2D g2; 
    private int index;
    private boolean done;
    Timer tm = new Timer(1000, this);
    public DrawArray() {
        this.index = -1;
        this.done = false;
    }
    
    public void setXY(ArrayList<Node> List){
        int x = 0, y = 150, WIDTH = 60;
        for(Node e : List){
            e.setX(x+WIDTH);
            e.setY(y);
            x += WIDTH;
        }
    }
    
    public void draw(ArrayList<Node> List){
       int i = 0;
       int x=0;
        for(Node e: List){
           String valueText = e.getValue()+"";
           g2.setColor(Color.BLACK);
           
           if(i == this.index){
               g2.setColor(Color.green); 
               if(this.index == SearchTT.getIndexSearch()){
                   this.done = true;
               }
           }
           if(this.index >= List.size()){
               this.done= true;
           }
           
        int a = 1200*x;
        int b=  60*x;
            g2.drawRect(e.getX()- a, e.getY() +b , 60, 40);
            g2.drawString(valueText, e.getX()+18-a, e.getY()+20 +b);
            i++;
           if(e.getX() %1200 == 0){
            x=x+1;
           }
       }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g); // kế thừa lớp cha ở Jfram
        g2 = (Graphics2D) g; //ép kiểu 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ArrayList<Node> List = SearchTT.getList();
        setXY(List);
        draw(List);
       
    }
      public void startAction(){
            this.index = -1;
            this.done = false;
            tm.start();
        } 
    @Override
    public void actionPerformed(ActionEvent ae) {
            this.index++;
            if(!this.done){ 
                repaint();
            } else {
                tm.stop();
                this.index = -1;
                this.done = false;
            }
    }
}
