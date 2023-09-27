package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tipoTransacion {
    private int id;
    private String tipo_Transaccion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_Transaccion() {
        return tipo_Transaccion;
    }

    public void setTipo_Transaccion(String tipo_Transaccion) {
        this.tipo_Transaccion = tipo_Transaccion;
    }

    public tipoTransacion() {
    }

    @Override
    public String toString() {
        return "tipoTransacion{" +
                "id=" + id +
                ", tipo_Transaccion='" + tipo_Transaccion + '\'' +
                '}';
    }

}
