package Controlador;

import Modelo.ModeloServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorServidor
{
    ModeloServidor modelo;

    public ControladorServidor()
    {
        this.modelo = new ModeloServidor();
        modelo.setControlador(this);
        arrancar();
    }

    private void arrancar()
    {
        System.out.println("Servidor levantado\n");
        modelo.abrirPuerto();
        modelo.esperarAlCliente();
        modelo.crearFlujos();
        modelo.start();
    }

    public void salir()
    {
        System.exit(0);
    }
}
