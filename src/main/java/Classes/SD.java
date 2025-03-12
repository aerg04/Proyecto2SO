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
    Graph blocks;
    List table;
      
    public SD(int freeBlocks) {
        this.freeBlocks = freeBlocks;
        this.capacity = freeBlocks;
        Node[] nodes = new Node[freeBlocks];
        for (int i = 0; i < freeBlocks; i++) {
            nodes[i] = new Node();
        }
        blocks = new Graph(nodes);
        table = new List();
    }
    
    public void deleteFile(String filename){
        for (int i = 0; i < capacity; i++) {
            Node next = blocks.getById(i);
            if(next.getFile().equals(filename)){
                next.setFile(null);
                next.setNext(-1);
                freeBlocks++;
            }
        }
        NodoList pNext = table.getHead();
        while(pNext != null){
            if(((String[])pNext.getValue())[0].equals(filename)){
                table.delete(pNext);
            }
        }
    }
    public void createFile(int blocksnum, String filename) throws Exception{
        if(freeBlocks<blocksnum){
            throw new Exception();
        }else if(this.nameExist(filename)){
            throw new Exception();
        }else{
            allocateBlocks(blocksnum,filename);
        }
        
    }
    public void updateFile( String filename) throws Exception{
        //**********************************************
        //esto no es necesario
        //solo hay que modificar el nombre en el arreglo y la tabla
        //**********************************************
//        int currentlyBlocks = getBlocks(filename);
//        this.deleteFile(filename);
//        try{
//            this.createFile(blocksnum, filename);
//        }catch( Exception e){
//            this.createFile(currentlyBlocks, filename);
//            throw new Exception(e.getMessage());
//        }
            System.out.println("hola");
    }
    public boolean isFile(String filename){
        if(getBlocks(filename) == 0) return false;
        return true;
    }
    private int getBlocks(String filename){
        int output = 0;
        for (int i = 0; i < capacity; i++) {
            String next = blocks.getById(i).getFile();
            if(next.equals(filename)) output++;
        }
        return output;
    }
    
    private boolean nameExist(String filename){
        for (int i = 0; i < capacity; i++) {
            String next = blocks.getById(i).getFile();
            if(next.equals(filename)) return true;
        }
        return false;
    }
    
    private int nextFreeBlock(){
        for (int i = 0; i < capacity; i++) {
            String next = blocks.getById(i).getFile();
            if(next.equals(null)) return i;
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
            Node next = blocks.getById(j);
            next.setFile(filename);
            if(i != blocksnum -1){
                int nextFree = this.nextFreeBlock();
                next.setNext(nextFree);
            }
        }
    }
}
