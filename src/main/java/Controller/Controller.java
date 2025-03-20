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
    
    public void updateFile(String oldname,String name) throws Exception {
        sd.updateFile(oldname,name);
    }
    public void deleteFile(String filename){
        sd.deleteFile(filename);
    }
    public String[][] getBlocks(){
        String[][] output = new String[4][10];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                try{
                    output[i][j] = Integer.toString(i) + Integer.toString(j) + ": " + sd.getBlocks()[i*10 + j].getFile().split("/")[sd.getBlocks()[i*10 + j].getFile().split("/").length-1];
                }catch(Exception e){
                    output[i][j] = Integer.toString(i) + Integer.toString(j) + ": free";
                }
            }
        }
        return output;
    }
    
    public boolean isAFile(String name){
        return sd.isFile(name);
    }
    
}
