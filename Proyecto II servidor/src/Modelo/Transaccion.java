package Modelo;

import Data.SQLExecutorURL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaccion {
    private int id_Transaccion;
    private String id_Cliente;
    private Integer monto_Transac;
    private int tipo_id;

    public Transaccion() {
    }

    public Transaccion(int id_Transaccion, String id_Cliente, Integer monto_Transac, int tipo_id) {
        this.id_Transaccion = id_Transaccion;
        this.id_Cliente = id_Cliente;
        this.monto_Transac = monto_Transac;
        this.tipo_id = tipo_id;
    }

    public int getId_Transaccion() {
        return id_Transaccion;
    }

    public void setId_Transaccion(int id_Transaccion) {
        this.id_Transaccion = id_Transaccion;
    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public Integer getMonto_Transac() {
        return monto_Transac;
    }

    public void setMonto_Transac(Integer monto_Transac) {
        this.monto_Transac = monto_Transac;
    }

    public int getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        this.tipo_id = tipo_id;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id_Transaccion=" + id_Transaccion +
                ", id_Cliente='" + id_Cliente + '\'' +
                ", monto_Transac=" + monto_Transac +
                ", tipo_id=" + tipo_id +
                '}';
    }


}
