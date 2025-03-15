/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package proyecto2.proyecto2;
import View.MainView;
import Controller.Controller;
import Classes.*;
/**
 *
 * @author DELL
 */
public class Proyecto2 {

    public static void main(String[] args) {
        Node[] nodes = new Node[40];
        for (int i = 0; i < 40; i++) {
            nodes[i] = new Node();
        }
        List table = new List();
        SD sd = new SD(40,nodes,table);
        Controller controller = new Controller(sd);
        MainView view = new MainView(table);
        view.setController(controller);
        
        view.updateJtable1();
        view.updateJtable2();
    }
}
