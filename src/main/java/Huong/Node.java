/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huong;
public class Node {
    private int value; // giá trị 
    int x; //  2 tọa độ  
    private int y;

    public Node(int val) {
          this.value = val;
          this.x = 0;
          this.y = 0;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getX(){
       return this.x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
       return this.y;
    }
}
