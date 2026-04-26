/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import entidades.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OCRC1 extends ConexionSDO{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    APS_OCRC1 aps_ocrc1;

    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertar(int idAPS_OCRC) throws ClassNotFoundException, SQLException {
        try {
            ps = super.con().prepareStatement("INSERT INTO APS_OCRC1(idAPS_OCRC, idUsuario) "+ 
                                              "VALUES(?, (SELECT TOP 1 idUsuario FROM USUARIO ORDER BY idUsuario DESC))");
            ps.setInt(1, idAPS_OCRC);
            res = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_OCRC1.insertar(): "+ e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    public int guardar(Object ob) throws ClassNotFoundException, SQLException {
        try {
            aps_ocrc1=(APS_OCRC1)ob;
            ps = super.con().prepareStatement("INSERT INTO APS_OCRC1(idAPS_OCRC, idUsuario) "+ 
                                              "VALUES(?, ?)");
            ps.setInt(1, aps_ocrc1.getAPS_OCRC().getIdAPS_OCRC());
            ps.setInt(2, aps_ocrc1.getUsuario().getIdUsuario());
            res = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_OCRC1.guardar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int eliminar(Object ob) throws ClassNotFoundException, SQLException {
        try {
            aps_ocrc1=(APS_OCRC1)ob;
            ps = super.con().prepareStatement("DELETE FROM APS_OCRC1 "+ 
                                              "WHERE idUsuario=? AND idAPS_OCRC=?");
            ps.setInt(1, aps_ocrc1.getUsuario().getIdUsuario());
            ps.setInt(2, aps_ocrc1.getAPS_OCRC().getIdAPS_OCRC());
            res = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_OCRC1.eliminar(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
}
