package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void guardarSDEnJson(String rutaArchivo, Classes.SD sd) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(sd, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Classes.SD leerSDDesdeJson(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            return gson.fromJson(reader, Classes.SD.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void guardarJTreeEnJson(String rutaArchivo, DefaultMutableTreeNode root) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(convertTreeNodeToJson(root), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DefaultMutableTreeNode leerJTreeDesdeJson(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            TreeNodeJson jsonNode = gson.fromJson(reader, TreeNodeJson.class);
            return convertJsonToTreeNode(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static TreeNodeJson convertTreeNodeToJson(DefaultMutableTreeNode node) {
        TreeNodeJson jsonNode = new TreeNodeJson(node.toString());
        for (int i = 0; i < node.getChildCount(); i++) {
            jsonNode.children.add(convertTreeNodeToJson((DefaultMutableTreeNode) node.getChildAt(i)));
        }
        return jsonNode;
    }

    private static DefaultMutableTreeNode convertJsonToTreeNode(TreeNodeJson jsonNode) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(jsonNode.name);
        for (TreeNodeJson childJson : jsonNode.children) {
            node.add(convertJsonToTreeNode(childJson));
        }
        return node;
    }

    // Clase interna para representar los nodos como JSON
    private static class TreeNodeJson {
        String name;
        List<TreeNodeJson> children = new ArrayList<>();

        TreeNodeJson(String name) {
            this.name = name;
        }

        // Constructor vacío requerido por Gson
        public TreeNodeJson() {}
    }
}