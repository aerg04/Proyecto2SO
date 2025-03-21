package Classes;

import java.io.Serializable;

public class SDTemp implements Serializable {
    private int capacidad;
    private int bloquesLibres;
    private Node[] bloques;
    private String[][] tabla; // Se guarda la tabla como array de Strings en JSON

    public SDTemp(int capacidad, int bloquesLibres, Node[] bloques, String[][] tabla) {
        this.capacidad = capacidad;
        this.bloquesLibres = bloquesLibres;
        this.bloques = bloques;
        this.tabla = tabla;
    }

    // Getters y Setters
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getBloquesLibres() {
        return bloquesLibres;
    }

    public void setBloquesLibres(int bloquesLibres) {
        this.bloquesLibres = bloquesLibres;
    }

    public Node[] getBloques() {
        return bloques;
    }

    public void setBloques(Node[] bloques) {
        this.bloques = bloques;
    }

    public String[][] getTabla() {
        return tabla;
    }

    public void setTabla(String[][] tabla) {
        this.tabla = tabla;
    }
}
