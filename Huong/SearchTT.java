/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huong;

import java.util.ArrayList;
public class SearchTT {
    private static final ArrayList<Node> List = new ArrayList<Node>();
    private static int indexSearch; // giá trị tìm kiếm 
    public static ArrayList<Node> getList(){
        return List;
    }
    public static void setIndexSearch(int val){
        indexSearch = val;
    }
    public static int getIndexSearch(){
        return indexSearch;
    }
    public SearchTT() {
        this.indexSearch = -1000;
    }
    public void insertNode(int val){
        List.add(new Node(val));
    }
    public void deleteNode(int index){ 
        if(index > -1){ // nếu tìm thấy phần thử 
           List.remove(index);
        }else
            System.out.println("index less 0");
    }
    public Node searchTT(int val){
        Node result = null; // tạo một node null
        int i = 0;
        // tìm kiếm trong danh sách 
        for(Node e: List){
           if(e.getValue() == val){
               setIndexSearch(i); // khi tìm thấy thì gán giá trị tìm kiêm là giá trị của phần tử 
               result = e; // gán cho result giá trị của node đã được tìm thấy 
               break;
           }
           i++; 
        }
        if(result == null) setIndexSearch(-1); // nếu không tìm kiếm được thì gán giá trị tìm kiếm là -1 
        return result; 
    }
}
