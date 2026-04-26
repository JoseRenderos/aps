/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
/**
 *
 * @author Desarrollo Alsasa
 */
public class ConexionSBO {
    private String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url="jdbc:sqlserver://207.139.25.140:1433;databaseName=SBO_ALSASA";
//    private String url="jdbc:sqlserver://200.35.189.153:1433;databaseName=ALSASA_PRUEBAS";
    private String user="sa";
    private String pass="$Admin#2025x";
    
    public Connection con() throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pass);
    }
}
