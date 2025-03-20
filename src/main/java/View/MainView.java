package View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import Classes.*;
import Controller.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class MainView extends javax.swing.JFrame {
    Controller controller;
    Classes.List asigntable;
    /**
     * Creates new form MainView
     */
    public MainView(Classes.List data) {
        initComponents();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        asigntable = data;
        this.createButton.setEnabled(false);
        this.updateButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
    }
    public MainView() {
        initComponents();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void updateJtable1(){

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }

        NodoList pNext = asigntable.getHead();
        while(pNext != null){
            String[] new_ = new String[3];

            int i = ((String[]) pNext.getValue())[0].split("/").length-1;
            new_[0] = ((String[]) pNext.getValue())[0].split("/")[i];
            new_[1] = ((String[]) pNext.getValue())[1];
            new_[2] = ((String[]) pNext.getValue())[2];
            model.addRow(new_);
            pNext = pNext.getpNext();
        }

    }
    //solamente falta para que se actulize la tabla de bloques y la tabla de asignacion
    public void updateJtable2(){
        //controler. getBlocks  return a String[][]

        String[][] blocks = controller.getBlocks();
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                jTable2.setValueAt(blocks[row][col], row, col);
            }
        }
        
    }
    
    public void updateJtree(String name){
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        TreePath selectedPath = jTree1.getSelectionPath();
        if (selectedPath == null) {
            JOptionPane.showMessageDialog(this, "Select a node to rename");
            return;
        }
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();

        // Check if a node with the same name already exists among siblings
        boolean nodeExists = false;
        for (int i = 0; i < parentNode.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) parentNode.getChildAt(i);
            if (name.equals(childNode.getUserObject().toString())) {
                nodeExists = true;
                break;
            }
        }

        if (nodeExists) {
            JOptionPane.showMessageDialog(this, "A node with the same name already exists");
            return;
        }

        // Construct the path of the new node
        try {
            if (controller.isAFile(this.getPathAsString(selectedPath))) {
                name = name + ".txt";
                String newPath = getPathAsString(selectedPath.getParentPath()) + "/" + name;

                System.out.println("Path of the new node before renaming: " + newPath);
                controller.updateFile(this.getPathAsString(selectedPath), newPath);
            }
            selectedNode.setUserObject(name);
            model.nodeChanged(selectedNode);
            model.reload(selectedNode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private String getPathAsString(TreePath path) {
    Object[] nodes = path.getPath();
    StringBuilder pathString = new StringBuilder();

    for (Object node : nodes) {
        pathString.append(node.toString()).append("/");
    }

    // Remove the trailing slash
    if (pathString.length() > 0) {
        pathString.setLength(pathString.length() - 1);
    }

    return pathString.toString();
    }  
    private void deleteNodeAndChildren(DefaultMutableTreeNode node) {
    // Recursively delete all child nodes
    while (node.getChildCount() > 0) {
        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getFirstChild();
        deleteNodeAndChildren(childNode);
        node.remove(childNode);
    }

    // Delete the file associated with this node
    TreePath path = new TreePath(node.getPath());
    controller.deleteFile(this.getPathAsString(path));
    }
    private void createJtree(String name, int num){
            DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
            TreePath selectedPath = jTree1.getSelectionPath();
            DefaultMutableTreeNode selectedNode;
            if (selectedPath != null) {
                selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            } else {
                selectedNode = (DefaultMutableTreeNode) model.getRoot();
            }
            // Check if a node with the same name already exists
            boolean nodeExists = false;
            for (int i = 0; i < selectedNode.getChildCount(); i++) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) selectedNode.getChildAt(i);
                if (name.equals(childNode.getUserObject().toString())) {
                    nodeExists = true;
                    break;
                }
            }

            if (!nodeExists) {
                // Construct the path of the new node before adding it to the tree
                try{
                    if (controller.isAFile(this.getPathAsString( new TreePath(selectedNode.getPath()) ))) {
                        JOptionPane.showMessageDialog(this,"Is a file");
                        return;
                    }
                    if(num >0){
                        name = name + ".txt";
                        TreePath parentPath = new TreePath(selectedNode.getPath());
                        
                        String newPath = getPathAsString(parentPath) + "/" + name;
                        

                        System.out.println("Path of the new node before adding: " + newPath);
                        controller.createFile(num, newPath);
                    }
                    //System.out.println("hola2");
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
                    //System.out.println("hola3");
                    selectedNode.add(newNode);
                    //System.out.println("hola4");
                    model.reload(selectedNode);
                    //System.out.println("hola5");
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    e.printStackTrace();
                }
                // Add the new node to the tree
            } else {
                JOptionPane.showMessageDialog(this, "Cant be 2 same files");
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 200, 180));

        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Name", "First Block", "Lenght"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 230, 180));

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 120, -1));

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jPanel1.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 120, -1));

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jPanel1.add(createButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 120, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 120, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(40);
        jScrollPane3.setViewportView(jTable2);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 600, 170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Nombre del archivo/directorio");
        if(!name.equals(null)){
            String blocknum = JOptionPane.showInputDialog("Numero de bloques");
            try{
                int num = Integer.parseInt(blocknum);
                this.createJtree(name,num);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Not an integer");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Empty String");
        }
        this.updateJtable1();
        this.updateJtable2();
    }//GEN-LAST:event_createButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Nombre del archivo/directorio");
        if(!name.equals(null)){
            
            try{
                this.updateJtree(name);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Empty String");
        }
        this.updateJtable1();
        this.updateJtable2();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
      // TODO add your handling code here:
    TreePath selectedPath = jTree1.getSelectionPath();
    if (selectedPath == null) {
        JOptionPane.showMessageDialog(this, "Select a node to delete");
        return;
    }

    // Get the selected node
    DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

    // Recursively delete all child nodes and their files
    deleteNodeAndChildren(selectedNode);

    // Remove the node from the JTree
    model.removeNodeFromParent(selectedNode);

    // Update the tables
    this.updateJtable1();
    this.updateJtable2();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if(this.jComboBox1.getSelectedIndex() == 0){
            this.createButton.setEnabled(false);
            this.updateButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
        }else{
            this.createButton.setEnabled(true);
            this.updateButton.setEnabled(true);
            this.deleteButton.setEnabled(true);  
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
