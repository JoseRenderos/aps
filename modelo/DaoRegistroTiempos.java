/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import entidades.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Mario Valdez
 */
public class DaoRegistroTiempos  extends ConexionSDO{
    PreparedStatement ps;
    ResultSet rs;
    RTAV rtav;
    Actividades actividades;
    int res;
    
    public int insertar(Object Ob,int idUsuario) throws ClassNotFoundException, SQLException {
        try{
            rtav=(RTAV)Ob;
            String inicio=rtav.getInicio()+":00";
            String fin=rtav.getFin()+":00";
            ps=con().prepareStatement("INSERT INTO RTAV(codeEmp,nomEmp,inicio,fin,tiempo,idActividad,idUsuario) VALUES(?,?,?,?,DATEDIFF(MINUTE,CAST(? AS DATETIME),CAST(? AS DATETIME)),?,?);");
            ps.setInt(1,rtav.getCodeEmp());
            ps.setString(2,rtav.getNomEmp());
            ps.setString(3,inicio);
            ps.setString(4,fin);
            ps.setString(5,inicio);
            ps.setString(6,fin);
            ps.setString(7,rtav.getActividades().getId());
            ps.setInt(8,idUsuario);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoRegistroTiempos.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<Object> mostrarActividadesRegistradas() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps=super.con().prepareStatement("SELECT * FROM VW_ACTIVIDADES_VARIAS ");
            rs = ps.executeQuery();
            while(rs.next()){
               actividades= new Actividades(rs.getString(7), rs.getString(8));
               rtav=new RTAV();
               rtav.setId_RTAV(rs.getInt(1));
               rtav.setCodeEmp(rs.getInt(2));
               rtav.setNomEmp(rs.getString(3));
               rtav.setInicio(rs.getString(4));
               rtav.setFin(rs.getString(5));
               rtav.setTiempo(rs.getString(6));
               rtav.setActividades(actividades);
               ar.add(rtav);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.mostrarActividades(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int eliminar(int idActvidad) throws ClassNotFoundException, SQLException {
        try{
            ps=con().prepareStatement("UPDATE RTAV SET estado=0 WHERE id_RTAV=?");
            ps.setInt(1,idActvidad);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoRegistroTiempos.eliminar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
