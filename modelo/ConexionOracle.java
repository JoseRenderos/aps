/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mario Valdez
 */
public class ConexionOracle {
    private String driver="oracle.jdbc.driver.OracleDriver";
    private String url="jdbc:oracle:thin:@207.139.25.2:1521:XE";
    private String user="rh_humanos";
    private String pass="infos";
    
    public Connection con() throws ClassNotFoundException, SQLException{
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pass);
    }
    
    
}
