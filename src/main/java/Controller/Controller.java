/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Classes.*;
/**
 *
 * @author DELL
 */
public class Controller {
    private SD sd;

    public Controller(SD sd) {
        this.sd = sd;
    }
    public void createFile(int num,String name) throws Exception {
        sd.createFile(num, name);
    }
}
