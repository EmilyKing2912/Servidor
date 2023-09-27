package Modelo;

import Controlador.ControladorServidor;
import Data.ManejoDatos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloServidor extends Thread
{
    ControladorServidor controlador;
    ManejoDatos datos;
    final int PUERTO = 7020;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public ModeloServidor()
    {
        datos = new ManejoDatos();
    }

    public void setControlador(ControladorServidor controlador)
    {
        this.controlador = controlador;
    }

    public void abrirPuerto()
    {
        try
        {
            serverSocket = new ServerSocket(PUERTO);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void esperarAlCliente()
    {
        try
        {
           socket = serverSocket.accept();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void crearFlujos()
    {
        try
        {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            bufferedReader = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            bufferedWriter = new BufferedWriter(osw);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public String recibirMensaje()
    {
        try
        {
            String mensaje = bufferedReader.readLine();
            return mensaje;
        }
        catch (IOException ex)
        {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE,null,ex);
        }
        return "";
    }

    public void enviarMensaje(String mensaje)
    {
        try
        {
            bufferedWriter.write(mensaje);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ModeloServidor.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void run()
    {
        while (true)
        {
            String mensaje = recibirMensaje();
            if(mensaje.equals("Salir"))
            {
                System.out.println("Y se fue el pana");
                controlador.salir();
            }
            else
            {
                if(mensaje.equals("CL Listo") || mensaje.equals("CCS Listo"))
                {
                    System.out.println("El cliente dice: " + mensaje);
                }
                else
                {
                    int posicion = mensaje.indexOf(':');
                    String accion = mensaje.substring(0,posicion);
                    switch (accion)
                    {
                        case "Verificar usuario":
                        {
                            String usuario = mensaje.substring(posicion+1,mensaje.length());
                            if(datos.validaUsuario(usuario))
                            {
                                enviarMensaje("true");
                            }
                            else
                            {
                                enviarMensaje("false");
                            }
                        }
                        break;
                        case "Verificar usuario y password":
                        {
                            int posicion2 = mensaje.indexOf(';');
                            String usuario = mensaje.substring(posicion+1,posicion2);
                            String password = mensaje.substring(posicion2+1,mensaje.length());
                            if(datos.getClave(usuario).equals(password))
                            {
                                enviarMensaje("true");
                            }
                            else
                            {
                                enviarMensaje("false");
                            }
                        }
                        break;
                        case "Devolver saldo":
                        {
                            String usuario = mensaje.substring(posicion+1,mensaje.length());
                            String saldo = String.valueOf(datos.getSaldo(usuario));
                            enviarMensaje(saldo);
                        }
                        break;
                        case "Cambio de password":
                        {
                            int posicion2 = mensaje.indexOf(';');
                            String usuario = mensaje.substring(posicion+1,posicion2);
                            String password = mensaje.substring(posicion2+1,mensaje.length());
                            Cliente temporal= datos.cargarCliente(usuario);
                            temporal.setClave(password);
                            datos.actualizarCliente(temporal);
                        }
                        break;
                        case "Verificar monto":
                        {
                            int posicion2 = mensaje.indexOf(';');
                            String usuario = mensaje.substring(posicion+1,posicion2);
                            int montoRetirar = Integer.valueOf(mensaje.substring(posicion2+1,mensaje.length()));
                            if(datos.getSaldo(usuario) > montoRetirar)
                            {
                                enviarMensaje("true");
                            }
                            else
                            {
                                enviarMensaje("false");
                            }
                        }
                        break;
                        case "Actualizar saldo":
                        {
                            int posicion2 = mensaje.indexOf(';');
                            int posicion3 = mensaje.indexOf('*');
                            String usuario = mensaje.substring(posicion+1,posicion2);
                            String saldo = mensaje.substring(posicion2+1,posicion3);
                            int montoRetirado = Integer.valueOf(mensaje.substring(posicion3+1,mensaje.length()));
                            Cliente temporal= datos.cargarCliente(usuario);
                            temporal.setSaldo(Integer.valueOf(saldo));
                            datos.actualizarCliente1(temporal);
                            int numeroTransaccion= datos.numeroTransaccion(usuario)+1;
                            String cedula=datos.getID(usuario);
                            Transaccion temporal1 = new Transaccion(numeroTransaccion,cedula,montoRetirado,2);
                            datos.insertarTransaccion(temporal1);
                        }
                        break;
                        default:
                        {
                            enviarMensaje("error");
                        }
                    }
                }
            }
        }
    }
}
