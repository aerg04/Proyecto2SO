/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author DELL
 */
public class Graph {
    private Node[] array;

    public Graph(Node[] array) {
        this.array = array;
    }

    public Node getById(int i) {
        return array[i];
    }
    
}
