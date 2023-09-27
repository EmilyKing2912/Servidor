package Data;

import java.sql.*;

public class SQLExecutorURL {
    private String sqlServerConeccionURL;

     private Connection dbConn=null;
     private PreparedStatement stmt=null;
     private ResultSet rs=null; //datos que devuelve la base de datos
     private  Statement statement=null;
    public SQLExecutorURL(String port,String bdName,String user,String pwd){
        //puerto 1433
        //data base PROYECTO2
        //user= sa
        //password=password;

        this.sqlServerConeccionURL="jdbc:sqlserver://localhost: "+port+
                ";databaseName="+bdName+";user="+user+";password="+pwd;
    }

    public  void  abreConexion()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //get conecction.
            dbConn= DriverManager.getConnection(sqlServerConeccionURL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void prepareStatement(String[] parametros){
        try{
            stmt=dbConn.prepareStatement(parametros[0]);
            for(int i=1;i<parametros.length;i++){
                stmt.setString(i,parametros[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  ResultSet ejecutaSQL(String sql){
        try{
            stmt=dbConn.prepareStatement(sql);
            rs=stmt.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public  void cierraConexion(){
        if(rs!=null){
            try{
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(dbConn!=null){
            try {
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
