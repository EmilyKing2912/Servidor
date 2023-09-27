package Data;

import Modelo.Cliente;
import Modelo.Transaccion;
import Modelo.tipoTransacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManejoDatos {
    private List<Cliente> listaClientes;
    private List<Transaccion> listaTransacciones;

    public ManejoDatos() {
        this.listaClientes =cargarDatosURLCLIENTE();
        this.listaTransacciones = cargarDatosURLTransaccion();
    }

    public static List<Cliente> cargarDatosURLCLIENTE() {
        List<Cliente> listaCliente = new ArrayList<>();
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433", "PROYECTO2", "sa", "password");
        sqlExecutorURL.abreConexion();
        ResultSet rs2 = sqlExecutorURL.ejecutaSQL("select * from CLIENTE");

        try {
            while (rs2.next()){
                Cliente comodin=new Cliente();
                comodin.setCedula(rs2.getString("CEDULA"));
                comodin.setNombre(rs2.getString("NOMBRE"));
                comodin.setUsuario(rs2.getString("USUARIO"));
                comodin.setClave(rs2.getString("CLAVE"));
                comodin.setSaldo(rs2.getInt("SALDO"));
                listaCliente.add(comodin);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sqlExecutorURL.cierraConexion();
        return listaCliente;
    }
    //valida si el usuario existe
    public boolean validaUsuario(String usuario) {
   boolean respuesta=false;
   for(int i=0;i<listaClientes.size();i++){
       if(listaClientes.get(i).getUsuario().equals(usuario)){
           respuesta= true;
       }
   }
   return respuesta;
    }


    public  String getClave(String usuario){
        String clave="";
        for(int i=0;i<listaClientes.size();i++){
            if(listaClientes.get(i).getUsuario().equals(usuario)){
                clave=listaClientes.get(i).getClave();
            }
        }
        return clave;
    }


    public  Integer getSaldo(String usuario){
        Integer saldo=0;
        for(int i=0;i<listaClientes.size();i++){
            if(listaClientes.get(i).getUsuario().equals(usuario)){
                saldo=listaClientes.get(i).getSaldo();
            }
        }
        return saldo;
    }

    public String getID(String usuario)
    {
        String id="";
        for(int i=0;i<listaClientes.size();i++){
            if(listaClientes.get(i).getUsuario().equals(usuario)){
                id=listaClientes.get(i).getCedula();
            }
        }
        return id;
    }

    public Cliente cargarCliente(String usuario){
        Cliente cli=new Cliente();
        for(int i=0;i<listaClientes.size();i++){
            if(listaClientes.get(i).getUsuario().equals(usuario)){
                cli.setCedula(listaClientes.get(i).getCedula());
                cli.setNombre(listaClientes.get(i).getNombre());
                cli.setUsuario(listaClientes.get(i).getUsuario());
                cli.setClave(listaClientes.get(i).getClave());
                cli.setSaldo(listaClientes.get(i).getSaldo());
            }
        }
        return cli;
    }

    public void actualizarCliente(Cliente cliente){
        SQLExecutorURL sqlExecutorURL=new SQLExecutorURL("1433","PROYECTO2","sa","password");
        sqlExecutorURL.abreConexion();
        String valores[]=new String[7];
        valores[0]="update CLIENTE set CEDULA = ?, NOMBRE = ?, USUARIO = ? ,"+
                "CLAVE = ?, SALDO = ?  where CEDULA = ?";
        valores[1]=cliente.getCedula();
        valores[2]=cliente.getNombre();
        valores[3]=cliente.getUsuario();
        valores[4]=cliente.getClave();
        valores[5]=Integer.toString(cliente.getSaldo());
        valores[6]=cliente.getCedula();
        sqlExecutorURL.prepareStatement(valores);
        System.out.println("Se ha cambiado la clave de manera correcta");
        listaClientes=cargarDatosURLCLIENTE();
    }
    public void actualizarCliente1(Cliente cliente){
        SQLExecutorURL sqlExecutorURL=new SQLExecutorURL("1433","PROYECTO2","sa","password");
        sqlExecutorURL.abreConexion();
        String valores[]=new String[7];
        valores[0]="update CLIENTE set CEDULA = ?, NOMBRE = ?, USUARIO = ? ,"+
                "CLAVE = ?, SALDO = ?  where CEDULA = ?";
        valores[1]=cliente.getCedula();
        valores[2]=cliente.getNombre();
        valores[3]=cliente.getUsuario();
        valores[4]=cliente.getClave();
        valores[5]=Integer.toString(cliente.getSaldo());
        valores[6]=cliente.getCedula();
        sqlExecutorURL.prepareStatement(valores);
        listaClientes=cargarDatosURLCLIENTE();
    }


    public void insertarTransaccion(Transaccion transaccion){
        SQLExecutorURL sqlExecutorURL=new SQLExecutorURL("1433","PROYECTO2","sa","password");
        sqlExecutorURL.abreConexion();
        String valores[]=new String[5];
        valores[0]="INSERT INTO TRANSACCIONES(ID_TRANSACCION,ID_CLIENTE, MONTO_TRANSAC, TIPO_ID)\n" +
                "VALUES"+"(?,?,?,?)";
        valores[1]=Integer.toString(transaccion.getId_Transaccion());
        valores[2]=transaccion.getId_Cliente();
        valores[3]=Integer.toString(transaccion.getMonto_Transac());
        valores[4]=Integer.toString(transaccion.getTipo_id());
        sqlExecutorURL.prepareStatement(valores);
        listaTransacciones=cargarDatosURLTransaccion();
    }

    public static List<Transaccion> cargarDatosURLTransaccion() {
        List<Transaccion> listaTransaccion = new ArrayList<>();
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433", "PROYECTO2", "sa", "password");
        sqlExecutorURL.abreConexion();
        ResultSet rs2 = sqlExecutorURL.ejecutaSQL("select * from TRANSACCIONES");

        try {
            while (rs2.next()){
                Transaccion comodin=new Transaccion();
                comodin.setId_Transaccion(rs2.getInt("ID_TRANSACCION"));
                comodin.setId_Cliente(rs2.getString("ID_CLIENTE"));
                comodin.setMonto_Transac(rs2.getInt("MONTO_TRANSAC"));
                comodin.setTipo_id(rs2.getInt("TIPO_ID"));
                listaTransaccion.add(comodin);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sqlExecutorURL.cierraConexion();
        return listaTransaccion;
    }

    public int numeroTransaccion(String usuario)
    {
        String idCliente=getID(usuario);
        int contador=0;
        for(int i=0;i<listaTransacciones.size();i++){
            if(listaTransacciones.get(i).getId_Cliente().equals(idCliente)){
                contador++;
            }
        }
        return contador;
    }
}
