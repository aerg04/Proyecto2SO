package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Classes.SD;
import Classes.ModeloSDTemp;
import Classes.List; // Tu lista personalizada

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Guardar SD en JSON usando ModeloSDTemp
    public static void guardarSDEnJson(String rutaArchivo, SD sd) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            ModeloSDTemp temp = new ModeloSDTemp(
                sd.getCapacidad(),
                sd.getBloquesLibres(),
                sd.getBloques(),
                convertListToArray(sd.getTabla()) // Convertimos la lista a un array antes de guardar
            );
            gson.toJson(temp, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leer SD desde JSON usando ModeloSDTemp
    public static SD leerSDDesdeJson(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type modeloSDTempType = new TypeToken<ModeloSDTemp>() {}.getType();
            ModeloSDTemp temp = gson.fromJson(reader, modeloSDTempType);

            List tabla = convertArrayToList(temp.getTabla()); // Convertimos el array de vuelta a la lista personalizada
            return new SD(temp.getBloquesLibres(), temp.getBloques(), tabla);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos auxiliares para convertir entre List y array
    private static String[] convertListToArray(List tabla) {
        String[] array = new String[tabla.getSize()];
        int index = 0;
        for (Object nodo : tabla) {
            array[index++] = (String) nodo;
        }
        return array;
    }

    private static List convertArrayToList(String[] array) {
        List tabla = new List();
        for (String elemento : array) {
            tabla.appendLast(elemento);
        }
        return tabla;
    }

    // Guardar el estado del JTree
    public static void guardarJTreeEnJson(String rutaArchivo, DefaultMutableTreeNode root) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(convertTreeNodeToJson(root), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leer el estado del JTree
    public static DefaultMutableTreeNode leerJTreeDesdeJson(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            TreeNodeJson jsonNode = gson.fromJson(reader, TreeNodeJson.class);
            return convertJsonToTreeNode(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convertir DefaultMutableTreeNode a JSON-friendly estructura
    private static TreeNodeJson convertTreeNodeToJson(DefaultMutableTreeNode node) {
        TreeNodeJson jsonNode = new TreeNodeJson(node.toString());
        for (int i = 0; i < node.getChildCount(); i++) {
            jsonNode.children.add(convertTreeNodeToJson((DefaultMutableTreeNode) node.getChildAt(i)));
        }
        return jsonNode;
    }

    // Convertir JSON a DefaultMutableTreeNode
    private static DefaultMutableTreeNode convertJsonToTreeNode(TreeNodeJson jsonNode) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(jsonNode.name);
        for (TreeNodeJson childJson : jsonNode.children) {
            node.add(convertJsonToTreeNode(childJson));
        }
        return node;
    }

    // Clase auxiliar para serializar nodos de JTree
    private static class TreeNodeJson {
        String name;
        java.util.List<TreeNodeJson> children = new java.util.ArrayList<>();

        TreeNodeJson(String name) {
            this.name = name;
        }
    }
}