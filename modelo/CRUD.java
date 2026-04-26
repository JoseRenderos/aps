/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public interface CRUD {
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException;
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException;
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException;
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException;

}
