/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author DELL
 */
public class SD {
    int capacity;
    int freeBlocks;
    Node[] blocks;
    List table;
      
    public SD(int freeBlocks, Node[] nodes, List list) {
        this.freeBlocks = freeBlocks;
        this.capacity = freeBlocks;
//        Node[] nodes = new Node[freeBlocks];
//        for (int i = 0; i < freeBlocks; i++) {
//            nodes[i] = new Node();
//        }
        blocks = nodes;
        table = list;
    }

    public Node[] getBlocks() {
        return blocks;
    }
    
    public void deleteFile(String filename){
        for (int i = 0; i < capacity; i++) {
            Node next = blocks[i];
            if(next.getFile()!= null){
                if(next.getFile().equals(filename)){
                    next.setFile(null);
                    next.setNext(-1);
                    freeBlocks++;
                }
            }
        }
        NodoList pNext = table.getHead();
        while(pNext != null){
            if(((String[])pNext.getValue())[0].equals(filename)){
                table.delete(pNext);
                break;
            }
        }
    }
    public void createFile(int blocksnum, String filename) throws Exception{
        if(freeBlocks<blocksnum){
            throw new Exception(ExceptionMessage.notSpace);
        }else if(this.nameExist(filename)){
            throw new Exception(ExceptionMessage.sameName);
        }else{
            allocateBlocks(blocksnum,filename);
        }
        
    }
    public void updateFile( String filename, String newfilename) throws Exception{
        //**********************************************
        if(this.nameExist(newfilename)){
            throw new Exception(ExceptionMessage.sameName);
        }
        //**********************************************
        NodoList pNext = table.getHead();
        while(pNext != null){
            if(((String[])pNext.getValue())[0].equals(filename)){
                ((String[]) pNext.getValue())[0] = newfilename;

                break;
            }
        }
        int i = startFile(filename); 
        while(i != -1){
            blocks[i].setFile(newfilename);
            i = blocks[i].getNext();
        }
    }
    public boolean isFile(String filename){
        if(getBlocks(filename) == 0) return false;
        return true;
    }
    
    public int startFile(String filename){
        for (int i = 0; i < capacity; i++) {
            if(blocks[i].getFile() != null){
            if(blocks[i].getFile().equals(filename) ) return i;
            }
        }
        return -1;
    }
    
    private int getBlocks(String filename){
        int output = 0;
        for (int i = 0; i < capacity; i++) {
            String next = blocks[i].getFile();
            if (next != null) {    
            if(next.equals(filename)) output++;
            }
        }
        return output;
    }
    
    private boolean nameExist(String filename){
        for (int i = 0; i < capacity; i++) {
            String next = blocks[i].getFile();
            if(next != null){
            if(next.equals(filename)) return true;
            }
        }
        return false;
    }
    
    private int nextFreeBlock(){
        for (int i = 0; i < capacity; i++) {
            String next = blocks[i].getFile();
            if(next == null) return i;
        }
        return -1;
    }
    private void allocateBlocks(int blocksnum, String filename){
        for (int i = 0; i < blocksnum; i++) {
            int j = this.nextFreeBlock();
            if(i == 0){
                String[] array = {filename,Integer.toString(j),Integer.toString(blocksnum)};
                table.appendLast(array);
            }
            Node next = blocks[j];
            next.setFile(filename);
            if(i != blocksnum -1){
                int nextFree = this.nextFreeBlock();
                next.setNext(nextFree);
            }
            freeBlocks--;
        }
    }
}
